package library.repository;

import library.domain.Book;
import library.domain.BookStatus;
import library.exception.BookException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

class FileBookRepositoryTest {

    private static final String TEST_FILE_PATH = "src/test/resources/books.csv";
    private final static int REPEAT_COUNT = 1000;
    private final static int THREAD_SIZE = 32;
    private FileBookRepository bookRepository;

    private static Book createDummyBook(int bookNumber) {
        return Book.createAvailableBook(
                bookNumber,
                "Title " + bookNumber,
                "Author " + bookNumber,
                bookNumber);
    }

    private void deleteAllBooks() {
        bookRepository.findAll().forEach(bookRepository::delete);
    }

    @BeforeEach
    void setUp() {
        bookRepository = new FileBookRepository(TEST_FILE_PATH);
    }

    @AfterEach
    void tearDown() {
        deleteAllBooks();
    }

    private void executeConcurrentActions(Consumer<Integer> action) {
        AtomicInteger atomicInteger = new AtomicInteger();
        CountDownLatch countDownLatch = new CountDownLatch(REPEAT_COUNT);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_SIZE);

        for (int i = 1; i <= REPEAT_COUNT; i++) {
            executorService.execute(() -> {
                int bookNumber = atomicInteger.incrementAndGet();
                action.accept(bookNumber);
                countDownLatch.countDown();
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("여러 사용자가 동시에 도서를 추가할 수 있어야 합니다.")
    void testAdd() {
        // When
        executeConcurrentActions(bookNumber -> bookRepository.add(createDummyBook(bookNumber)));

        // Then
        assertThat(bookRepository.findAll()).hasSize(REPEAT_COUNT);
    }

    @Test
    @DisplayName("여러 사용자가 동시에 도서를 삭제할 수 있어야 합니다.")
    void testDelete() {
        // Given
        for (int i = 1; i <= REPEAT_COUNT; i++) {
            Book book = createDummyBook(i);
            bookRepository.add(book);
        }

        // When
        executeConcurrentActions(bookNumber -> {
            Book book = bookRepository.findByBookNumber(bookNumber).orElseThrow();
            bookRepository.delete(book);
        });

        // Then
        assertThat(bookRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("여러 사용자가 동시에 하나의 도서를 대여하는 경우 첫 번째 사용자만 성공해야 합니다.")
    void testRent() {
        // Given
        final int BOOK_NUMBER = 1;
        Book book = createDummyBook(BOOK_NUMBER);
        bookRepository.add(book);

        // When
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        executeConcurrentActions(bookNumber -> {
            Book foundBook = bookRepository.findByBookNumber(BOOK_NUMBER).orElseThrow();
            try {
                foundBook.toRent();
                successCount.incrementAndGet();
            } catch (BookException e) {
                failCount.incrementAndGet();
            }
            bookRepository.persist();
        });

        // Then
        Book foundBook = bookRepository.findByBookNumber(BOOK_NUMBER).orElseThrow();
        assertThat(foundBook.getStatus()).isEqualTo(BookStatus.RENTED);
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failCount.get()).isEqualTo(REPEAT_COUNT - 1);
    }
}
