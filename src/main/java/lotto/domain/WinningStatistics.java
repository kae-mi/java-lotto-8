package lotto.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinningStatistics {
    private final Map<Ranking, Integer> rankCounts;

    public WinningStatistics(List<Ranking> ranks) {
        this.rankCounts = new HashMap<>();
        initializeRankCounts();
        countRanks(ranks);
    }

    private void initializeRankCounts() {
        rankCounts.put(Ranking.FIRST, 0);
        rankCounts.put(Ranking.SECOND, 0);
        rankCounts.put(Ranking.THIRD, 0);
        rankCounts.put(Ranking.FOURTH, 0);
        rankCounts.put(Ranking.FIFTH, 0);
        rankCounts.put(Ranking.NONE, 0);
    }

    private void countRanks(List<Ranking> ranks) {
        for (Ranking rank : ranks) {
            int currentCount = rankCounts.get(rank);
            rankCounts.put(rank, currentCount + 1);
        }
    }

    public int getCountByRank(Ranking rank) {
        return rankCounts.get(rank);
    }

    public long calculateTotalPrizeMoney() {
        long totalPrizeMoney = 0;
        totalPrizeMoney += (long) rankCounts.get(Ranking.FIRST) * Ranking.FIRST.getPrizeMoney();
        totalPrizeMoney += (long) rankCounts.get(Ranking.SECOND) * Ranking.SECOND.getPrizeMoney();
        totalPrizeMoney += (long) rankCounts.get(Ranking.THIRD) * Ranking.THIRD.getPrizeMoney();
        totalPrizeMoney += (long) rankCounts.get(Ranking.FOURTH) * Ranking.FOURTH.getPrizeMoney();
        totalPrizeMoney += (long) rankCounts.get(Ranking.FIFTH) * Ranking.FIFTH.getPrizeMoney();
        return totalPrizeMoney;
    }
}