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

    @DisplayName("구입 금액 대비 수익률을 계산한다.")
    @Test
    void 수익률_계산() {
        // 5등 1개 = 5,000원, 구입 금액 8,000원
        List<Ranking> ranks = List.of(Ranking.FIFTH, Ranking.NONE, Ranking.NONE);
        WinningStatistics statistics = new WinningStatistics(ranks);

        double rateOfReturn = statistics.calculateRateOfReturn(8_000);
        assertThat(rateOfReturn).isEqualTo(62.5);
    }

    @DisplayName("수익률은 소수점 둘째 자리에서 반올림한다.")
    @Test
    void 수익률_반올림() {
        // 5등 1개 = 5,000원, 구입 금액 6,000원
        // 5000 / 6000 * 100 = 83.333... → 83.3
        List<Ranking> ranks = List.of(Ranking.FIFTH);
        WinningStatistics statistics = new WinningStatistics(ranks);

        double rateOfReturn = statistics.calculateRateOfReturn(6_000);
        assertThat(rateOfReturn).isEqualTo(83.3);
    }

    @DisplayName("낙첨 시 수익률은 0%이다.")
    @Test
    void 수익률_낙첨() {
        List<Ranking> ranks = List.of(Ranking.NONE, Ranking.NONE);
        WinningStatistics statistics = new WinningStatistics(ranks);

        double rateOfReturn = statistics.calculateRateOfReturn(2_000);
        assertThat(rateOfReturn).isEqualTo(0.0);
    }

    @DisplayName("원금보다 많이 당첨되면 100%를 초과한다.")
    @Test
    void 수익률_100퍼센트_초과() {
        // 3등 1개 = 1,500,000원, 구입 금액 1,000원
        List<Ranking> ranks = List.of(Ranking.THIRD);
        WinningStatistics statistics = new WinningStatistics(ranks);

        double rateOfReturn = statistics.calculateRateOfReturn(1_000);
        assertThat(rateOfReturn).isEqualTo(150_000.0);
    }

    @DisplayName("1등 당첨 시 수익률을 계산한다.")
    @Test
    void 수익률_일등_당첨() {
        // 1등 1개 = 2,000,000,000원, 구입 금액 1,000원
        List<Ranking> ranks = List.of(Ranking.FIRST);
        WinningStatistics statistics = new WinningStatistics(ranks);

        double rateOfReturn = statistics.calculateRateOfReturn(1_000);
        assertThat(rateOfReturn).isEqualTo(200_000_000.0);
    }

    @DisplayName("여러 등수 당첨 시 수익률을 계산한다.")
    @Test
    void 수익률_여러_등수() {
        // 5등 2개 + 4등 1개 = 10,000 + 50,000 = 60,000원
        // 구입 금액 8,000원
        // 60,000 / 8,000 * 100 = 750.0
        List<Ranking> ranks = List.of(Ranking.FIFTH, Ranking.FIFTH, Ranking.FOURTH);
        WinningStatistics statistics = new WinningStatistics(ranks);

        double rateOfReturn = statistics.calculateRateOfReturn(8_000);
        assertThat(rateOfReturn).isEqualTo(750.0);
    }

    @DisplayName("반올림 예시 - 51.56%는 51.6%가 된다.")
    @Test
    void 수익률_반올림_올림() {
        // 4등 1개 = 50,000원, 구입 금액 97,000원
        // 50,000 / 97,000 * 100 = 51.546... → 51.5
        List<Ranking> ranks = List.of(Ranking.FOURTH);
        WinningStatistics statistics = new WinningStatistics(ranks);

        double rateOfReturn = statistics.calculateRateOfReturn(97_000);
        assertThat(rateOfReturn).isEqualTo(51.5);
    }
}