import dev.course.config.LibraryConfig;
import dev.course.config.BookRepositoryConfig;
import dev.course.domain.AppConstants;
import dev.course.domain.Book;
import dev.course.domain.BookState;
import dev.course.exception.FuncFailureException;
import dev.course.manager.ConsoleManager;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * 도서 관리 애플리케이션의 일반 모드와 테스트 모드 테스트
 * 사용할 모드를 선택하는 과정에서, setter 메서드의 사용을 최대한 지양하고 싶었으나,,
 * 일반 모드에서 입력받는 BookRepositoryId 값을 통해 각 모드의 구현체를 사용하도록 구현했기 때문에
 * 현재의 설계 상에서는 테스트를 위해 setter 메서드로 값을 전달하는 것이 최선이라고 생각했습니다.
 *
 * 이 부분에 대해서 궁금한 점이
 * 1. 실무에서는 실제 서비스 환경과 테스트 환경이 다른 경우에, 매우 불가피한 상황이 아니라면 테스트 환경에서도 setter 사용을 무조건 지양하는 편인가요?
 *    테스트 환경에서드 사용을 지양한다면 그 이유는 좋은 객체지향 설계가 아니기 때문일까요?
 *
 * 2. 테스트 코드를 작성하면서 가장 어려움을 겪었던 부분은, BookRepository의 구현체를 적용하여 LibraryManagement 인스턴스를 객체지향적으로 설계하고 받아오는 부분이었습니다.
 *    만약 제가 구현한 아키텍처에서 테스트를 위해 무조건 setter 메서드를 사용해야 한다면, 이는 아키텍처 설계가 객체지향적이지 않기 떄문일까요?
 *
 * 3. 테스트 코드를 작성하면서, 한 번에 테스트하고자 하는 로직(?)의 범위가 작을수록 좋다는 것을 적용하고자 했습니다.
 *    그런데 작성하다보니, 도서의 기능에 비해 너무 많은 테스트 코드가 만들어진 것 같다는 생각을 했습니다.
 *    테스트 코드를 작성할 때 테스트하고자 하는 번위를 어떤 식으로 설정하는 것이 좋은지에 대한 멘토님의 기준이 궁금합니다.
 */

public class LibraryAppFeatureTest {

    private LibraryConfig libraryConfig;
    private LibraryManagement library;
    private FileWriter writer;
    private FileOutputStream fileOutputStream;
    private TestBookRepositoryConfig testBookRepositoryConfig;
    private JSONFileManager jsonFileManager;

    /**
     * 테스트용 JSON 파일을 사용해서 테스트를 진행했음
     * 테스트 상에서는 도서가 반납처리된 후, 5분 뒤에 대여가 가능하다는 검증을 간단히 10초로 진행했음
     * 도서 제목으로 검색을 하는 로직 상에서, 일반 모드와 테스트 모드의 차이가 없기 때문에 하나의 테스트로 검증했음
     * 대부분의 일반 모드와 테스트 모드의 로직은 비슷하나, JSON 파일에서 읽어오고 (load), JSON 파일에 작성되는지 (update) 추가로 검증했음
     */

    static class TestBookRepositoryConfig extends BookRepositoryConfig {

        private BookRepository bookRepository;

        private TestBookRepositoryConfig(int BookRepositoryId) {
            super(BookRepositoryId);
        }

        public void setBookRepository(BookRepository BookRepository) {
            this.bookRepository = BookRepository;
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

            if (book.getState().equals(BookState.RENTAL_AVAILABLE)) {
                throw new FuncFailureException("[System] 대여 가능한 도서로 반납이 불가합니다.\n");
            } else if (book.getState().equals(BookState.RENTING) || book.getState().equals(BookState.LOST)) {
                Book returned = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.ARRANGEMENT);
                this.bookRepository.add(returned);
                afterDelayArrangementComplete(returned, 10000);
            } else {
                throw new FuncFailureException("[System] 정리중인 도서로 반납이 불가합니다.\n");
            }

            System.out.println("[System] 도서 반납 처리가 완료되었습니다.\n");
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

    public LibraryConfig buildConfig(BookRepositoryConfig bookRepositoryConfig, ConsoleManager consoleManager) {
        return LibraryConfig.builder()
                .bookRepositoryConfig(bookRepositoryConfig)
                .consoleManager(consoleManager)
                .build();
    }

    @DisplayName("일반 모드에서 JSON 파일의 자동 등록 기능 검증")
    @Test
    public void generalAutoRegister() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        JSONArray jsonArray = jsonFileManager.getJSON(AppConstants.FILEPATH);
        assertThat(bookRepository.getSize()).isEqualTo(jsonArray.size());
    }


