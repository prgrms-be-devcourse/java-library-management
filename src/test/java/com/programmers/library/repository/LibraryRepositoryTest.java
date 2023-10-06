package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.StatusType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class LibraryRepositoryTest {

    private LibraryRepository libraryRepository;

    @BeforeEach
    public void setUp() {
        libraryRepository = new LibraryFileRepository();
//        libraryRepository = new LibraryMemoryRepository();
    }

    @AfterEach
    public void tearDown() {
        libraryRepository.clearAll();
    }

    @Test
    @DisplayName("도서를 등록한다.")
    void saveTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);

        // when
        int saveBookId = libraryRepository.save(book);

        // then
        assertThat(saveBookId).isOne();
    }

    @Test
    @DisplayName("도서 등록에 대해 동시성 테스트를 수행한다.")
    void concurrentSaveTest() throws InterruptedException {
        int threadCount = 100;  // 100개의 스레드
        CountDownLatch latch = new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        IntStream.range(0, threadCount).forEach(i -> {
            executorService.submit(() -> {
                Book book = new Book("Book " + i, "Author " + i, 100 + i);
                int bookId = libraryRepository.save(book);
                latch.countDown();
            });
        });

        executorService.shutdown();
        latch.await();  // 모든 스레드가 작업을 완료할 때까지 기다린다

        assertThat(libraryRepository.findAll()).hasSize(threadCount);   // 저장된 도서의 수를 확인하여 동시성 작업이 성공적으로 수행되는지 확인
    }

    @Test
    @DisplayName("전체 도서 목록을 조회한다.")
    void findAllTest() {
        // given
        Book book1 = new Book("오브젝트(객체)", "조영호", 456);
        int saveBookId1 = libraryRepository.save(book1);

        Book book2 = new Book("객체지향의 사실과 오해", "조영호", 123);
        int saveBookId2 = libraryRepository.save(book2);

        // when
        List<Book> books = libraryRepository.findAll();

        // then
        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("도서 제목(키워드)으로 도서를 조회한다.")
    void findByTitleTest() {
        // given
        Book book1 = new Book("오브젝트(객체)", "조영호", 456);
        int saveBookId1 = libraryRepository.save(book1);

        Book book2 = new Book("객체지향의 사실과 오해", "조영호", 123);
        int saveBookId2 = libraryRepository.save(book2);

        // when
        List<Book> searchBooks = libraryRepository.findByTitle("객체");

        // then
        assertThat(searchBooks).hasSize(2);
    }

    @Test
    @DisplayName("도서 번호로 도서를 조회한다.")
    void findByIdTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int saveBookId = libraryRepository.save(book);

        // when
        Book findBook = libraryRepository.findById(saveBookId).get();

        // then
        assertThat(book).isEqualTo(findBook);
    }

    @Test
    @DisplayName("변경된 도서 상태 정보를 업데이트한다.")
    void updateTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);
        StatusType beforeStatus = book.getStatus();

        // when
        Book updatedBook = book.updateStatus(StatusType.RENTING);  // 대여 가능 -> 대여중으로 상태 변경
        libraryRepository.update(updatedBook);

        Book findBook = libraryRepository.findById(bookId).get();
        StatusType afterStatus = findBook.getStatus();

        // then
        assertThat(afterStatus).isEqualTo(StatusType.RENTING);  // 상태 변경 확인
        assertThat(afterStatus).isNotEqualTo(beforeStatus);     // 상태 변경 전과 후 비교
    }

    @Test
    @DisplayName("도서 번호로 도서를 삭제한다.")
    void deleteTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int saveBookId = libraryRepository.save(book);

        // when
        libraryRepository.delete(saveBookId);

        // then
        List<Book> books = libraryRepository.findAll();
        assertThat(books).isEmpty();    // 삭제시, 빈 도서 목록임을 확인
    }
}