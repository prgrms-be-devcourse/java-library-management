import domain.Book;
import domain.BookStatus;
import exception.NotExistBookIdException;
import exception.UnchangeableStatusException;
import manager.console.OutputManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.TestRepository;
import service.BookService;

import java.util.ArrayList;
import java.util.List;

import static domain.BookStatus.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestServiceTest {
    private final OutputManager outputManager = new OutputManager();
    private final TestRepository repository = new TestRepository(new ArrayList<>());
    private final BookService service = new BookService(repository, outputManager);


    @Test
    @DisplayName("책 저장 성공")
    public void saveBook() {
        //given
        String title = "개인주의자 선언";
        String author = "문유석";
        Integer page = 111;

        //when
        service.saveBook(title, author, page);
        Book book = repository.getBooks().get(0);

        //then
        assertEquals(book.getTitle(), title);
        assertEquals(book.getAuthor(), author);
        assertEquals(book.getPage(), page);
        assertEquals(book.getStatus(), BORROW_AVAILABLE);
    }

    @Test
    @DisplayName("제목으로 책 검색 성공")
    public void findBookByTitle() {
        //given
        service.saveBook("사피엔스", "유발 하라리", 111);
        service.saveBook("사피엔솔로지", "송준호", 222);
        //when
        List<Book> books = repository.findByTitle("사피");
        //then
        assertEquals("사피엔스", books.get(0).getTitle());
        assertEquals("사피엔솔로지", books.get(1).getTitle());
    }

    @Test
    @DisplayName("책 대여 성공")
    public void borrowBook() {
        //given
        Book book = saveAndReturnBook();
        //when
        service.borrowBook(book.getId());
        //then
        assertEquals(BORROWED, book.getStatus());
    }

    @Test
    @DisplayName("책 반납 후 정리 성공")
    public void returnBook() {
        //given
        Book book = saveAndReturnBook();
        //when
        service.borrowBook(book.getId());
        service.returnBook(book.getId());
        //then
        assertEquals(CLEANING, book.getStatus());
    }

    @Test
    @DisplayName("책 분실 성공")
    public void lostBook() {
        //given
        Book book = saveAndReturnBook();
        service.borrowBook(book.getId());
        //when
        service.reportLostBook(book.getId());
        //then
        assertEquals(LOST, book.getStatus());
    }

    @Test
    @DisplayName("책 반납 시 분실 변경 성공")
    public void returnLostBook() {
        //given
        Book book = saveAndReturnBook();
        service.borrowBook(book.getId());
        //when
        service.reportLostBook(book.getId());
        service.returnBook(book.getId());
        //then
        assertEquals(CLEANING, book.getStatus());
    }


    @Test
    @DisplayName("책 삭제 성공")
    public void deleteBook() {
        //given
        Book book = saveAndReturnBook();
        //when
        service.removeBook(book.getId());
        //then
        assertThrows(NotExistBookIdException.class, () -> service.borrowBook(book.getId()));
    }

    @Test
    @DisplayName("분실한 책 대여 불가")
    public void canNotBorrowLostBook() {
        //given
        Book book = saveAndReturnBook();
        service.borrowBook(book.getId());
        service.reportLostBook(book.getId());
        //when then
        UnchangeableStatusException e = assertThrows(UnchangeableStatusException.class, () -> service.borrowBook(book.getId()));
        assertEquals("분실된 도서입니다.", e.getMessage());
    }

    @Test
    @DisplayName("정리중인 책 대여 불가")
    public void canNotBorrowCleaningBook() {
        //given
        Book book = saveAndReturnBook();
        service.borrowBook(book.getId());
        service.returnBook(book.getId());
        //when then
        UnchangeableStatusException e = assertThrows(UnchangeableStatusException.class, () -> service.returnBook(book.getId()));
        assertEquals("이미 반납되어 정리 중인 도서입니다.", e.getMessage());
    }

    @Test
    @DisplayName("반납 후 특정 시간 뒤에 대여 가능")
    public void checkCleaning() throws InterruptedException {
        //given
        Book book = saveAndReturnBook();

        book.setCleanTimeForTest(); // 임의로 정리시간 10초로 설정

        service.borrowBook(book.getId());
        service.returnBook(book.getId());

        //when
        Thread.sleep(10 * 1000);

        //then
        service.borrowBook(book.getId());
    }

    @Test
    @DisplayName("대여 가능한 책 반납 불가")
    public void canNotReturnAvailableBook() {
        //given
        Book book = saveAndReturnBook();
        //when then
        UnchangeableStatusException e = assertThrows(UnchangeableStatusException.class, () -> service.returnBook(book.getId()));
        assertEquals("원래 대여가 가능한 도서입니다.", e.getMessage());
    }

    @Test
    @DisplayName("이미 분실된 책 분실 신고 불가")
    public void canNotReportTwice() {
        //given
        Book book = saveAndReturnBook();
        service.borrowBook(book.getId());
        service.reportLostBook(book.getId());
        //when then
        UnchangeableStatusException e = assertThrows(UnchangeableStatusException.class, () -> service.reportLostBook(book.getId()));
        assertEquals("이미 분실처리된 도서입니다.", e.getMessage());
    }

    @Test
    @DisplayName("대여 가능한 책 분실신고 불가")
    public void canNotReportAvailableBook() {
        //given
        Book book = saveAndReturnBook();
        //when then
        UnchangeableStatusException e = assertThrows(UnchangeableStatusException.class, () -> service.reportLostBook(book.getId()));
        assertEquals("도서관에서 보관중으로, 분실처리 대상 도서가 아닙니다.", e.getMessage());
    }

    @Test
    @DisplayName("정리 중인 책 분실 신고 불가")
    public void canNotReportCleaningBook() {
        //given
        Book book = saveAndReturnBook();
        service.borrowBook(book.getId());
        service.returnBook(book.getId());
        //when then
        UnchangeableStatusException e = assertThrows(UnchangeableStatusException.class, () -> service.reportLostBook(book.getId()));
        assertEquals("도서관에서 보관중으로, 분실처리 대상 도서가 아닙니다.", e.getMessage());
    }

    private Book saveAndReturnBook() {
        service.saveBook("개인주의자 선언", "문유석", 111);
        return repository.getBooks().get(0);
    }
}
