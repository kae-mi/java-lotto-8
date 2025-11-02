package lotto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 1부터 45 범위를 벗어나면 예외가 발생한다.")
    @Test
    void 로또_번호가_1부터_45_범위를_벗어나면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 46)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 0 이하면 예외가 발생한다.")
    @Test
    void 로또_번호가_0_이하면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(0, 1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호는 오름차순으로 정렬된다.")
    @Test
    void 로또_번호는_오름차순으로_정렬된다() {
        Lotto lotto = new Lotto(Arrays.asList(6, 5, 4, 3, 2, 1));
        assertThat(lotto.getNumbers()).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @DisplayName("자동 생성된 로또는 6개의 번호를 가진다.")
    @Test
    void 자동_생성된_로또는_6개의_번호를_가진다() {
        Lotto lotto = Lotto.generateRandomLotto();
        assertThat(lotto.getNumbers()).hasSize(6);
    }

    @DisplayName("자동 생성된 로또의 번호는 1부터 45 사이다.")
    @Test
    void 자동_생성된_로또의_번호는_범위_내에_있다() {
        Lotto lotto = Lotto.generateRandomLotto();
        assertThat(lotto.getNumbers()).allMatch(number -> number >= 1 && number <= 45);
    }
}
