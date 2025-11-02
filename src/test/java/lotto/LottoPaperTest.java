package lotto;

import lotto.domain.LottoPaper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LottoPaperTest {

    @DisplayName("입력 개수만큼 로또가 들어간 종이를 생성한다.")
    @Test
    void 로또들이_나열되어_되어_있는_종이_생성() {
        LottoPaper paper = LottoPaper.generateLottoPaperByLottoCount(8);
        assertThat(paper.getLottoCountInPaper()).isEqualTo(8);
    }

    @DisplayName("생성된 모든 로또는 6개의 번호를 가진다.")
    @Test
    void 모든_로또_6개_번호() {
        LottoPaper paper = LottoPaper.generateLottoPaperByLottoCount(5);
        assertThat(paper.getLottoPaper())
                .allMatch(lotto -> lotto.getNumbers().size() == 6);
    }
}