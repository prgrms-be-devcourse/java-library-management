package library.util.file;

import library.domain.Book;
import library.domain.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvFileHandlerTest {

    private static final String testFilePath = "src/main/resources/books.csv";
    private CsvFileHandler fileHandler;

    static Stream<Book> provideBooks() {
        return Stream.of(
                Book.createBook(1L, "Title 1", "Author 1", 100, LocalDateTime.now(), BookStatus.AVAILABLE),
                Book.createBook(2L, "Title 2", "Author 2", 200, LocalDateTime.now(), BookStatus.RENTED)
        );
    }

    @BeforeEach
    void setUp() {
        fileHandler = new CsvFileHandler(testFilePath);
    }

    @Test
    @DisplayName("도서 목록을 파일에서 불러올 수 있어야 합니다.")
    void testLoadBooksFromFile() {
        // when
        List<Book> loadedBooks = fileHandler.loadBooksFromFile();

        // then
        assertNotNull(loadedBooks);
    }

    @DisplayName("도서 목록을 파일에 저장할 수 있어야 합니다.")
    @MethodSource("provideBooks")
    @ParameterizedTest
    void testSaveBooksToFile(Book book) {
        // given
        List<Book> books = List.of(book);

        // when
        fileHandler.saveBooksToFile(books);

        // then
        List<Book> loadedBooks = fileHandler.loadBooksFromFile();
        assertEquals(books, loadedBooks);
    }
}
