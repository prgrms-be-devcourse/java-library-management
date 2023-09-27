package java_library_management.test;

import java_library_management.config.CallbackConfig;
import java_library_management.config.LibraryConfig;
import java_library_management.config.ModeConfig;
import java_library_management.domain.Book;
import java_library_management.domain.BookState;
import java_library_management.exception.FuncFailureException;
import java_library_management.manager.ConsoleManager;
import java_library_management.manager.JSONFileManager;
import java_library_management.repository.General;
import java_library_management.repository.Mode;
import java_library_management.repository.Tests;
import java_library_management.repository.mock.MockMode;
import java_library_management.service.LibraryManagement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.*;

/**
 * 도서 관리 애플리케이션의 일반 모드와 테스트 모드 테스트
 * 사용할 모드를 선택하는 과정에서, setter 메서드의 사용을 최대한 지양하고 싶었으나,,
 * 일반 모드에서 입력받는 modeId 값을 통해 각 모드의 구현체를 사용하도록 구현했기 때문에
 * 현재의 설계 상에서는 테스트를 위해 setter 메서드로 값을 전달하는 것이 최선이라고 생각했습니다.
 *
 * 이 부분에 대해서 궁금한 점이
 * 1. 실무에서는 실제 서비스 환경과 테스트 환경이 다른 경우에, 매우 불가피한 상황이 아니라면 테스트 환경에서도 setter 사용을 무조건 지양하는 편인가요?
 *    테스트 환경에서드 사용을 지양한다면 그 이유는 좋은 객체지향 설계가 아니기 때문일까요?
 *
 * 2. 테스트 코드를 작성하면서 가장 어려움을 겪었던 부분은, Mode의 구현체를 적용하여 LibraryManagement 인스턴스를 객체지향적으로 설계하고 받아오는 부분이었습니다.
 *    만약 제가 구현한 아키텍처에서 테스트를 위해 무조건 setter 메서드를 사용해야 한다면, 이는 아키텍처 설계가 객체지향적이지 않기 떄문일까요?
 *
 * 3. 테스트 코드를 작성하면서, 한 번에 테스트하고자 하는 로직(?)의 범위가 작을수록 좋다는 것을 적용하고자 했습니다.
 *    그런데 작성하다보니, 도서의 기능에 비해 너무 많은 테스트 코드가 만들어진 것 같다는 생각을 했습니다.
 *    테스트 코드를 작성할 때 테스트하고자 하는 번위를 어떤 식으로 설정하는 것이 좋은지에 대한 멘토님의 기준이 궁금합니다.
 */

public class Valid {

    Map<Integer, Book> bookMap = new TreeMap<>();
    CallbackConfig callbackConfig;
    LibraryConfig libraryConfig;
    LibraryManagement library;
    Mode mode;
    String filePath;
    FileWriter writer;
    FileOutputStream fileOutputStream;
    TestModeConfig testModeConfig;
    JSONFileManager jsonFileManager;

    /**
     * 테스트용 JSON 파일을 사용해서 테스트를 진행했음
     * 테스트 상에서는 도서가 반납처리된 후, 5분 뒤에 대여가 가능하다는 검증을 간단히 10초로 진행했음
     * 도서 제목으로 검색을 하는 로직 상에서, 일반 모드와 테스트 모드의 차이가 없기 때문에 하나의 테스트로 검증했음
     * 대부분의 일반 모드와 테스트 모드의 로직은 비슷하나, JSON 파일에서 읽어오고 (load), JSON 파일에 작성되는지 (update) 추가로 검증했음
     */

    static class TestModeConfig extends ModeConfig {

        private Mode mode;

        private TestModeConfig(int modeId) {
            super(modeId);
        }

        public void setMode(Mode mode) {
            this.mode = mode;
        }

        public Mode getMode() {
            return this.mode;
        }
    }

