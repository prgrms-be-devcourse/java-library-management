package app.library.management.core.domain.util;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static app.library.management.core.domain.BookStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BookStatusManagerTest {

    @DisplayName("'ORGANIZING' 상태인 도서는 지정한 시간 뒤에 'AVAILABLE'로 도서 상태가 변경된다.")
    @Test
    void test() throws InterruptedException {
        // given
        BookRepository bookRepositoryMock = mock(BookRepository.class);
        ScheduledExecutorService realScheduler = Executors.newSingleThreadScheduledExecutor();
        LocalDateTime now = LocalDateTime.now();
        BookStatusManager bookStatusManager = new BookStatusManager(bookRepositoryMock, realScheduler, 400);
        Book book = new Book(1L, "책1", "작가1", 10, ORGANIZING, now);

        // when
        bookStatusManager.execute(book, now);
        Thread.sleep(100);

        // then
        assertThat(book.getStatus()).isEqualTo(AVAILABLE);
        verify(bookRepositoryMock).update(book);
        realScheduler.shutdown();
    }


}