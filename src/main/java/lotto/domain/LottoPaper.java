package lotto.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LottoPaper {
    private final List<Lotto> lottoPaper;

    private LottoPaper(List<Lotto> lottoPaper) {
        this.lottoPaper = lottoPaper;
    }

    public static LottoPaper generateLottoPaperByLottoCount(int count) {
        List<Lotto> lottoPaper = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            lottoPaper.add(Lotto.generateRandomLotto());
        }
        return new LottoPaper(lottoPaper);
    }

    public int getLottoCountInPaper() {
        return lottoPaper.size();
    }

    public List<Lotto> getLottoPaper() {
        return Collections.unmodifiableList(lottoPaper);
    }
}