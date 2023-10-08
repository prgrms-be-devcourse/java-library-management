package app.library.management.core.service;

import app.library.management.core.domain.Book;
import app.library.management.core.domain.util.BookStatusManager;
import app.library.management.core.repository.BookRepository;
import app.library.management.core.repository.memory.BookMemoryRepository;
import app.library.management.core.service.response.dto.BookServiceResponse;
import app.library.management.core.service.response.dto.status.ResponseState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

import static app.library.management.core.domain.BookStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class BookServiceMemoryConcurrencyTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    public void setUp() {
        bookRepository = new BookMemoryRepository();
        BookStatusManager bookStatusManager = mock(BookStatusManager.class);
        bookService = new BookService(bookRepository, bookStatusManager);
    }

    @DisplayName("동시에 동일한 도서를 [대여]하려고 할 때에, 오직 하나의 스레드만 성공하고 나머지는 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {100, 150, 200, 250, 500, 1000, 1500, 2000})
    void rentWithMultiThread(int threadCount) throws InterruptedException, ExecutionException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Book book = new Book(1L, "title", "author", 100, AVAILABLE, LocalDateTime.now());
        Book savedBook = bookRepository.save(book);

        // when
        List<Future<BookServiceResponse>> futures = executorService.invokeAll(getRentCallables(threadCount, (int)savedBook.getId()));
        executorService.shutdown();
        int succcessCnt = 0;
        for (Future<BookServiceResponse> future : futures) {
            if (future.get().getResponseState() == ResponseState.SUCCESS)
                succcessCnt++;
        }

        // then
        assertThat(succcessCnt).isEqualTo(1);
    }

    private List<Callable<BookServiceResponse>> getRentCallables(int threadCnt, int bookId) {
        List<Callable<BookServiceResponse>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                return bookService.rent(bookId, LocalDateTime.now());
            });
        }
        return callables;
    }

    @DisplayName("동시에 동일한 도서를 [반납]하려고 할 때에, 오직 하나의 스레드만 성공하고 나머지는 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {100, 150, 200, 250, 500, 1000, 1500, 2000})
    void returnBookWithMultiThread(int threadCount) throws InterruptedException, ExecutionException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Book book = new Book(1L, "title", "author", 100, RENTED, LocalDateTime.now());
        Book savedBook = bookRepository.save(book);

        // when
        List<Future<BookServiceResponse>> futures = executorService.invokeAll(getReturnBookCallables(threadCount, (int)savedBook.getId()));
        executorService.shutdown();
        int succcessCnt = 0;
        for (Future<BookServiceResponse> future : futures) {
            if (future.get().getResponseState() == ResponseState.SUCCESS)
                succcessCnt++;
        }

        // then
        assertThat(succcessCnt).isEqualTo(1);
    }

    private List<Callable<BookServiceResponse>> getReturnBookCallables(int threadCnt, int bookId) {
        List<Callable<BookServiceResponse>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                return bookService.returnBook(bookId, LocalDateTime.now());
            });
        }
        return callables;
    }

    @DisplayName("동시에 동일한 도서를 [분실처리]하려고 할 때에, 오직 하나의 스레드만 성공하고 나머지는 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {100, 150, 200, 250, 500, 1000, 1500, 2000})
    void reportLostWithMultiThread(int threadCount) throws InterruptedException, ExecutionException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Book book = new Book(1L, "title", "author", 100, AVAILABLE, LocalDateTime.now());
        Book savedBook = bookRepository.save(book);

        // when
        List<Future<BookServiceResponse>> futures = executorService.invokeAll(getReportLostCallables(threadCount, (int)savedBook.getId()));
        executorService.shutdown();
        int succcessCnt = 0;
        for (Future<BookServiceResponse> future : futures) {
            if (future.get().getResponseState() == ResponseState.SUCCESS)
                succcessCnt++;
        }

        // then
        assertThat(succcessCnt).isEqualTo(1);
    }

    private List<Callable<BookServiceResponse>> getReportLostCallables(int threadCnt, int bookId) {
        List<Callable<BookServiceResponse>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                return bookService.reportLost(bookId, LocalDateTime.now());
            });
        }
        return callables;
    }

    @DisplayName("동시에 동일한 도서를 [삭제]하려고 할 때에, 오직 하나의 스레드만 성공하고 나머지는 실패한다.")
    @ParameterizedTest
    @ValueSource(ints = {100, 150, 200, 250, 500, 1000, 1500, 2000})
    void deleteWithMultiThread(int threadCount) throws InterruptedException, ExecutionException {
        // given
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        Book book = new Book(1L, "title", "author", 100, AVAILABLE, LocalDateTime.now());
        Book savedBook = bookRepository.save(book);

        // when
        List<Future<BookServiceResponse>> futures = executorService.invokeAll(getDeleteCallables(threadCount, (int)savedBook.getId()));
        executorService.shutdown();
        int succcessCnt = 0;
        for (Future<BookServiceResponse> future : futures) {
            if (future.get().getResponseState() == ResponseState.SUCCESS)
                succcessCnt++;
        }

        // then
        assertThat(succcessCnt).isEqualTo(1);
    }

    private List<Callable<BookServiceResponse>> getDeleteCallables(int threadCnt, int bookId) {
        List<Callable<BookServiceResponse>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                return bookService.delete(bookId);
            });
        }
        return callables;
    }

}