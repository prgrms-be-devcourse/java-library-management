import devcourse.backend.business.BookOrganizingScheduler;
import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;
import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static devcourse.backend.FileSetting.TEST_FILE_NAME;
import static devcourse.backend.FileSetting.TEST_FILE_PATH;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookOrganizingSchedulerTest {
    private final int BOOK_ORGANIZATION_TIME = 5000; // 0.5초
    private BookOrganizingScheduler scheduler;
    private Repository repository;

    public BookOrganizingSchedulerTest() {
        repository = new FileRepository(TEST_FILE_PATH.getValue(), TEST_FILE_NAME.getValue());
        scheduler = new BookOrganizingScheduler(repository, BOOK_ORGANIZATION_TIME);
    }

    @Test
    @DisplayName("테스트에서는 0.5초 뒤에 [대여 가능] 상태로 변경됩니다.")
    void 도서_정리_중에서_대여_가능_상태로_변경() {
        // 도서 도서 정리 중인 도서 생성
        repository.addBook(
                new Book.Builder("이펙티브 자바", "조슈아 블로크", 520)
                        .bookStatus(BookStatus.ARRANGING)
                        .build());

        // 도서 반납
        scheduler.startScheduler();

        // 도서 반납 시 일정 시간 후 [대여 가능] 상태로 바뀜
        Book returnedBook = repository.findByTitleAndAuthor("이펙티브 자바", "조슈아 블로크").get();
        assertEquals(BookStatus.AVAILABLE, returnedBook.getStatus());
    }
}
