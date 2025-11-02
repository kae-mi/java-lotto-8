package lotto.domain;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum Ranking {

    FIRST(6, 2_000_000_000, "6개 일치",(match, bonus) -> match == 6),

    SECOND(5, 30_000_000, "5개 일치, 보너스 볼 일치",(match, bonus) -> match == 5 && bonus),

    THIRD(5, 1_500_000, "5개 일치",(match, bonus) -> match == 5 && !bonus),

    FOURTH(4, 50_000, "4개 일치",(match, bonus) -> match == 4),

    FIFTH(3, 5_000, "3개 일치",(match, bonus) -> match == 3),

    NONE(0, 0, "낙첨",(match, bonus) -> true);  // 기본값


    private final int matchCount;
    private final int prizeMoney;
    private final String description;
    private final BiPredicate<Integer, Boolean> condition;

    Ranking(int matchCount, int prizeMoney, String description,
         BiPredicate<Integer, Boolean> condition) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;
        this.description = description;
        this.condition = condition;
    }

    public static Ranking of(int matchCount, boolean matchBonus) {
        return Arrays.stream(values())
                .filter(rank -> rank.matches(matchCount, matchBonus))
                .findFirst()
                .orElse(NONE);
    }

    private boolean matches(int matchCount, boolean matchBonus) {
        return condition.test(matchCount, matchBonus);
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public String getDescription() {
        return description;
    }

    public boolean isWinning() {
        return this != NONE;
    }
}