    @BeforeEach
    public void init() {
        try {
            bookMap.clear();
            filePath = "src/java_library_management/resources/TestBook.json"; // 테스트용 JSON 파일을 사용하여 테스트 진행
            writer = new FileWriter(filePath);
            testModeConfig = new TestModeConfig(0);
            jsonFileManager = new JSONFileManager();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    public void after() {
        try {
            fileOutputStream = new FileOutputStream(filePath, false); // 하나의 테스트가 끝나고 테스트할 파일 다 지우기
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public LibraryManagement buildLibrary(LibraryConfig libraryConfig) {
        return LibraryManagement.builder()
                .libraryConfig(libraryConfig)
                .build();
    }

    public LibraryConfig buildConfig(ModeConfig modeConfig, CallbackConfig callbackConfig, ConsoleManager consoleManager) {
        return LibraryConfig.builder()
                .modeConfig(modeConfig)
                .callbackConfig(callbackConfig)
                .consoleManager(consoleManager)
                .build();
    }

    @DisplayName("일반 모드에서 JSON 파일의 자동 등록 기능 검증")
    @Test
    public void validGeneralAutoRegister() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThat(bookMap.size()).isEqualTo(0);

        Book book1 = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        Book book2 = new Book(2, "스킨 인 더 게임", "나심 탈레브", 444, BookState.AVAILABLE);

        library.add(bookMap, book1);
        library.add(bookMap, book2);

        assertThat(bookMap.size()).isEqualTo(2);
        assertThat(jsonFileManager.getJSON(filePath)).isNull(); // Unexpected token END OF FILE at position 0.

        library.update(bookMap, filePath); // JSON 파일에 등록함

        bookMap.clear();
        assertThat(bookMap.size()).isEqualTo(0);

        assertThat(jsonFileManager.getJSON(filePath)).isNotNull();

        library.load(bookMap, filePath); // JSON 파일에서 읽어와 bookMap 저장함

        assertThat(bookMap.size()).isEqualTo(2);

        JSONArray jsonArray = jsonFileManager.getJSON(filePath);

        JSONObject object1 = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(object1, book1);

        JSONObject object2 = jsonFileManager.getJSONObject(jsonArray, 1);
        compareBookObject(object2, book2);
    }


    @DisplayName("일반 모드에서 도서 등록, 조회 기능 검증")
    @Test
    public void validGeneralAddNGet() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThat(bookMap.size()).isEqualTo(0);

        Book obj1 = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        assertThat(bookMap).doesNotContainKey(obj1.getBook_id());
        assertThat(bookMap).doesNotContainValue(obj1);

        library.add(bookMap, obj1);

        assertThat(bookMap.size()).isEqualTo(1);
        assertThat(bookMap).containsKey(obj1.getBook_id());
        assertThat(bookMap).containsValue(obj1);

        Book obj2 = new Book(2, "스킨 인 더 게임", "나심 탈레브", 444, BookState.AVAILABLE);
        assertThat(bookMap).doesNotContainKey(obj2.getBook_id());
        assertThat(bookMap).doesNotContainValue(obj2);

        library.add(bookMap, obj2);

        assertThat(bookMap.size()).isEqualTo(2);
        assertThat(bookMap).containsKey(obj2.getBook_id());
        assertThat(bookMap).containsValue(obj2);

        library.update(bookMap, filePath); // JSON 파일에 등록함

        // 변경된 사항이 JSON 파일에 제대로 작성되었는지 검증
        JSONArray jsonArray = jsonFileManager.getJSON(filePath);
        JSONObject object1 = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(object1, obj1);

        JSONObject object2 = jsonFileManager.getJSONObject(jsonArray, 1);
        compareBookObject(object2, obj2);
    }

    /**
    public JSONObject getJSONObject(JSONArray jsonArray, int idx) {
        return (JSONObject) jsonArray.get(idx);
    }

    public JSONArray getJSONFile(String filePath) {

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            JSONObject jsonObject = (JSONObject) parser.parse(reader); // Unexpected token END OF FILE at position 0.
            if (jsonObject == null) return null;
            JSONArray jsonArray = (JSONArray) jsonObject.get("book");
            if (jsonArray == null) return null;
            return jsonArray;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {}
        return null;
    }
    */

    public void compareBookObject(JSONObject object, Book book) {

        assertThat(Integer.parseInt(String.valueOf(object.get("book_id")))).isEqualTo(book.getBook_id());
        assertThat(String.valueOf(object.get("title"))).isEqualTo(book.getTitle());
        assertThat(String.valueOf(object.get("author"))).isEqualTo(book.getAuthor());
        assertThat(Integer.valueOf(String.valueOf(object.get("page_num")))).isEqualTo(book.getPage_num());
        assertThat(BookState.valueOfState(String.valueOf(object.get("state")))).isEqualTo(BookState.AVAILABLE);
    }

    @DisplayName("테스트 모드에서 도서 등록, 조회 기능 검증")
    @Test
    public void validTestAddNGet() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThat(bookMap.size()).isEqualTo(0);

        Book obj1 = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        assertThat(bookMap).doesNotContainKey(obj1.getBook_id());
        assertThat(bookMap).doesNotContainValue(obj1);

        library.add(bookMap, obj1);

        assertThat(bookMap.size()).isEqualTo(1);
        assertThat(bookMap).containsKey(obj1.getBook_id());
        assertThat(bookMap).containsValue(obj1);

        Book obj2 = new Book(2, "스킨 인 더 게임", "나심 탈레브", 444, BookState.AVAILABLE);
        assertThat(bookMap).doesNotContainKey(obj2.getBook_id());
        assertThat(bookMap).doesNotContainValue(obj2);

        library.add(bookMap, obj2);

        assertThat(bookMap.size()).isEqualTo(2);
        assertThat(bookMap).containsKey(obj2.getBook_id());
        assertThat(bookMap).containsValue(obj2);
    }

    @DisplayName("일반 모드에서 '대여 가능' 상태의 도서 대여 성공 검증")
    @Test
    public void validGeneralBorrowSuccess1() throws FuncFailureException {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);
        Book obj = bookMap.get(book.getBook_id());

        library.borrow(bookMap, book.getBook_id());
        assertThat(obj.getState()).isEqualTo(BookState.LOAN);
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 대여 실패 검증")
    @Test
    public void validGeneralBorrowFailure1() throws FuncFailureException {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.borrow(bookMap, 10))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서가 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 '대여중' 상태의 도서 대여 실패 검증")
    @Test
    public void validGeneralBorrowFailure2() throws FuncFailureException {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        book.setState(BookState.LOAN);
        library.add(bookMap, book);
        Book obj = bookMap.get(book.getBook_id());

        assertThatThrownBy(() -> library.borrow(bookMap, obj.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 이미 대여중인 도서입니다.");
        assertThat(obj.getState()).isEqualTo(BookState.LOAN);
    }

    @DisplayName("일반 모드에서 '분실됨' 상태의 도서 대여 실패 검증")
    @Test
    public void validGeneralBorrowFailure3() throws FuncFailureException {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOST);

        library.add(bookMap, book);

        Book obj = bookMap.get(book.getBook_id());
        assertThatThrownBy(() -> library.borrow(bookMap, obj.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 분실된 도서입니다.");
        assertThat(obj.getState()).isEqualTo(BookState.LOST);
    }

    @DisplayName("일반 모드에서 정리중인 도서 대여 실패 검증")
    @Test
    public void validGeneralBorrowFailure4() throws FuncFailureException {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);

        library.add(bookMap, book);

        Book obj = bookMap.get(book.getBook_id());
        assertThatThrownBy(() -> library.borrow(bookMap, obj.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서입니다.");
        assertThat(obj.getState()).isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("일반 모드에서 정리중인 도서 대여 5분 뒤 성공 검증")
    @Test
    public void validGeneralBorrowSuccess2() throws FuncFailureException {

        CountDownLatch lock = new CountDownLatch(1);

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);

        library.add(bookMap, book);

        Book elem = bookMap.get(book.getBook_id());

        Timer m = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                book.setState(BookState.AVAILABLE);
                mode.update(bookMap, filePath);
                library.borrow(bookMap, book.getBook_id());
                assertThat(elem.getState()).isEqualTo(BookState.LOAN);
            }
        };
        m.schedule(task, 10000);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 대여 성공 검증")
    @Test
    public void validTestBorrowSuccess() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);
        Book obj = bookMap.get(book.getBook_id());

        library.borrow(bookMap, book.getBook_id());
        assertThat(obj.getState()).isEqualTo(BookState.LOAN);
    }

    @DisplayName("테스트 모드에서 존재하지 않는 도서 대여 실패 검증")
    @Test
    public void validTestBorrowFailure1() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.borrow(bookMap, 10))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서가 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 대여중인 도서 대여 실패 검증")
    @Test
    public void validTestBorrowFailure2() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        book.setState(BookState.LOAN);
        library.add(bookMap, book);
        Book obj = bookMap.get(book.getBook_id());

        assertThatThrownBy(() -> library.borrow(bookMap, obj.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 이미 대여중인 도서입니다.");
        assertThat(obj.getState()).isEqualTo(BookState.LOAN);
    }

    @DisplayName("테스트 모드에서 분실된 도서 대여 실패 검증")
    @Test
    public void validTestBorrowFailure3() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        book.setState(BookState.LOST);
        library.add(bookMap, book);
        Book obj = bookMap.get(book.getBook_id());

        assertThatThrownBy(() -> library.borrow(bookMap, obj.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 분실된 도서입니다.");
        assertThat(obj.getState()).isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 정리중인 도서 대여 실패 검증")
    @Test
    public void validTestBorrowFailure4() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        book.setState(BookState.ARRANGEMENT);
        library.add(bookMap, book);
        Book obj = bookMap.get(book.getBook_id());

        assertThatThrownBy(() -> library.borrow(bookMap, obj.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 정리중인 도서입니다.");
        assertThat(obj.getState()).isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("테스트 모드에서 정리중인 도서 5분 뒤 도서 대여 성공 검증")
    @Test
    public void validTestBorrowSuccess2() {

        CountDownLatch lock = new CountDownLatch(1);

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        book.setState(BookState.ARRANGEMENT);
        library.add(bookMap, book);
        Book elem = bookMap.get(book.getBook_id());

        Timer m = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                book.setState(BookState.AVAILABLE);
                library.borrow(bookMap, book.getBook_id());
                assertThat(elem.getState()).isEqualTo(BookState.LOAN);
            }
        };
        m.schedule(task, 10000);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 1. 대여 중인 도서 등록
     * 2. 등록된 도서를 JSON 입력
     * 3. 반납과 update 수행
     * 4. 도서 정리중으로 변경되었는지 검증
     * 5. JSON 도서 정리중 검증
     * 6. 도서 대여 가능으로 변경되었는지 검증
     * 7. JSON 도서 대여 가능 검증
     */
    @DisplayName("일반 모드에서 대여중인 도서 반납 성공 검증")
    @Test
    public void validGeneralReturnSuccess1() {

        CountDownLatch lock = new CountDownLatch(1);

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOAN);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // bookMap -> JSON 등록

        Book elem = bookMap.get(book.getBook_id());
        assertThat(elem.getState()).isEqualTo(BookState.LOAN);
        library.returns(bookMap, book.getBook_id(), filePath, 10000, library::update);
        library.update(bookMap, filePath);
        assertThat(elem.getState()).isEqualTo(BookState.ARRANGEMENT);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.ARRANGEMENT.getState());

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(jsonObject, elem);

        assertThat(jsonObject.get("state")).isEqualTo(BookState.AVAILABLE.getState());
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 반납 실패 검증")
    @Test
    public void validGeneralReturnFailure1() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.returns(bookMap, 10, filePath, 10000, null))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서가 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 반납 실패 검증")
    @Test
    public void validGeneralReturnFailure2() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // bookMap -> JSON 등록

        assertThatThrownBy(() -> library.returns(bookMap, book.getBook_id(), filePath, 10000, null))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 원래 대여가 가능한 도서입니다.");
        assertThat(book.getState()).isEqualTo(BookState.AVAILABLE);
    }

    @DisplayName("일반 모드에서 분실된 도서 반납 성공 검증")
    @Test
    public void validGeneralReturnSuccess2() {

        CountDownLatch lock = new CountDownLatch(1);

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // bookMap -> JSON 등록

        Book elem = bookMap.get(book.getBook_id());
        assertThat(elem.getState()).isEqualTo(BookState.LOST);
        library.returns(bookMap, book.getBook_id(), filePath, 10000, library::update);
        library.update(bookMap, filePath);
        assertThat(elem.getState()).isEqualTo(BookState.ARRANGEMENT);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.ARRANGEMENT.getState());

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        compareBookObject(jsonObject, elem);

        assertThat(jsonObject.get("state")).isEqualTo(BookState.AVAILABLE.getState());
    }

    @DisplayName("일반 모드에서 정리중인 도서 반납 실패 검증")
    @Test
    public void validGeneralReturnFailure3() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // bookMap -> JSON 등록

        assertThatThrownBy(() -> library.returns(bookMap, book.getBook_id(), filePath, 10000, null))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 대여되지 않은 도서로 반납이 불가합니다.");
        assertThat(book.getState()).isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("테스트 모드에서 존재하지 않은 도서 반납 실패 검증")
    @Test
    public void validTestReturnFailure1() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.returns(bookMap, 10, filePath, 10000, null))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서가 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 정리중인 도서 반납 실패 검증")
    @Test
    public void validTestReturnFailure() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(bookMap, book);

