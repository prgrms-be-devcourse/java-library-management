package library.view.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookFunctionTest {

    @DisplayName("도서 기능 코드로 도서 기능을 찾을 수 있어야 합니다.")
    @EnumSource(BookFunction.class)
    @ParameterizedTest(name = "도서 기능 코드가 {0}인 경우 도서 기능을 찾을 수 있어야 합니다.")
    void testFindByCode(BookFunction bookFunction) {
        Optional<BookFunction> foundBookFunction = BookFunction.findByCode(bookFunction.getCode());
        assertThat(foundBookFunction).contains(bookFunction);
    }

    @DisplayName("도서 기능 코드로 도서 기능을 찾을 수 없으면 Optional.empty()를 반환합니다.")
    @ValueSource(strings = {"8", "9"})
    @ParameterizedTest(name = "도서 기능 코드가 {0}인 경우 도서 기능을 찾을 수 없어야 합니다.")
    void failFindByCodeTest(String code) {
        Optional<BookFunction> foundBookFunction = BookFunction.findByCode(code);
        assertThat(foundBookFunction).isEmpty();
    }
}
