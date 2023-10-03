import dev.course.config.LibraryConfig;
import dev.course.config.BookRepositoryConfig;
import dev.course.domain.AppConstants;
import dev.course.domain.Book;
import dev.course.domain.BookState;
import dev.course.exception.FuncFailureException;
import dev.course.manager.JSONFileManager;
import dev.course.repository.BookRepository;

import dev.course.repository.GeneralBookRepository;
import dev.course.repository.TestBookRepository;
import dev.course.service.LibraryManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTimeout;

public class LibraryAppFeatureTest {

    private LibraryConfig libraryConfig;
    private LibraryManagement library;
    private FileWriter writer;
    private FileOutputStream fileOutputStream;
    private TestBookRepositoryConfig testBookRepositoryConfig;
    private JSONFileManager jsonFileManager;

    static class TestBookRepositoryConfig extends BookRepositoryConfig {

        private BookRepository bookRepository;

        private TestBookRepositoryConfig(int BookRepositoryId) {
            super(BookRepositoryId);
        }

        public void setBookRepository(BookRepository bookRepository) {
            this.bookRepository = bookRepository;
        }

        public BookRepository getBookRepository() {
            return this.bookRepository;
        }
    }

    static class TestLibraryManagement extends LibraryManagement {

        private final BookRepository bookRepository;
        private final List<Book> searched = new ArrayList<>();

        public TestLibraryManagement(LibraryConfig libraryConfig) {
            super(libraryConfig);
            this.bookRepository = libraryConfig.getBookRepositoryConfig().getBookRepository();
        }

        @Override
        public void add(Book book) {
            super.add(book);
        }

        @Override
        public void returns(Long bookId) throws FuncFailureException {

            Book book = this.bookRepository.findById(bookId)
                    .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));

