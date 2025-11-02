package lotto;

import lotto.domain.Ranking;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RankingTest {

    @DisplayName("6개 일치 시 1등이다.")
    @Test
    void 일등_판별() {
        Ranking rank = Ranking.of(6, false);
        assertThat(rank).isEqualTo(Ranking.FIRST);
        assertThat(rank.getPrizeMoney()).isEqualTo(2_000_000_000);
    }

    @DisplayName("6개 일치 시 보너스는 무시된다.")
    @Test
    void 일등_보너스_무시() {
        assertThat(Ranking.of(6, true)).isEqualTo(Ranking.FIRST);
        assertThat(Ranking.of(6, false)).isEqualTo(Ranking.FIRST);
    }

    @DisplayName("5개 일치 + 보너스 일치 시 2등이다.")
    @Test
    void 이등_판별() {
        Ranking rank = Ranking.of(5, true);
        assertThat(rank).isEqualTo(Ranking.SECOND);
        assertThat(rank.getPrizeMoney()).isEqualTo(30_000_000);
    }

    @DisplayName("5개 일치 시 3등이다.")
    @Test
    void 삼등_판별() {
        Ranking rank = Ranking.of(5, false);
        assertThat(rank).isEqualTo(Ranking.THIRD);
        assertThat(rank.getPrizeMoney()).isEqualTo(1_500_000);
    }

    @DisplayName("4개 일치 시 4등이다.")
    @Test
    void 사등_판별() {
        Ranking rank = Ranking.of(4, false);
        assertThat(rank).isEqualTo(Ranking.FOURTH);
        assertThat(rank.getPrizeMoney()).isEqualTo(50_000);
    }

    @DisplayName("4개 일치 + 보너스 일치해도 4등이다 (보너스 무시).")
    @Test
    void 사등_보너스_무시() {
        Ranking rank = Ranking.of(4, true);
        assertThat(rank).isEqualTo(Ranking.FOURTH);
    }

    @DisplayName("3개 일치 시 5등이다.")
    @Test
    void 오등_판별() {
        Ranking rank = Ranking.of(3, false);
        assertThat(rank).isEqualTo(Ranking.FIFTH);
        assertThat(rank.getPrizeMoney()).isEqualTo(5_000);
    }

    @DisplayName("3개 일치 + 보너스 일치해도 5등이다 (보너스 무시).")
    @Test
    void 오등_보너스_무시() {
        Ranking rank = Ranking.of(3, true);
        assertThat(rank).isEqualTo(Ranking.FIFTH);
    }

    @DisplayName("2개 이하 일치 시 낙첨이다.")
    @Test
    void 낙첨_판별() {
        assertThat(Ranking.of(2, false)).isEqualTo(Ranking.NONE);
        assertThat(Ranking.of(1, false)).isEqualTo(Ranking.NONE);
        assertThat(Ranking.of(0, false)).isEqualTo(Ranking.NONE);
    }

    @DisplayName("2개 일치 + 보너스 일치해도 낙첨이다.")
    @Test
    void 낙첨_보너스_무시() {
        assertThat(Ranking.of(2, true)).isEqualTo(Ranking.NONE);
    }

    @DisplayName("당첨 여부를 확인할 수 있다.")
    @Test
    void 당첨_여부_확인() {
        assertThat(Ranking.FIRST.isWinning()).isTrue();
        assertThat(Ranking.FIFTH.isWinning()).isTrue();
        assertThat(Ranking.NONE.isWinning()).isFalse();
    }
}