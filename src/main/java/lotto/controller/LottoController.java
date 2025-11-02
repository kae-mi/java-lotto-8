package lotto.controller;

import lotto.domain.*;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.ArrayList;
import java.util.List;

public class LottoController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        LottoPurchaseAmount purchaseAmount = inputPurchaseAmount();
        LottoPaper lottoPaper = generateAndPrintLottoPaper(purchaseAmount);

        Lotto winningNumbers = inputWinningNumbers();
        BonusNumber bonusNumber = inputBonusNumber(winningNumbers);

        printWinningResult(lottoPaper, winningNumbers, bonusNumber, purchaseAmount);
    }

    private LottoPurchaseAmount inputPurchaseAmount() {
        while (true) {
            try {
                String input = inputView.readPurchaseAmount();
                return new LottoPurchaseAmount(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private LottoPaper generateAndPrintLottoPaper(LottoPurchaseAmount purchaseAmount) {
        int lottoCount = purchaseAmount.calculatePurchasableLottoCount();
        LottoPaper lottoPaper = LottoPaper.generateLottoPaperByLottoCount(lottoCount);

        outputView.printPurchaseCount(lottoPaper.getLottoCountInPaper());
        outputView.printLottoPaper(lottoPaper);

        return lottoPaper;
    }

    private Lotto inputWinningNumbers() {
        while (true) {
            try {
                String input = inputView.readWinningNumbers();
                return inputView.parseWinningNumbers(input);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private BonusNumber inputBonusNumber(Lotto winningNumbers) {
        while (true) {
            try {
                String input = inputView.readBonusNumber();
                BonusNumber bonusNumber = new BonusNumber(input);
                bonusNumber.validateBonusNumberNotDuplicateWithWinningNumbers(winningNumbers);
                return bonusNumber;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void printWinningResult(LottoPaper lottoPaper, Lotto winningNumbers,
                                    BonusNumber bonusNumber, LottoPurchaseAmount purchaseAmount) {
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);
        List<Ranking> rankings = calculateRankings(lottoPaper, matcher);

        WinningStatistics statistics = new WinningStatistics(rankings);
        outputView.printWinningStatistics(statistics);

        double rateOfReturn = statistics.calculateRateOfReturn(purchaseAmount.getAmount());
        outputView.printRateOfReturn(rateOfReturn);
    }

    private List<Ranking> calculateRankings(LottoPaper lottoPaper, LottoMatcher matcher) {
        List<Ranking> rankings = new ArrayList<>();
        for (Lotto lotto : lottoPaper.getLottoPaper()) {
            Ranking ranking = matcher.match(lotto);
            rankings.add(ranking);
        }
        return rankings;
    }
}