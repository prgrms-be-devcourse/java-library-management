package library.view;

import library.repository.BookRepository;
import library.repository.FileBookRepository;
import library.repository.InMemoryBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ModeTest {

    private static Stream<Arguments> modeStrategy() {
        return Stream.of(
                Arguments.of(Mode.NORMAL, FileBookRepository.class),
                Arguments.of(Mode.TEST, InMemoryBookRepository.class)
        );
    }

    @DisplayName("모드 코드로 모드를 찾을 수 있어야 합니다.")
    @EnumSource(Mode.class)
    @ParameterizedTest(name = "모드 {0}의 코드는 {1}입니다.")
    void testFindByCode(Mode mode) {
        Optional<Mode> actual = Mode.findByCode(mode.getCode());
        assertThat(actual).contains(mode);
    }

    @DisplayName("모드 코드로 모드를 찾을 수 없으면 Optional.empty()를 반환합니다.")
    @ValueSource(strings = {"3", "4"})
    @ParameterizedTest(name = "모드 코드 {0}에 해당하는 모드가 없으면 Optional.empty()를 반환합니다.")
    void failFindByCodeTest(String code) {
        Optional<Mode> actual = Mode.findByCode(code);
        assertThat(actual).isEmpty();
    }

    @DisplayName("빈 값이나 null로 모드를 조회 시 Optional.empty()를 반환합니다.")
    @NullAndEmptySource
    @ParameterizedTest(name = "모드 코드가 {0}이면 Optional.empty()를 반환합니다.")
    void failFindByNullAndEmptyCodeTest(String code) {
        Optional<Mode> actual = Mode.findByCode(code);
        assertThat(actual).isEmpty();
    }

    @DisplayName("모드에 따라 도서 저장소를 가져올 수 있어야 합니다.")
    @MethodSource("modeStrategy")
    @ParameterizedTest(name = "모드 {0}에 따라 도서 저장소를 가져올 수 있어야 합니다.")
    void testGetBookRepository(Mode mode, Class<?> expected) {
        BookRepository actual = mode.getBookRepository();
        assertThat(actual).isInstanceOf(expected);
    }
}
