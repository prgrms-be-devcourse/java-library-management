package service;

import model.Book;
import model.Status;
import org.junit.jupiter.api.*;
import repository.FileRepository;
import util.BookScheduler;
import util.BookTestScheduler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FIleBookServiceTest {
    private BookService bookService;
    private BookScheduler bookScheduler;

    @BeforeEach
    void setUp() {
        FileRepository fileRepository = new FileRepository("test.csv");
        bookService = new BookService(fileRepository);
        fileRepository.saveBook(new Book(fileRepository.createBookNo(), "대여 가능 책", "작가1", 123, Status.AVAILABLE));
        fileRepository.saveBook(new Book(fileRepository.createBookNo(), "대여 중인 책", "작가2", 123, Status.BORROWED));
        fileRepository.saveBook(new Book(fileRepository.createBookNo(), "분실된 책", "작가3", 123, Status.LOST));
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void save() {
        bookService.saveBook("해리포터", "J.K 롤링", 255);

        assertThat(bookService.findAllBook().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("제목으로 도서 검색 테스트")
    void findBooksByTitle() {
        bookService.saveBook("해리포터", "J.K 롤링", 255);

        List<Book> books = bookService.findBooksByTitle("해리");
        assertThat(books.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void borrowBookByBookNo() {
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.borrowBookByBookNo(2L));
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.borrowBookByBookNo(3L));
        bookService.borrowBookByBookNo(1L);
        List<Book> books = bookService.findAllBook();
        assertThat(books.get(0).getStatus()).isEqualTo(Status.BORROWED);
    }

    @Test
    @DisplayName("도서 반납 테스트")
    void returnBookByBookNo() {
        bookScheduler = new BookTestScheduler();
        Assertions.assertThrows(IllegalStateException.class, () -> bookService.returnBookByBookNo(1L, bookScheduler));
        bookService.returnBookByBookNo(2L, bookScheduler);
        bookService.returnBookByBookNo(3L, bookScheduler);
        List<Book> books = bookService.findAllBook();
        assertThat(books.get(1).getStatus()).isEqualTo(Status.ORGANIZING);
        assertThat(books.get(2).getStatus()).isEqualTo(Status.ORGANIZING);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            assertThat(books.get(1).getStatus()).isEqualTo(Status.AVAILABLE);
            assertThat(books.get(2).getStatus()).isEqualTo(Status.AVAILABLE);
        }
    }

    @Test
    @DisplayName("도서 분실 테스트")
    void lostBookByBookNo() {
        bookService.lostBookByBookNo(1L);
        List<Book> books = bookService.findAllBook();
        assertThat(books.get(0).getStatus()).isEqualTo(Status.LOST);
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void deleteBookByBookNo() {
        bookService.deleteBookByBookNo(1L);
        List<Book> books = bookService.findAllBook();
        assertThat(books.size()).isEqualTo(2);
    }

    @AfterEach
    void deleteFile() throws IOException {
        FileWriter fileWriter = new FileWriter("test.csv");
        fileWriter.write(""); // 내용을 빈 문자열로 덮어씁니다.
        fileWriter.close();
    }
}
