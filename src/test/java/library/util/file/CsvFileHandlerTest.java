package library.util.file;

import library.domain.Book;
import library.domain.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CsvFileHandlerTest {

    private static final String testFilePath = "src/test/resources/books.csv";
    private CsvFileHandler fileHandler;

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
        assertThat(loadedBooks).isNotNull();
    }

    @Test
    @DisplayName("도서 목록을 파일에 저장할 수 있어야 합니다.")
    void testSaveBooksToFile() {
        // given
        List<Book> books = List.of(
                Book.createBook(1L, "Title 1", "Author 1", 100, LocalDateTime.now(), BookStatus.AVAILABLE),
                Book.createBook(2L, "Title 2", "Author 2", 200, LocalDateTime.now(), BookStatus.RENTED)
        );

        // when
        fileHandler.saveBooksToFile(books);

        // then
        List<Book> loadedBooks = fileHandler.loadBooksFromFile();
        assertThat(loadedBooks).isEqualTo(books);
    }
}
