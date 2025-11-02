package lotto.domain;

public class LottoMatcher {
    private final Lotto winningNumbers;
    private final BonusNumber bonusNumber;

    public LottoMatcher(Lotto winningNumbers, BonusNumber bonusNumber) {
        bonusNumber.validateBonusNumberNotDuplicateWithWinningNumbers(winningNumbers);
        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    public Ranking match(Lotto lotto) {
        int matchCount = countMatchingNumbers(lotto);
        boolean matchBonus = lotto.containsNumber(bonusNumber.getNumber());
        return Ranking.of(matchCount, matchBonus);
    }

    private int countMatchingNumbers(Lotto lotto) {
        return (int) lotto.getNumbers().stream()
                .filter(number -> winningNumbers.getNumbers().contains(number))
                .count();
    }
}