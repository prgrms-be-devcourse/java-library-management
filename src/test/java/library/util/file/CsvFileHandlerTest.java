package library.util.file;

import library.domain.Book;
import library.domain.BookStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CsvFileHandlerTest {

    static private final String testFilePath = "src/main/resources/books.csv";

    @Test
    @DisplayName("도서 목록을 파일에서 불러올 수 있어야 합니다.")
    void testLoadBooksFromFile() {
        // given
        CsvFileHandler fileHandler = new CsvFileHandler(testFilePath);

        // when
        List<Book> loadedBooks = fileHandler.loadBooksFromFile();

        // then
        assertNotNull(loadedBooks);
    }

    @Test
    @DisplayName("도서 목록을 파일에 저장할 수 있어야 합니다.")
    void testSaveBooksToFile() {
        // given
        CsvFileHandler fileHandler = new CsvFileHandler(testFilePath);
        List<Book> savedBooks = new ArrayList<>();
        savedBooks.add(Book.createBook(1L, "Title 1", "Author 1", 100, LocalDateTime.now(), BookStatus.AVAILABLE));
        savedBooks.add(Book.createBook(2L, "Title 2", "Author 2", 200, LocalDateTime.now(), BookStatus.RENTED));

        // when
        fileHandler.saveBooksToFile(savedBooks);
        List<Book> loadedBooks = fileHandler.loadBooksFromFile();

        // then
        assertEquals(savedBooks.size(), loadedBooks.size());
    }
}