            Book elem = book.getState().handleReturn(bookRepository, book);
            if (elem.equalState(BookState.ARRANGEMENT)) {

                CompletableFuture<Void> completableFuture = runAsyncToWaitArrangement(book, 10000);

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (!completableFuture.isDone()) {
                        Book availableBook = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.RENTAL_AVAILABLE);
                        this.bookRepository.add(availableBook);
                        System.out.println("[System] 시스템 종료 전 처리: 도서 상태를 '대여 가능'으로 변경하였습니다.\n");
                    }
                }));
            }

            System.out.println("[System] 도서 반납 처리가 완료되었습니다.\n");
        }

        @Override
        public CompletableFuture<Void> runAsyncToWaitArrangement(Book book, long delay) {
            return super.runAsyncToWaitArrangement(book, delay);
        }

        @Override
        public void afterDelayArrangementComplete(Book book, long delay) {
            super.afterDelayArrangementComplete(book, delay);
        }

        @Override
        public void findByTitle(String title) throws FuncFailureException {
            List<Book> bookList = this.bookRepository.getAll();
            bookList.stream().filter(elem ->
                    elem.getTitle().contains(title))
                    .forEach(this.searched::add);
        }

        public List<Book> getSearched() {
            return this.searched;
        }
    }

    @BeforeEach
    public void init() {
        try {
            writer = new FileWriter(AppConstants.TEST_FILEPATH);
            testBookRepositoryConfig = new TestBookRepositoryConfig(0);
            jsonFileManager = new JSONFileManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void after() {
        try {
            fileOutputStream = new FileOutputStream(AppConstants.TEST_FILEPATH, false); // 하나의 테스트가 끝나고 테스트할 파일 다 지우기
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LibraryManagement buildLibrary(LibraryConfig libraryConfig) {
        return LibraryManagement.builder()
                .libraryConfig(libraryConfig)
                .build();
    }

    public LibraryConfig buildConfig(BookRepositoryConfig bookRepositoryConfig) {
        return LibraryConfig.builder()
                .bookRepositoryConfig(bookRepositoryConfig)
                .build();
    }

    @DisplayName("일반 모드에서 JSON 파일의 자동 등록 기능 검증")
    @Test
    public void loadInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        JSONArray jsonArray = jsonFileManager.getJSON(AppConstants.FILEPATH);
        assertThat(bookRepository.getSize()).isEqualTo(jsonArray.size());
    }


    @DisplayName("일반 모드에서 도서 등록, 조회 기능 검증")
    @Test
    public void addNGetInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThat(bookRepository.getSize()).isEqualTo(0);

        Book book1 = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);

        assertThat(bookRepository.findById(book1.getBookId()))
                .isNotPresent();

        library.add(book1);

        assertThat(bookRepository.findById(book1.getBookId()))
                .isPresent()
                .contains(book1);

        assertThat(bookRepository.getSize()).isEqualTo(1);

        Book book2 = new Book(2L, "스킨 인 더 게임", "나심 탈레브", 444, BookState.RENTAL_AVAILABLE);

        assertThat(bookRepository.findById(book2.getBookId()))
                .isNotPresent();

        library.add(book2);

        assertThat(bookRepository.getSize()).isEqualTo(2);
        assertThat(bookRepository.findById(book2.getBookId()))
                .isPresent()
                .contains(book2);

        // 변경된 사항이 JSON 파일에 제대로 작성되었는지 검증
        JSONArray jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        JSONObject object1 = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(object1, book1);

        JSONObject object2 = jsonFileManager.getJSONObject(jsonArray, 1);
        compareBookObject(object2, book2);
    }

    public void compareBookObject(JSONObject object, Book book) {

        assertThat(Long.parseLong(String.valueOf(object.get("book_id")))).isEqualTo(book.getBookId());
        assertThat(String.valueOf(object.get("title"))).isEqualTo(book.getTitle());
        assertThat(String.valueOf(object.get("author"))).isEqualTo(book.getAuthor());
        assertThat(Integer.valueOf(String.valueOf(object.get("page_num")))).isEqualTo(book.getPage_num());
        assertThat(BookState.valueOf(String.valueOf(object.get("state")))).isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("테스트 모드에서 도서 등록, 조회 기능 검증")
    @Test
    public void addNGetInTest() {

        BookRepository bookRepository  = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThat(bookRepository.getSize()).isEqualTo(0);

        Book book1 = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);

        assertThat(bookRepository.findById(book1.getBookId()))
                .isNotPresent();

        library.add(book1);

        assertThat(bookRepository.findById(book1.getBookId()))
                .isPresent()
                .contains(book1);

        assertThat(bookRepository.getSize()).isEqualTo(1);

        Book book2 = new Book(2L, "스킨 인 더 게임", "나심 탈레브", 444, BookState.RENTAL_AVAILABLE);

        assertThat(bookRepository.findById(book2.getBookId()))
                .isNotPresent();

        library.add(book2);

        assertThat(bookRepository.findById(book2.getBookId()))
                .isPresent()
                .contains(book2);

        assertThat(bookRepository.getSize()).isEqualTo(2);
    }

    @DisplayName("일반 모드에서 '대여 가능' 상태의 도서 대여 성공 검증")
    @Test
    public void borrowAvailableBookInGeneral() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .isEqualTo(book);

        library.borrow(book.getBookId());
        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTING);
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 대여 실패 검증")
    @Test
    public void borrowBookThatDoesNotExistInGeneral() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.borrow(10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 '대여중' 상태의 도서 대여 실패 검증")
    @Test
    public void borrowRentedBookInGeneral() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 대여중인 도서로 대여가 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTING);
    }

    @DisplayName("일반 모드에서 '분실됨' 상태의 도서 대여 실패 검증")
    @Test
    public void borrowLostBookInGeneral() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 분실된 도서로 대여가 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);
    }

    @DisplayName("일반 모드에서 정리중인 도서 대여 실패 검증")
    @Test
    public void borrowArrangingBookInGeneral() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서로 대여가 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("일반 모드에서 정리중인 도서 대여 10초 뒤 성공 검증")
    @Test
    public void borrowBookBeingArrangingAfter10SecInGeneral() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서로 대여가 불가합니다.");

        CompletableFuture<Void> future = library.runAsyncToWaitArrangement(book, 10000);

        assertTimeout(Duration.ofMillis(11000), () -> future.get());
        assertThat(future.isDone()).isTrue();

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 대여 성공 검증")
    @Test
    public void borrowAvailableBookInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);

        library.borrow(book.getBookId());
        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTING);
    }

    @DisplayName("테스트 모드에서 존재하지 않는 도서 대여 실패 검증")
    @Test
    public void borrowBookThatDoesNotExistInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.borrow(10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 대여중인 도서 대여 실패 검증")
    @Test
    public void borrowRentedBookInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 대여중인 도서로 대여가 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTING);
    }

    @DisplayName("테스트 모드에서 분실된 도서 대여 실패 검증")
    @Test
    public void borrowLostBookInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 분실된 도서로 대여가 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 정리중인 도서 대여 실패 검증")
    @Test
    public void borrowBookBeingArrangingInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서로 대여가 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("테스트 모드에서 정리중인 도서 10초 뒤 도서 대여 성공 검증")
    @Test
    public void borrowBookBeingArrangingAfter10SecInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        assertTimeout(Duration.ofMillis(11000), () -> {
            library.runAsyncToWaitArrangement(book, 10000).get();
        });

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("일반 모드에서 대여중인 도서 반납 성공 검증")
    @Test
    public void returnRentedBookInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        testLibrary.add(book);

        CountDownLatch latch = new CountDownLatch(1);

        testLibrary.returns(book.getBookId());

        // wait 10 sec because of waiting to complete arrangement
        CompletableFuture.runAsync(() -> {
            try {
                latch.await(11000, TimeUnit.MILLISECONDS);
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).thenRunAsync(() -> {
            System.out.println("Async is Complete");
        });

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.ARRANGEMENT.toString());

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);

        Book elem = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다."));

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(jsonObject, elem);

        assertThat(jsonObject.get("state")).isEqualTo(BookState.RENTAL_AVAILABLE.toString());
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 반납 실패 검증")
    @Test
    public void returnBookThatDoesNotExistInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.returns( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 반납 실패 검증")
    @Test
    public void returnAvailableBookInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);

        assertThatThrownBy(() -> library.returns(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 대여 가능한 도서로 반납이 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("일반 모드에서 분실된 도서 반납 성공 검증")
    @Test
    public void returnLostBookInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        testLibrary.add(book);

        CountDownLatch latch = new CountDownLatch(1);

        testLibrary.returns(book.getBookId());

        CompletableFuture.runAsync(() -> {
            try {
                latch.await(11000, TimeUnit.MILLISECONDS);
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).thenRunAsync(() -> {
            System.out.println("Async is Complete");
        });

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.ARRANGEMENT.toString());

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        Book elem = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다."));

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(jsonObject, elem);

        assertThat(jsonObject.get("state")).isEqualTo(BookState.RENTAL_AVAILABLE.toString());
    }

    @DisplayName("일반 모드에서 정리중인 도서 반납 실패 검증")
    @Test
    public void returnBookBeingArrangingInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        assertThatThrownBy(() -> library.returns(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서로 반납이 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("테스트 모드에서 존재하지 않은 도서 반납 실패 검증")
    @Test
    public void returnBookThatDoesNotExistInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.returns( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 정리중인 도서 반납 실패 검증")
    @Test
    public void returnBookBeingArrangingInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        assertThatThrownBy(() -> library.returns(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서로 반납이 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 반납 실패 검증")
    @Test
    public void returnAvailableBookInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);

        assertThatThrownBy(() -> library.returns(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 대여 가능한 도서로 반납이 불가합니다.");

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("테스트 모드에서 대여중인 도서 반납 성공 검증")
    @Test
    public void returnRentedBookInTest() {

        CountDownLatch latch = new CountDownLatch(1);

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        testLibrary.add(book);
        testLibrary.returns(book.getBookId());

        CompletableFuture.runAsync(() -> {
            try {
                latch.await(11000, TimeUnit.MILLISECONDS);
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).thenRunAsync(() -> {
            System.out.println("Async is Complete");
        });

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("테스트 모드에서 분실중인 도서 반납 성공 검증")
    @Test
    public void returnLostBookInTest() {

        CountDownLatch latch = new CountDownLatch(1);

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        testLibrary.add(book);
        testLibrary.returns(book.getBookId());

        CompletableFuture.runAsync(() -> {
            try {
                latch.await(11000, TimeUnit.MILLISECONDS);
                latch.countDown();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).thenRunAsync(() -> {
            System.out.println("Async is Complete");
        });

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 분실 성공 검증")
    @Test
    public void repostLostBookThatAvailableInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);
        library.lost(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.toString());
    }

    @DisplayName("일반 모드에서 대여중인 도서 분실 성공 검증")
    @Test
    public void reportLostBookThatBeingRentedInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        library.add(book);
        library.lost(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.toString());
    }

    @DisplayName("일반 모드에서 정리중인 도서 분실 성공 검증")
    @Test
    public void repostLostBookThatBeingArrangingInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);
        library.lost(book.getBookId()); // 분실 처리

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.toString());
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 분실 실패 검증")
    @Test
    public void repostLostBookThatDoesNotExistInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.lost( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 분실된 도서 분실 실패 검증")
    @Test
    public void validGeneralLostFailure2() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.toString());

        assertThatThrownBy(() -> library.lost(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 이미 분실된 도서입니다.");

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.toString());
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 분실 성공 검증")
    @Test
    public void repostLostBookThatAvailableInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);
        library.lost(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 대여중인 도서 분실 성공 검증")
    @Test
    public void repostLostBookBeingRentedInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        library.add(book);
        library.lost(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 정리중인 도서 분실 성공 검증")
    @Test
    public void repostLostBookBeingArrangingInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);
        library.lost(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 존재하지 않는 도서 분실 실패 검증")
    @Test
    public void repostLostBookThatDoesNotExistInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.lost( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 분실된 도서 분실 실패 검증")
    @Test
    public void repostLostBookThatBeingLostInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);

        assertThatThrownBy(() -> library.lost(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 이미 분실된 도서입니다.");
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 삭제 성공 검증")
    @Test
    public void deleteAvailableBookInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Long.parseLong(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBookId());

        library.delete(book.getBookId());

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 대여중인 도서 삭제 성공 검증")
    @Test
    public void deleteBookBeingRentedInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        library.add(book);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Long.parseLong(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBookId());

        library.delete(book.getBookId());

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 정리중인 도서 삭제 성공 검증")
    @Test
    public void deleteBookBeingArrangingInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Long.parseLong(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBookId());

        library.delete(book.getBookId());

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 분실된 도서 삭제 성공 검증")
    @Test
    public void deleteLostBookInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Long.parseLong(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBookId());

        library.delete(book.getBookId());

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 삭제 실패 검증")
    @Test
    public void deleteBookThatDoesNotExistInGeneral() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.delete( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");

        JSONArray jsonArray;
        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH); // Unexpected token END OF FILE at position 0.
        assertThat(jsonArray).isNull();
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 삭제 성공 검증")
    @Test
    public void deleteAvailableBookInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 대여중인 도서 삭제 성공 검증")
    @Test
    public void deleteBookBeingRentedInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 정리중인 도서 삭제 성공 검증")
    @Test
    public void deleteBookBeingArrangingInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 분실된 도서 삭제 성공 검증")
    @Test
    public void deleteLostBookInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 존재하지 않는 도서 삭제 실패 검증")
    @Test
    public void deleteBookThatDoesNotExistInTest() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        library = buildLibrary(libraryConfig);

        assertThat(bookRepository.getSize()).isEqualTo(0);

        assertThatThrownBy(() -> library.delete( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");

        assertThat(bookRepository.getSize()).isEqualTo(0);
    }

    @DisplayName("TestService 클래스를 이용한 도서 검색 성공 검증")
    @Test
    public void findByTitleUsingTestService() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book1 = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        Book book2 = new Book(2L, "스킨 인 더 게임", "나심 탈레브", 444, BookState.RENTAL_AVAILABLE);
        Book book3 = new Book(3L, "스프링, 한 권으로 끝낸다", "아무개", 888, BookState.RENTAL_AVAILABLE);

        testLibrary.add(book1);
        testLibrary.add(book2);
        testLibrary.add(book3);
        testLibrary.findByTitle("스프링");

        List<Book> searched = testLibrary.getSearched();
        assertThat(searched.size()).isEqualTo(2);
        assertThat(searched.get(0).getTitle()).isEqualTo(book1.getTitle());
        assertThat(searched.get(1).getTitle()).isEqualTo(book3.getTitle());

        searched.clear();
        testLibrary.findByTitle("멍청이");

        searched = testLibrary.getSearched();
        assertThat(searched.size()).isEqualTo(0);
    }
}
