package lotto;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static camp.nextstep.edu.missionutils.test.Assertions.assertRandomUniqueNumbersInRangeTest;
import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;

class ApplicationTest extends NsTest {
    private static final String ERROR_MESSAGE = "[ERROR]";

    @Test
    void 기능_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("8000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "8개를 구매했습니다.",
                            "[8, 21, 23, 41, 42, 43]",
                            "[3, 5, 11, 16, 32, 38]",
                            "[7, 11, 16, 35, 36, 44]",
                            "[1, 8, 11, 31, 41, 42]",
                            "[13, 14, 16, 38, 42, 45]",
                            "[7, 11, 30, 40, 42, 43]",
                            "[2, 13, 22, 32, 38, 45]",
                            "[1, 3, 5, 14, 22, 45]",
                            "3개 일치 (5,000원) - 1개",
                            "4개 일치 (50,000원) - 0개",
                            "5개 일치 (1,500,000원) - 0개",
                            "5개 일치, 보너스 볼 일치 (30,000,000원) - 0개",
                            "6개 일치 (2,000,000,000원) - 0개",
                            "총 수익률은 62.5%입니다."
                    );
                },
                List.of(8, 21, 23, 41, 42, 43),
                List.of(3, 5, 11, 16, 32, 38),
                List.of(7, 11, 16, 35, 36, 44),
                List.of(1, 8, 11, 31, 41, 42),
                List.of(13, 14, 16, 38, 42, 45),
                List.of(7, 11, 30, 40, 42, 43),
                List.of(2, 13, 22, 32, 38, 45),
                List.of(1, 3, 5, 14, 22, 45)
        );
    }

    @DisplayName("1등 당첨 시 정상 동작")
    @Test
    void 일등_당첨_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("1000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "1개를 구매했습니다.",
                            "[1, 2, 3, 4, 5, 6]",
                            "6개 일치 (2,000,000,000원) - 1개",
                            "총 수익률은 200000000.0%입니다."
                    );
                },
                List.of(1, 2, 3, 4, 5, 6)
        );
    }

    @DisplayName("여러 등수 복합 당첨 시 정상 동작")
    @Test
    void 복합_당첨_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    run("5000", "1,2,3,4,5,6", "7");
                    assertThat(output()).contains(
                            "5개를 구매했습니다.",
                            "3개 일치 (5,000원) - 2개",
                            "4개 일치 (50,000원) - 1개",
                            "5개 일치 (1,500,000원) - 1개"
                    );
                },
                List.of(1, 2, 3, 10, 11, 12),   // 5등
                List.of(1, 2, 3, 4, 11, 12),    // 4등
                List.of(1, 2, 3, 4, 5, 12),     // 3등
                List.of(1, 2, 3, 13, 14, 15),   // 5등
                List.of(10, 11, 12, 13, 14, 15) // 낙첨
        );
    }

    @DisplayName("예외 발생 시 재입력 요청")
    @Test
    void 예외_재입력_테스트() {
        assertRandomUniqueNumbersInRangeTest(
                () -> {
                    runException(
                            "abc",              // 구입 금액 오류
                            "3000",             // 정상
                            "1,2,3,4,5",        // 당첨 번호 개수 오류
                            "1,2,3,4,5,6",      // 정상
                            "6",                // 보너스 번호 중복
                            "7"                 // 정상
                    );
                    assertThat(output()).contains(
                            "[ERROR] 구입 금액은 숫자여야 합니다.",
                            "3개를 구매했습니다.",
                            "[ERROR] 로또 번호는 6개여야 합니다.",
                            "[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.",
                            "당첨 통계"
                    );
                },
                List.of(1, 2, 3, 4, 5, 6),
                List.of(10, 11, 12, 13, 14, 15),
                List.of(20, 21, 22, 23, 24, 25)
        );
    }


    @Test
    void 예외_테스트() {
        assertSimpleTest(() -> {
            runException("1000j");
            assertThat(output()).contains(ERROR_MESSAGE);
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
