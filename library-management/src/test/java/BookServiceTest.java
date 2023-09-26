import devcourse.backend.business.BookService;
import devcourse.backend.medel.Book;
import devcourse.backend.medel.FileRepository;
import devcourse.backend.view.BookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class BookServiceTest {
    FileRepository repository = new FileRepository("src/test/resources/도서 목록.csv");
    BookService service = new BookService(repository);

    @Test
    void 수동_도서_등록 () {
        //given
        BookDto book = new BookDto();
        book.setTitle("객체지향의 사실과 오해");
        book.setAuthor("조영호");
        book.setTotalPages(260);

        //when
        service.registerBook(book);

        //then
        Assertions.assertEquals(repository.findAll().size(), 1);
        Assertions.assertEquals(repository.findAll().get(0).getTitle(), book.getTitle());
    }
}
