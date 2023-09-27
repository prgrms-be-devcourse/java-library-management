import devcourse.backend.business.BookService;
import devcourse.backend.medel.Book;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.view.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookServiceTest {
    FileRepository repository = new FileRepository("src/test/resources/도서 목록.csv");
    BookService service = new BookService(repository);

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
        Assertions.assertTrue(books.get(0).isMatched(book.getTitle(), book.getAuthor(), book.getTotalPages()));
        Assertions.assertTrue(books.get(1).isMatched(book2.getTitle(), book2.getAuthor(), book2.getTotalPages()));
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
}
