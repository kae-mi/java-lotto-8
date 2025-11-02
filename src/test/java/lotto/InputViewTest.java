package lotto;

import lotto.domain.Lotto;
import lotto.view.InputView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputViewTest {

    @DisplayName("쉼표로 구분된 당첨 번호를 파싱하여 Lotto 객체를 생성한다.")
    @Test
    void 당첨_번호_파싱() {
        InputView inputView = new InputView();
        Lotto lotto = inputView.parseWinningNumbers("1,2,3,4,5,6");
        assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("공백이 포함된 당첨 번호를 파싱한다.")
    @Test
    void 공백_포함_파싱() {
        InputView inputView = new InputView();
        Lotto lotto = inputView.parseWinningNumbers("1, 2, 3, 4, 5, 6");
        assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("당첨 번호가 숫자가 아니면 예외가 발생한다.")
    @Test
    void 당첨_번호_숫자_아님() {
        InputView inputView = new InputView();
        assertThatThrownBy(() -> inputView.parseWinningNumbers("1,2,3,4,5,a"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Lotto의 검증 로직이 작동한다 - 6개가 아닌 경우.")
    @Test
    void 로또_검증_6개_아님() {
        InputView inputView = new InputView();
        assertThatThrownBy(() -> inputView.parseWinningNumbers("1,2,3,4,5"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Lotto의 검증 로직이 작동한다 - 범위 초과.")
    @Test
    void 로또_검증_범위_초과() {
        InputView inputView = new InputView();
        assertThatThrownBy(() -> inputView.parseWinningNumbers("1,2,3,4,5,46"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("Lotto의 검증 로직이 작동한다 - 중복.")
    @Test
    void 로또_검증_중복() {
        InputView inputView = new InputView();
        assertThatThrownBy(() -> inputView.parseWinningNumbers("1,2,3,4,5,5"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}