package lotto.domain;

public class BonusNumber {
    private static final int MIN_LOTTO_NUMBER = 1;
    private static final int MAX_LOTTO_NUMBER = 45;

    private final int number;

    public BonusNumber(String input, Lotto winningNumbers) {
        int parsedNumber = parseInputToBonusNumber(input);
        validateBonusNumber(parsedNumber, winningNumbers);
        this.number = parsedNumber;
    }

    private int parseInputToBonusNumber(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 숫자여야 합니다.");
        }
    }

    private void validateBonusNumber(int number, Lotto winningNumbers) {
        validateBonusNumberRange(number);
        validateBonusNumberNotDuplicateWithWinningNumbers(number, winningNumbers);
    }

    private void validateBonusNumberRange(int number) {
        if (number < MIN_LOTTO_NUMBER || number > MAX_LOTTO_NUMBER) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 1부터 45 사이의 숫자여야 합니다.");
        }
    }

    private void validateBonusNumberNotDuplicateWithWinningNumbers(int number, Lotto winningNumbers) {
        if (winningNumbers.getNumbers().contains(number)) {
            throw new IllegalArgumentException("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
        }
    }

    public int getNumber() {
        return number;
    }
}