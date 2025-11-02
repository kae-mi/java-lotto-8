package lotto;

import lotto.domain.BonusNumber;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BonusNumberTest {

    @DisplayName("정상적인 보너스 번호로 객체를 생성한다.")
    @Test
    void 정상_보너스_번호() {
        BonusNumber bonusNumber = new BonusNumber("7");
        assertThat(bonusNumber.getNumber()).isEqualTo(7);
    }

    @DisplayName("보너스 번호가 숫자가 아니면 예외가 발생한다.")
    @Test
    void 보너스_번호_숫자_아님() {
        assertThatThrownBy(() -> new BonusNumber("a"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void 보너스_번호_범위_초과() {
        assertThatThrownBy(() -> new BonusNumber("46"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 0 이하면 예외가 발생한다.")
    @Test
    void 보너스_번호_범위_미만() {
        assertThatThrownBy(() -> new BonusNumber("0"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("공백이 포함된 보너스 번호를 파싱한다.")
    @Test
    void 공백_포함_보너스_번호() {
        BonusNumber bonusNumber = new BonusNumber(" 7 ");
        assertThat(bonusNumber.getNumber()).isEqualTo(7);
    }
}