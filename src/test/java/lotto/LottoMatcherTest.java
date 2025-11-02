package lotto;

import lotto.domain.BonusNumber;
import lotto.domain.Lotto;
import lotto.domain.LottoMatcher;
import lotto.domain.Ranking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class LottoMatcherTest {

    @DisplayName("6개 일치 시 1등이다.")
    @Test
    void 일등_당첨() {
        Lotto winningNumbers = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber("7");
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);

        Lotto myLotto = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        Ranking rank = matcher.match(myLotto);

        assertThat(rank).isEqualTo(Ranking.FIRST);
    }

    @DisplayName("5개 일치 + 보너스 일치 시 2등이다.")
    @Test
    void 이등_당첨() {
        Lotto winningNumbers = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber("7");
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);

        Lotto myLotto = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 7));
        Ranking rank = matcher.match(myLotto);

        assertThat(rank).isEqualTo(Ranking.SECOND);
    }

    @DisplayName("5개 일치 시 3등이다.")
    @Test
    void 삼등_당첨() {
        Lotto winningNumbers = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber("7");
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);

        Lotto myLotto = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 8));
        Ranking rank = matcher.match(myLotto);

        assertThat(rank).isEqualTo(Ranking.THIRD);
    }

    @DisplayName("4개 일치 + 보너스 일치해도 4등이다.")
    @Test
    void 삼등_당첨_보너스_일치() {
        Lotto winningNumbers = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber("7");
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);

        Lotto myLotto = new Lotto(Arrays.asList(1, 2, 3, 4, 7, 10));
        Ranking rank = matcher.match(myLotto);

        assertThat(rank).isEqualTo(Ranking.FOURTH);
    }

    @DisplayName("4개 일치 시 4등이다.")
    @Test
    void 사등_당첨() {
        Lotto winningNumbers = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber("7");
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);

        Lotto myLotto = new Lotto(Arrays.asList(1, 2, 3, 4, 8, 10));
        Ranking rank = matcher.match(myLotto);

        assertThat(rank).isEqualTo(Ranking.FOURTH);
    }

    @DisplayName("3개 일치 시 5등이다.")
    @Test
    void 오등_당첨() {
        Lotto winningNumbers = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber("7");
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);

        Lotto myLotto = new Lotto(Arrays.asList(1, 2, 3, 10, 11, 12));
        Ranking rank = matcher.match(myLotto);

        assertThat(rank).isEqualTo(Ranking.FIFTH);
    }

    @DisplayName("2개 이하 일치 시 낙첨이다.")
    @Test
    void 낙첨() {
        Lotto winningNumbers = new Lotto(Arrays.asList(1, 2, 3, 4, 5, 6));
        BonusNumber bonusNumber = new BonusNumber("7");
        LottoMatcher matcher = new LottoMatcher(winningNumbers, bonusNumber);

        Lotto myLotto = new Lotto(Arrays.asList(1, 2, 10, 11, 12, 13));
        Ranking rank = matcher.match(myLotto);

        assertThat(rank).isEqualTo(Ranking.NONE);
    }
}