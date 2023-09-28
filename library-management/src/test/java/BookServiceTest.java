import devcourse.backend.business.BookService;
import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.view.BookDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookServiceTest {
    String path = "src/test/resources/";
    String fileName = "도서 목록.csv";
    Path FILE_PATH = Paths.get(path.concat(fileName));
    FileRepository repository = new FileRepository(path, fileName);
    BookService service = new BookService(repository);

    @AfterEach
    void 파일_내용_지우기() {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write(repository.getColumns());
            writer.newLine();
        } catch (IOException e) {}
    }

    @Test
    void 수동_도서_등록() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);

        //when
        service.registerBook(book);

        //then
        Assertions.assertEquals(repository.findAll().size(), 1);
        Assertions.assertTrue(repository.findAll().get(0).isMatched(book.getTitle(), book.getAuthor(), book.getTotalPages()));
    }

    @Test
    void 전체_도서_목록_조회() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);

        BookDto book2 = new BookDto();
        book2.setTitle("이펙티브 자바 Effective Java 3/E");
        book2.setAuthor("조슈아 블로크");
        book2.setTotalPages(520);
        service.registerBook(book2);

        //when
        List<Book> books = service.getAllBooks();

        //then
        Assertions.assertEquals(books.size(), 2);
        Assertions.assertTrue(books.stream().anyMatch(b -> b.isMatched(book.getTitle(), book.getAuthor(), book.getTotalPages())));
        Assertions.assertTrue(books.stream().anyMatch(b -> b.isMatched(book2.getTitle(), book2.getAuthor(), book2.getTotalPages())));
    }

    @Test
    void 도서_제목으로_검색() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);

        String keyword = "객체지향";

        //when
        Book found = service.searchBooks(keyword).get(0);

        //then
        Assertions.assertTrue(found.isMatched(book.getTitle(), book.getAuthor(), book.getTotalPages()));
    }

    @Test
    void 도서_대여_성공() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);

        //when
        long id = repository.findByTitleAndAuthorAndTotalPages(book.getTitle(), book.getAuthor(), book.getTotalPages()).getId();
        service.rentBook(id);

        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> repository.findById(id).changeStatus(BookStatus.BORROWED));
    }

    @Test
    void 도서_반납_후_정리() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);
        long id = repository.findByTitleAndAuthorAndTotalPages(book.getTitle(), book.getAuthor(), book.getTotalPages()).getId();
        service.rentBook(id);

        //when
        service.returnBook(id);

        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.rentBook(id));
    }

    @Test
    void 도서_반납_5분_뒤() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);
        long id = repository.findByTitleAndAuthorAndTotalPages(book.getTitle(), book.getAuthor(), book.getTotalPages()).getId();
        service.rentBook(id);

        //when
        service.returnBook(id);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Assertions.assertDoesNotThrow(() -> service.rentBook(id));
            }
        }, 5 * 60 * 1000);
    }

    @Test
    void 도서_분실_처리() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);

        //when
        long id = repository.findByTitleAndAuthorAndTotalPages(book.getTitle(), book.getAuthor(), book.getTotalPages()).getId();
        service.reportLoss(id);

        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.reportLoss(id));
    }

    @Test
    void 분실_도서_반납() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);

        //when
        long id = repository.findByTitleAndAuthorAndTotalPages(book.getTitle(), book.getAuthor(), book.getTotalPages()).getId();
        service.reportLoss(id);
        service.returnBook(id);

        //then
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Assertions.assertDoesNotThrow(() -> service.rentBook(id));
            }
        }, 5 * 60 * 1000);
    }

    @Test
    void 도서_삭제_성공() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);

        //when
        long id = repository.findByTitleAndAuthorAndTotalPages(book.getTitle(), book.getAuthor(), book.getTotalPages()).getId();
        service.deleteBook(id);

        //then
        Assertions.assertEquals(service.getAllBooks().size(), 0);
    }

    @Test
    void 도서_삭제_실패() {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);
        service.registerBook(book);

        //when


        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            service.deleteBook(-1);
        });
    }
}
