package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoPaper;
import lotto.domain.Ranking;
import lotto.domain.WinningStatistics;

import java.util.List;

public class OutputView {
    private static final String PURCHASE_COUNT_MESSAGE = "%d개를 구매했습니다.";
    private static final String LOTTO_NUMBERS_FORMAT = "[%s]";
    private static final String NUMBER_DELIMITER = ", ";
    private static final String STATISTICS_HEADER = "당첨 통계";
    private static final String STATISTICS_DIVIDER = "---";
    private static final String RANK_RESULT_FORMAT = "%s (%s원) - %d개";
    private static final String RATE_OF_RETURN_MESSAGE = "총 수익률은 %.1f%%입니다.";

    public void printPurchaseCount(int count) {
        System.out.println();
        System.out.println(String.format(PURCHASE_COUNT_MESSAGE, count));
    }

    public void printLottoPaper(LottoPaper paper) {
        for (Lotto lotto : paper.getLottoPaper()) {
            printLottoNumbers(lotto);
        }
    }

    private void printLottoNumbers(Lotto lotto) {
        String numbers = formatLottoNumbers(lotto.getNumbers());
        System.out.println(String.format(LOTTO_NUMBERS_FORMAT, numbers));
    }

    private String formatLottoNumbers(List<Integer> numbers) {
        return numbers.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + NUMBER_DELIMITER + b)
                .orElse("");
    }

    public void printWinningStatistics(WinningStatistics statistics) {
        printStatisticsHeader();
        printRankStatistics(statistics);
    }

    private void printStatisticsHeader() {
        System.out.println();
        System.out.println(STATISTICS_HEADER);
        System.out.println(STATISTICS_DIVIDER);
    }

    private void printRankStatistics(WinningStatistics statistics) {
        printRankResult(Ranking.FIFTH, statistics.getCountByRank(Ranking.FIFTH));
        printRankResult(Ranking.FOURTH, statistics.getCountByRank(Ranking.FOURTH));
        printRankResult(Ranking.THIRD, statistics.getCountByRank(Ranking.THIRD));
        printRankResult(Ranking.SECOND, statistics.getCountByRank(Ranking.SECOND));
        printRankResult(Ranking.FIRST, statistics.getCountByRank(Ranking.FIRST));
    }

    private void printRankResult(Ranking ranking, int count) {
        String formattedPrizeMoney = formatPrizeMoney(ranking.getPrizeMoney());
        System.out.println(String.format(RANK_RESULT_FORMAT,
                ranking.getDescription(),
                formattedPrizeMoney,
                count));
    }

    private String formatPrizeMoney(int prizeMoney) {
        return String.format("%,d", prizeMoney);
    }

    public void printRateOfReturn(double rateOfReturn) {
        System.out.printf(RATE_OF_RETURN_MESSAGE, rateOfReturn);
        System.out.println();
    }
}