package com.programmers.library.utils;

import com.programmers.library.domain.Book;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class CSVFileHandlerTest {

    private static CSVFileHandler csvFileHandler;

    @BeforeAll
    static void beforeAll() {
        csvFileHandler = new CSVFileHandler();
    }

//    @AfterEach
//    void tearDown() {
//        csvFileHandler.clearCSV();
//    }

    @Order(1)
    @Test
    @DisplayName("CSV 파일에서 도서 목록을 읽는다.")
    void readBooksFromCSVTest() {
        // when
        List<Book> books = csvFileHandler.readBooksFromCSV();

        // then
        assertThat(books).isEmpty();
    }

    @Order(2)
    @Test
    @DisplayName("도서 목록을 CSV 파일에 쓴다.")
    void writeBooksToCSVTest() {
        // given
        Book book1 = new Book(1, "Book1", "Author1", 100, StatusType.AVAILABLE);
        Book book2 = new Book(2, "Book2", "Author2", 200, StatusType.RENTING);
        List<Book> books = List.of(book1, book2);

        // when
        csvFileHandler.writeBooksToCSV(books);

        // then
        List<Book> readBooks = csvFileHandler.readBooksFromCSV();
        assertThat(readBooks).hasSize(2);
    }

    @Order(3)
    @Test
    @DisplayName("도서를 CSV 파일에 추가한다.")
    void appendBookToCSVTest() {
        // given
        Book book = new Book(3, "Book3", "Author3", 300, StatusType.AVAILABLE);

        // when
        csvFileHandler.appendBookToCSV(book);

        // then
        List<Book> readBooks = csvFileHandler.readBooksFromCSV();
        assertThat(readBooks).hasSize(3);
    }

    @Order(4)
    @Test
    @DisplayName("CSV 파일을 초기화한다.")
    void clearCSVTest() {
        // when
        csvFileHandler.clearCSV();

        // then
        List<Book> readBooks = csvFileHandler.readBooksFromCSV();
        assertThat(readBooks).isEmpty();
    }
}
