package lotto;

import java.util.List;

public class OutputView {
    private static final String PURCHASE_COUNT_MESSAGE = "%d개를 구매했습니다.";
    private static final String LOTTO_NUMBERS_FORMAT = "[%s]";
    private static final String NUMBER_DELIMITER = ", ";

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
}