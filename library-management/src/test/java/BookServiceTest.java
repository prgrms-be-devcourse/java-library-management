import devcourse.backend.business.ModeType;
import devcourse.backend.business.BookService;
import devcourse.backend.medel.BookStatus;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.BookDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookServiceTest {
    private BookService bookService;
    private Repository repository;

    @BeforeEach
    void BookService_초기화() {
        // Mock Repository를 생성
        repository = mock(Repository.class);
        bookService = new BookService(ModeType.TEST_MODE);
    }

    private static List<BookDto> getBookDtos() {
        List<BookDto> dtos = new ArrayList<>();

        BookDto book = new BookDto("이펙티브 자바", "조슈아 블로크", 520);
        dtos.add(book);

        BookDto book2 = new BookDto("친절한 SQL 튜닝", "조시형", 560);
        dtos.add(book2);

        return dtos;
    }

    @Test
    @DisplayName("도서를 등록할 수 있습니다.")
    void 수동_도서_등록() {
        // [이펙티브 자바] 등록
        BookDto book = getBookDtos().get(0);
        bookService.registerBook(book);

        // BookService가 Repository의 addBook() 호출
        verify(repository, times(1)).addBook(any());
    }

    @Test
    void 도서_반납_후_정리_상태_후_대여_가능_상태로_변경() {
        long bookId = 1L;
        bookService.returnBook(bookId);

        // Repository의 changeStatus 메서드가 호출
        /**
         * 5분뒤에 changeStatus(bookId, BookStatus.AVAILABLE)이 호출되는건 어떻게 확인..?
         */
        verify(repository, times(1)).changeStatus(bookId, BookStatus.ARRANGING);
    }

    @Test
    void 도서_분실_처리() {
        long bookId = 1L;
        bookService.reportLoss(bookId);

        // Repository의 changeStatus 메서드가 호출되었는지 검증
        verify(repository, times(1)).changeStatus(bookId, BookStatus.LOST);
    }

    @Test
    void 도서_삭제_성공() {
        long bookId = 1L;
        bookService.deleteBook(bookId);

        verify(repository, times(1)).deleteById(bookId);
    }
}
