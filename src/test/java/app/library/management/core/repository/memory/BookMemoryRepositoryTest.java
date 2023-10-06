package app.library.management.core.repository.memory;

import app.library.management.core.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookMemoryRepositoryTest {

    private BookMemoryRepository bookMemoryRepository;

    @BeforeEach
    public void setUp() {
        bookMemoryRepository = new BookMemoryRepository();
    }

    @DisplayName("도서를 추가하면 id값이 할당되고, bookArrayList에 책이 추가된다.")
    @Test
    void save() {
        // given
        Book book = new Book("토비의 스프링", "토비", 1000);

        // when
        Book savedBook = bookMemoryRepository.save(book);

        // then
        assertAll(
                () -> assertNotEquals(-1L, savedBook.getId()),
                () -> assertThat(bookMemoryRepository.findAll()).contains(savedBook)
        );
    }

    @DisplayName("동시에 여러 도서가 bookArrayList에 추가될 수 있다.")
    @Test
    void saveWithMulitThread() throws InterruptedException, ExecutionException {
        // given
        int threadCnt = 100;
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Callable<Long>> ans = getCallables(threadCnt);
        Set<Long> callableExSet = new HashSet<>();

        // when
        List<Future<Long>> futures = executorService.invokeAll(ans);
        for (Future<Long> future : futures) {
            callableExSet.add(future.get());
        }

        // then
        assertThat(callableExSet).hasSize(threadCnt);
    }

    private List<Callable<Long>> getCallables(int threadCnt) {
        List<Callable<Long>> callables = new ArrayList<>();
        for(int i=0; i<threadCnt; i++) {
            callables.add(() -> {
                Book book = new Book("title", "author", 100);
                Book savedBook = bookMemoryRepository.save(book);
                return savedBook.getId();
            });
        }
        return callables;
    }

    @DisplayName("도서 전체를 조회하면 bookArrayList에 있는 모든 도서들을 조회할 수 있다.")
    @Test
    void findAll() {
        // given
        Book book1 = new Book("토비의 스프링", "토비", 1000);
        Book book2 = new Book("토비의 스프링2", "토비2", 1000);

        Book savedBook1 = bookMemoryRepository.save(book1);
        Book savedBook2 = bookMemoryRepository.save(book2);

        // when
        List<Book> bookList = bookMemoryRepository.findAll();

        // then
        assertAll(
                () -> assertThat(bookList).hasSize(2),
                () -> assertThat(bookList).contains(savedBook1),
                () -> assertThat(bookList).contains(savedBook2)
        );
    }

    @DisplayName("도서 제목으로 도서들을 조회할 수 있다.")
    @Test
    void findByTitle() {
        // given
        Book book1 = new Book("토비의 스프링", "토비", 1000);
        Book book2 = new Book("토비의 스프링2", "토비2", 1000);
        Book book3 = new Book("자바", "작가", 1000);

        Book savedBook1 = bookMemoryRepository.save(book1);
        Book savedBook2 = bookMemoryRepository.save(book2);
        Book savedBook3 = bookMemoryRepository.save(book3);

        // when
        List<Book> bookList = bookMemoryRepository.findByTitle("자바");

        // then
        assertAll(
                () -> assertThat(bookList).hasSize(1),
                () -> assertThat(bookList).contains(savedBook3)
        );
    }

    @DisplayName("id로 도서를 조회할 수 있다.")
    @Test
    void findById() {
        // given
        Book book1 = new Book("토비의 스프링", "토비", 1000);
        Book book2 = new Book("토비의 스프링2", "토비2", 1000);
        Book book3 = new Book("토비의 스프링3", "토비3", 1000);

        Book savedBook1 = bookMemoryRepository.save(book1);
        bookMemoryRepository.save(book2);
        bookMemoryRepository.save(book3);

        // when
        Optional<Book> optionalBook = bookMemoryRepository.findById(savedBook1.getId());

        // then
        assertAll(
                () -> assertThat(optionalBook).isNotNull(),
                () -> assertThat(savedBook1).isEqualTo(optionalBook.get())
        );
    }

    @DisplayName("도서를 삭제하면, bookArrayList에서 도서가 삭제된다.")
    @Test
    void delete() {
        // given
        Book book = new Book("토비의 스프링", "토비", 1000);
        Book savedBook = bookMemoryRepository.save(book);

        // when
        bookMemoryRepository.delete(savedBook);
        List<Book> bookList = bookMemoryRepository.findAll();

        // then
        assertThat(bookList).doesNotContain(savedBook);
    }



}