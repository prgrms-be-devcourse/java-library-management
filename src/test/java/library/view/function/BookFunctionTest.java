package library.view.function;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookFunctionTest {

    @Test
    @DisplayName("도서 기능 코드로 도서 기능을 찾을 수 있어야 합니다.")
    void testFindByCode() {
        Optional<BookFunction> foundBookFunction = BookFunction.findByCode("1");
        assertTrue(foundBookFunction.isPresent());

        Optional<BookFunction> notFoundBookFunction = BookFunction.findByCode("8");
        assertFalse(notFoundBookFunction.isPresent());
    }
}
