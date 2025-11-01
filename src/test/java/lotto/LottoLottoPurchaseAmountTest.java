package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoLottoPurchaseAmountTest {

    @DisplayName("정상적인 구입 금액으로 객체를 생성한다.")
    @Test
    void 정상_구입_금액() {
        LottoPurchaseAmount lottoPurchaseAmount = new LottoPurchaseAmount("8000");
        assertThat(lottoPurchaseAmount.getAmount()).isEqualTo(8000);
    }

    @DisplayName("구입 금액이 숫자가 아니면 예외가 발생한다.")
    @Test
    void 구입_금액이_숫자가_아닌_경우() {
        assertThatThrownBy(() -> new LottoPurchaseAmount("9000asd"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 1,000원 단위가 아니면 예외가 발생한다.")
    @Test
    void 구입_금액이_천원_단위가_아닌_경우() {
        assertThatThrownBy(() -> new LottoPurchaseAmount("1500"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 0 이하면 예외가 발생한다.")
    @Test
    void 구입_금액이_0_이하인_경우() {
        assertThatThrownBy(() -> new LottoPurchaseAmount("0"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액이 음수면 예외가 발생한다.")
    @Test
    void 구입_금액이_음수인_경우() {
        assertThatThrownBy(() -> new LottoPurchaseAmount("-1000"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("구입 금액으로 구매 가능한 로또 개수를 계산한다.")
    @Test
    void 로또_개수_계산() {
        LottoPurchaseAmount lottoPurchaseAmount = new LottoPurchaseAmount("10000");
        assertThat(lottoPurchaseAmount.calculatePurchasableLottoCount()).isEqualTo(10);
    }
}