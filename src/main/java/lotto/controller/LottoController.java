package lotto.controller;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        LottoPurchaseAmount purchaseAmount = inputPurchaseAmount();
        LottoPaper lottoPaper = generateLottoPaper(purchaseAmount);
        printLottoPaper(lottoPaper);

        Lotto winningNumbers = inputWinningNumbers();
        BonusNumber bonusNumber = inputBonusNumber(winningNumbers);

        WinningStatistics statistics = calculateWinningStatistics(lottoPaper, winningNumbers, bonusNumber);
        printWinningStatistics(statistics, purchaseAmount);
    }

    private LottoPurchaseAmount inputPurchaseAmount() {
        return retryOnException(() -> {
            String input = inputView.readPurchaseAmount();
            return new LottoPurchaseAmount(input);
        });
    }

    private LottoPaper generateLottoPaper(LottoPurchaseAmount purchaseAmount) {
        int lottoCount = purchaseAmount.calculatePurchasableLottoCount();
        return LottoPaper.generateLottoPaperByLottoCount(lottoCount);
    }

    private void printLottoPaper(LottoPaper lottoPaper) {
        outputView.printPurchaseCount(lottoPaper.getLottoCountInPaper());
        outputView.printLottoPaper(lottoPaper);
    }

    private Lotto inputWinningNumbers() {
        return retryOnException(() -> {
            String input = inputView.readWinningNumbers();
            return inputView.parseWinningNumbers(input);
        });
    }

    private BonusNumber inputBonusNumber(Lotto winningNumbers) {
        return retryOnException(() -> {
            String input = inputView.readBonusNumber();
            BonusNumber bonusNumber = new BonusNumber(input);
            bonusNumber.validateBonusNumberNotDuplicateWithWinningNumbers(winningNumbers);
            return bonusNumber;
        });
    }

    private WinningStatistics calculateWinningStatistics(LottoPaper lottoPaper, Lotto winningNumbers, BonusNumber bonusNumber) {
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);
        List<Ranking> rankings = calculateRankings(lottoPaper, matcher);
        return new WinningStatistics(rankings);
    }

    private List<Ranking> calculateRankings(LottoPaper lottoPaper, LottoMatcher matcher) {
        List<Ranking> rankings = new ArrayList<>();
        for (Lotto lotto : lottoPaper.getLottoPaper()) {
            Ranking ranking = matcher.match(lotto);
            rankings.add(ranking);
        }
        return rankings;
    }

    private void printWinningStatistics(WinningStatistics statistics, LottoPurchaseAmount purchaseAmount) {
        outputView.printWinningStatistics(statistics);
        double rateOfReturn = statistics.calculateRateOfReturn(purchaseAmount.getAmount());
        outputView.printRateOfReturn(rateOfReturn);
    }

    private <T> T retryOnException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}