package library.view;

import library.repository.BookRepository;
import library.repository.FileBookRepository;
import library.repository.InMemoryBookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ModeTest {

    @Test
    @DisplayName("모드 코드로 모드를 찾을 수 있어야 합니다.")
    void testFindByCode() {
        Optional<Mode> normalMode = Mode.findByCode("1");
        assertEquals(Mode.NORMAL, normalMode.get());

        Optional<Mode> testMode = Mode.findByCode("2");
        assertEquals(Mode.TEST, testMode.get());

        Optional<Mode> invalidMode = Mode.findByCode("3");
        assertFalse(invalidMode.isPresent());
    }

    @Test
    @DisplayName("모드에 따라 도서 저장소를 가져올 수 있어야 합니다.")
    void testGetBookRepository() {
        BookRepository normalRepository = Mode.NORMAL.getBookRepository();
        assertTrue(normalRepository instanceof FileBookRepository);

        BookRepository testRepository = Mode.TEST.getBookRepository();
        assertTrue(testRepository instanceof InMemoryBookRepository);
    }
}