    @DisplayName("일반 모드에서 도서 등록, 조회 기능 검증")
    @Test
    public void generalAddNGet() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestAddNGet() {

        BookRepository bookRepository  = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralBorrowSuccess1() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralBorrowFailure1() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.borrow(10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 '대여중' 상태의 도서 대여 실패 검증")
    @Test
    public void validGeneralBorrowFailure2() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralBorrowFailure3() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralBorrowFailure4() throws FuncFailureException {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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

    @DisplayName("일반 모드에서 정리중인 도서 대여 5분 뒤 성공 검증")
    @Test
    public void validGeneralBorrowSuccess2() throws FuncFailureException {

        CountDownLatch lock = new CountDownLatch(1);

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);

        assertThatThrownBy(() -> library.borrow(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서로 대여가 불가합니다.");

        library.afterDelayArrangementComplete(book, 10000);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
            lock.countDown();
            assertThat(bookRepository.findById(book.getBookId()))
                    .isPresent()
                    .get()
                    .extracting(Book::getState)
                    .isEqualTo(BookState.RENTAL_AVAILABLE);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 대여 성공 검증")
    @Test
    public void validTestBorrowSuccess() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestBorrowFailure1() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.borrow(10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 대여중인 도서 대여 실패 검증")
    @Test
    public void validTestBorrowFailure2() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestBorrowFailure3() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestBorrowFailure4() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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

    @DisplayName("테스트 모드에서 정리중인 도서 5분 뒤 도서 대여 성공 검증")
    @Test
    public void validTestBorrowSuccess2() {

        CountDownLatch lock = new CountDownLatch(1);

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);
        library.afterDelayArrangementComplete(book, 10000);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
            lock.countDown();

            assertThat(bookRepository.findById(book.getBookId()))
            .isPresent()
            .get()
            .extracting(Book::getState)
            .isEqualTo(BookState.RENTAL_AVAILABLE);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("일반 모드에서 대여중인 도서 반납 성공 검증")
    @Test
    public void validGeneralReturnSuccess1() {

        CountDownLatch lock = new CountDownLatch(1);

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        testLibrary.add(book);
        testLibrary.returns(book.getBookId());

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
            lock.await(11000, TimeUnit.MILLISECONDS);
            lock.countDown();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Book elem = bookRepository.findById(book.getBookId())
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다."));

        jsonArray = jsonFileManager.getJSON(AppConstants.TEST_FILEPATH);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(jsonObject, elem);

        assertThat(jsonObject.get("state")).isEqualTo(BookState.RENTAL_AVAILABLE.toString());
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 반납 실패 검증")
    @Test
    public void validGeneralReturnFailure1() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.returns( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 반납 실패 검증")
    @Test
    public void validGeneralReturnFailure2() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralReturnSuccess2() {

        CountDownLatch lock = new CountDownLatch(1);

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        testLibrary.add(book);
        testLibrary.returns(book.getBookId());

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
            lock.await(11000, TimeUnit.MILLISECONDS);
            lock.countDown();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
    public void validGeneralReturnFailure3() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestReturnFailure1() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.returns( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 정리중인 도서 반납 실패 검증")
    @Test
    public void validTestReturnFailure() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestReturnFailure2() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestReturnSuccess1() {

        CountDownLatch lock = new CountDownLatch(1);

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        testLibrary.add(book);
        testLibrary.returns(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
            lock.countDown();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("테스트 모드에서 분실중인 도서 반납 성공 검증")
    @Test
    public void validTestReturnSuccess2() {

        CountDownLatch lock = new CountDownLatch(1);

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        TestLibraryManagement testLibrary = new TestLibraryManagement(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        testLibrary.add(book);
        testLibrary.returns(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.ARRANGEMENT);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
            lock.countDown();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(bookRepository.findById(book.getBookId()))
                .isPresent()
                .get()
                .extracting(Book::getState)
                .isEqualTo(BookState.RENTAL_AVAILABLE);
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 분실 성공 검증")
    @Test
    public void validGeneralLostSuccess1() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralLostSuccess2() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralLostSuccess3() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralLostFailure1() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestLostSuccess1() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestLostSuccess2() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestLostSuccess3() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestLostFailure1() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.lost( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 분실된 도서 분실 실패 검증")
    @Test
    public void validTestLostFailure2() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);

        assertThatThrownBy(() -> library.lost(book.getBookId()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 이미 분실된 도서입니다.");
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 삭제 성공 검증")
    @Test
    public void validGeneralDeleteSuccess1() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralDeleteSuccess2() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralDeleteSuccess3() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralDeleteSuccess4() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validGeneralDeleteFailure() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
    public void validTestDeleteSuccess1() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTAL_AVAILABLE);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 대여중인 도서 삭제 성공 검증")
    @Test
    public void validTestDeleteSuccess2() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.RENTING);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 정리중인 도서 삭제 성공 검증")
    @Test
    public void validTestDeleteSuccess3() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 분실된 도서 삭제 성공 검증")
    @Test
    public void validTestDeleteSuccess4() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1L, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(book);
        library.delete(book.getBookId());

        assertThat(bookRepository.findById(book.getBookId()))
                .isNotPresent();
    }

    @DisplayName("테스트 모드에서 존재하지 않는 도서 삭제 실패 검증")
    @Test
    public void validTestDeleteFailure() {

        BookRepository bookRepository = new TestBookRepository();
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
        library = buildLibrary(libraryConfig);

        assertThat(bookRepository.getSize()).isEqualTo(0);

        assertThatThrownBy(() -> library.delete( 10L))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서는 존재하지 않습니다.");

        assertThat(bookRepository.getSize()).isEqualTo(0);
    }

    @DisplayName("Mock 객체인 MockBookRepository를 이용한 도서 검색 성공과 존재하지 않는 도서 검색 실패 검증")
    @Test
    public void testFindByTitle() {

        BookRepository bookRepository = new GeneralBookRepository(jsonFileManager, AppConstants.TEST_FILEPATH);
        testBookRepositoryConfig.setBookRepository(bookRepository);

        libraryConfig = buildConfig(testBookRepositoryConfig, null);
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