        assertThatThrownBy(() -> library.returns(bookMap, book.getBook_id(), filePath, 10000, null))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 대여되지 않은 도서로 반납이 불가합니다.");
        assertThat(book.getState()).isEqualTo(BookState.ARRANGEMENT);
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 반납 실패 검증")
    @Test
    public void validTestReturnFailure2() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);

        assertThatThrownBy(() -> library.returns(bookMap, book.getBook_id(), filePath, 10000, null))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 원래 대여가 가능한 도서입니다.");
        assertThat(book.getState()).isEqualTo(BookState.AVAILABLE);
    }

    @DisplayName("테스트 모드에서 대여중인 도서 반납 성공 검증")
    @Test
    public void validTestReturnSuccess1() {

        CountDownLatch lock = new CountDownLatch(1);

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOAN);
        library.add(bookMap, book);

        Book elem = bookMap.get(book.getBook_id());
        assertThat(elem.getState()).isEqualTo(BookState.LOAN);
        library.returns(bookMap, book.getBook_id(), null, 10000, null);
        assertThat(elem.getState()).isEqualTo(BookState.ARRANGEMENT);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertThat(elem.getState()).isEqualTo(BookState.AVAILABLE);
    }

    @DisplayName("테스트 모드에서 분실중인 도서 반납 성공 검증")
    @Test
    public void validTestReturnSuccess2() {

        CountDownLatch lock = new CountDownLatch(1);

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(bookMap, book);

        Book elem = bookMap.get(book.getBook_id());
        assertThat(elem.getState()).isEqualTo(BookState.LOST);
        library.returns(bookMap, book.getBook_id(), null, 10000, null);
        assertThat(elem.getState()).isEqualTo(BookState.ARRANGEMENT);

        try {
            lock.await(11000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertThat(elem.getState()).isEqualTo(BookState.AVAILABLE);
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 분실 성공 검증")
    @Test
    public void validGeneralLostSuccess1() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // update

        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.AVAILABLE);
        library.lost(bookMap, book.getBook_id()); // 분실 처리
        library.update(bookMap, filePath);
        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOST);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.getState());
    }

    @DisplayName("일반 모드에서 대여중인 도서 분실 성공 검증")
    @Test
    public void validGeneralLostSuccess2() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOAN);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // update

        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOAN);
        library.lost(bookMap, book.getBook_id()); // 분실 처리
        library.update(bookMap, filePath);
        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOST);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.getState());
    }

    @DisplayName("일반 모드에서 정리중인 도서 분실 성공 검증")
    @Test
    public void validGeneralLostSuccess3() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // update

        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.ARRANGEMENT);
        library.lost(bookMap, book.getBook_id()); // 분실 처리
        library.update(bookMap, filePath);
        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOST);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.getState());
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 분실 실패 검증")
    @Test
    public void validGeneralLostFailure1() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.lost(bookMap, 10))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서가 존재하지 않습니다.");
    }

    @DisplayName("일반 모드에서 분실된 도서 분실 실패 검증")
    @Test
    public void validGeneralLostFailure2() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(bookMap, book);
        library.update(bookMap, filePath); // update

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.getState());

        assertThatThrownBy(() -> library.lost(bookMap, book.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 이미 분실 처리된 도서입니다.");
        library.update(bookMap, filePath);

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.get("state")).isEqualTo(BookState.LOST.getState());
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 분실 성공 검증")
    @Test
    public void validTestLostSuccess1() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);

        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.AVAILABLE);
        library.lost(bookMap, book.getBook_id()); // 분실 처리
        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 대여중인 도서 분실 성공 검증")
    @Test
    public void validTestLostSuccess2() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOAN);
        library.add(bookMap, book);

        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOAN);
        library.lost(bookMap, book.getBook_id()); // 분실 처리
        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 정리중인 도서 분실 성공 검증")
    @Test
    public void validTestLostSuccess3() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(bookMap, book);

        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.ARRANGEMENT);
        library.lost(bookMap, book.getBook_id()); // 분실 처리
        assertThat(bookMap.get(book.getBook_id()).getState()).isEqualTo(BookState.LOST);
    }

    @DisplayName("테스트 모드에서 존재하지 않는 도서 분실 실패 검증")
    @Test
    public void validTestLostFailure1() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThatThrownBy(() -> library.lost(bookMap, 10))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 해당 도서가 존재하지 않습니다.");
    }

    @DisplayName("테스트 모드에서 분실된 도서 분실 실패 검증")
    @Test
    public void validTestLostFailure2() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(bookMap, book);

        assertThatThrownBy(() -> library.lost(bookMap, book.getBook_id()))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 이미 분실 처리된 도서입니다.");
    }

    @DisplayName("일반 모드에서 대여 가능한 도서 삭제 성공 검증")
    @Test
    public void validGeneralDeleteSuccess1() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);
        library.update(bookMap, filePath);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Integer.parseInt(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBook_id());

        library.delete(bookMap, book.getBook_id());
        library.update(bookMap, filePath);

        jsonArray = jsonFileManager.getJSON(filePath);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 대여중인 도서 삭제 성공 검증")
    @Test
    public void validGeneralDeleteSuccess2() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOAN);
        library.add(bookMap, book);
        library.update(bookMap, filePath);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Integer.parseInt(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBook_id());

        library.delete(bookMap, book.getBook_id());
        library.update(bookMap, filePath);

        jsonArray = jsonFileManager.getJSON(filePath);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 정리중인 도서 삭제 성공 검증")
    @Test
    public void validGeneralDeleteSuccess3() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(bookMap, book);
        library.update(bookMap, filePath);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Integer.parseInt(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBook_id());

        library.delete(bookMap, book.getBook_id());
        library.update(bookMap, filePath);

        jsonArray = jsonFileManager.getJSON(filePath);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 분실된 도서 삭제 성공 검증")
    @Test
    public void validGeneralDeleteSuccess4() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(bookMap, book);
        library.update(bookMap, filePath);

        JSONArray jsonArray;
        JSONObject jsonObject;

        jsonArray = jsonFileManager.getJSON(filePath);
        jsonObject = jsonFileManager.getJSONObject(jsonArray, 0);
        assertThat(jsonObject.toString()).isNotNull();
        assertThat(Integer.parseInt(String.valueOf(jsonObject.get("book_id")))).isEqualTo(book.getBook_id());

        library.delete(bookMap, book.getBook_id());
        library.update(bookMap, filePath);

        jsonArray = jsonFileManager.getJSON(filePath);
        assertThat(jsonArray.size()).isEqualTo(0);
    }

    @DisplayName("일반 모드에서 존재하지 않는 도서 삭제 실패 검증")
    @Test
    public void validGeneralDeleteFailure() {

        mode = new General();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(
                (map, path) -> mode.load(bookMap, filePath),
                (map, path) -> mode.update(bookMap, filePath)
        );

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThat(bookMap.size()).isEqualTo(0);

        assertThatThrownBy(() -> mode.delete(bookMap, 10))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 존재하지 않는 도서번호 입니다.");

        assertThat(bookMap.size()).isEqualTo(0);

        JSONArray jsonArray;
        jsonArray = jsonFileManager.getJSON(filePath); // Unexpected token END OF FILE at position 0.
        assertThat(jsonArray).isNull();
    }

    @DisplayName("테스트 모드에서 대여 가능한 도서 삭제 성공 검증")
    @Test
    public void validTestDeleteSuccess1() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        library.add(bookMap, book);

        assertThat(bookMap.size()).isEqualTo(1);
        assertThat(bookMap).containsKey(book.getBook_id());
        assertThat(bookMap).containsValue(book);

        library.delete(bookMap, book.getBook_id());

        assertThat(bookMap.size()).isEqualTo(0);
        assertThat(bookMap).doesNotContainKey(book.getBook_id());
        assertThat(bookMap).doesNotContainValue(book);
    }

    @DisplayName("테스트 모드에서 대여중인 도서 삭제 성공 검증")
    @Test
    public void validTestDeleteSuccess2() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOAN);
        library.add(bookMap, book);

        assertThat(bookMap.size()).isEqualTo(1);
        assertThat(bookMap).containsKey(book.getBook_id());
        assertThat(bookMap).containsValue(book);

        library.delete(bookMap, book.getBook_id());

        assertThat(bookMap.size()).isEqualTo(0);
        assertThat(bookMap).doesNotContainKey(book.getBook_id());
        assertThat(bookMap).doesNotContainValue(book);
    }

    @DisplayName("테스트 모드에서 정리중인 도서 삭제 성공 검증")
    @Test
    public void validTestDeleteSuccess3() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.ARRANGEMENT);
        library.add(bookMap, book);

        assertThat(bookMap.size()).isEqualTo(1);
        assertThat(bookMap).containsKey(book.getBook_id());
        assertThat(bookMap).containsValue(book);

        library.delete(bookMap, book.getBook_id());

        assertThat(bookMap.size()).isEqualTo(0);
        assertThat(bookMap).doesNotContainKey(book.getBook_id());
        assertThat(bookMap).doesNotContainValue(book);
    }

    @DisplayName("테스트 모드에서 분실된 도서 삭제 성공 검증")
    @Test
    public void validTestDeleteSuccess4() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book = new Book(1, "토비의 스프링", "이일민", 999, BookState.LOST);
        library.add(bookMap, book);

        assertThat(bookMap.size()).isEqualTo(1);
        assertThat(bookMap).containsKey(book.getBook_id());
        assertThat(bookMap).containsValue(book);

        library.delete(bookMap, book.getBook_id());

        assertThat(bookMap.size()).isEqualTo(0);
        assertThat(bookMap).doesNotContainKey(book.getBook_id());
        assertThat(bookMap).doesNotContainValue(book);
    }

    @DisplayName("테스트 모드에서 존재하지 않는 도서 삭제 실패 검증")
    @Test
    public void validTestDeleteFailure() {

        mode = new Tests();
        testModeConfig.setMode(mode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        assertThat(bookMap.size()).isEqualTo(0);

        assertThatThrownBy(() -> mode.delete(bookMap, 10))
                .isInstanceOf(FuncFailureException.class)
                .hasMessageContaining("[System] 존재하지 않는 도서번호 입니다.");

        assertThat(bookMap.size()).isEqualTo(0);
    }

    @DisplayName("Mock 객체인 MockMode를 이용한 도서 검색 검증")
    @Test
    public void testFindByTitle() {

        MockMode mockMode = new MockMode();
        testModeConfig.setMode(mockMode);
        callbackConfig = new CallbackConfig(testModeConfig);
        callbackConfig.setCallback(null, null);

        libraryConfig = buildConfig(testModeConfig, callbackConfig, null);
        library = buildLibrary(libraryConfig);

        Book book1 = new Book(1, "토비의 스프링", "이일민", 999, BookState.AVAILABLE);
        Book book2 = new Book(2, "스킨 인 더 게임", "나심 탈레브", 444, BookState.AVAILABLE);
        Book book3 = new Book(3, "스프링, 한 권으로 끝낸다", "아무개", 888, BookState.AVAILABLE);

        library.add(bookMap, book1);
        library.add(bookMap, book2);
        library.add(bookMap, book3);

        library.findByTitle(bookMap, "스프링");

        List<Book> searched = mockMode.getSearched();
        assertThat(searched.size()).isEqualTo(2);
        assertThat(searched.get(0).getTitle()).isEqualTo(book1.getTitle());
        assertThat(searched.get(1).getTitle()).isEqualTo(book3.getTitle());
    }

}
