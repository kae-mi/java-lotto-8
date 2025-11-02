package lotto;

import lotto.domain.Ranking;
import lotto.domain.WinningStatistics;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WinningStatisticsTest {

    @DisplayName("등수별 당첨 개수를 집계한다.")
    @Test
    void 등수별_당첨_개수_집계() {
        List<Ranking> ranks = List.of(Ranking.FIFTH, Ranking.FOURTH, Ranking.FIFTH);
        WinningStatistics statistics = new WinningStatistics(ranks);

        assertThat(statistics.getCountByRank(Ranking.FIFTH)).isEqualTo(2);
        assertThat(statistics.getCountByRank(Ranking.FOURTH)).isEqualTo(1);
    }

    @DisplayName("당첨되지 않은 등수는 0개로 집계된다.")
    @Test
    void 미당첨_등수_0개() {
        List<Ranking> ranks = List.of(Ranking.FIFTH);
        WinningStatistics statistics = new WinningStatistics(ranks);

        assertThat(statistics.getCountByRank(Ranking.FIRST)).isEqualTo(0);
    }

    @DisplayName("총 당첨 금액을 계산한다.")
    @Test
    void 총_당첨_금액_계산() {
        List<Ranking> ranks = List.of(
                Ranking.FIFTH,  // 5,000
                Ranking.FOURTH, // 50,000
                Ranking.FIFTH   // 5,000
        );
        WinningStatistics statistics = new WinningStatistics(ranks);

        assertThat(statistics.calculateTotalPrizeMoney()).isEqualTo(60_000);
    }

    @DisplayName("모두 낙첨이면 총 당첨 금액은 0이다.")
    @Test
    void 모두_낙첨() {
        List<Ranking> ranks = List.of(Ranking.NONE, Ranking.NONE);
        WinningStatistics statistics = new WinningStatistics(ranks);

        assertThat(statistics.calculateTotalPrizeMoney()).isEqualTo(0);
    }
}