package lotto.domain;

public class LottoPurchaseAmount {
    private static final int LOTTO_PRICE = 1000;
    private final int amount;

    public LottoPurchaseAmount(String input) {
        int parsedAmount = parseInputToAmount(input);
        validateAmount(parsedAmount);
        this.amount = parsedAmount;
    }

    private int parseInputToAmount(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 숫자여야 합니다.");
        }
    }

    private void validateAmount(int amount) {
        validateAmountIsPositive(amount);
        validateAmountIsInThousandUnit(amount);
    }

    private void validateAmountIsPositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 양수여야 합니다.");
        }
    }

    private void validateAmountIsInThousandUnit(int amount) {
        if (amount % LOTTO_PRICE != 0) {
            throw new IllegalArgumentException("[ERROR] 구입 금액은 1,000원 단위여야 합니다.");
        }
    }

    public int calculatePurchasableLottoCount() {
        return amount / LOTTO_PRICE;
    }

    public int getAmount() {
        return amount;
    }
}