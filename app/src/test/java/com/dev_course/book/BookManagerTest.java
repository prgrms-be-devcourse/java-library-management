package com.dev_course.book;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.dev_course.book.BookManagerMessage.*;
import static org.assertj.core.api.Assertions.assertThat;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
abstract class BookManagerTest {
    protected abstract BookManager createBookManager();

    @Nested
    @Order(1)
    @DisplayName("도서 리스트 테스트")
    class TestGetBookList {
        @Test
        @DisplayName("초기 생성 시 도서 리스트는 비어 있어야 한다.")
        void testGetBookListBeforeInit() {
            // given
            BookManager bookManager = createBookManager();

            // when
            List<Book> bookList = bookManager.getBookList();

            // then
            assertThat(bookList).isEmpty();
        }

        @Test
        @DisplayName("init() 메서드 호출 시 도서 리스트는 init 데이터가 반영돼야 한다.")
        void testGetBookListAfterInit() {
            // given
            BookManager bookManager = createBookManager();
            List<Book> initData = new ArrayList<>();

            initData.add(new Book(1, "test1", "tester", 111, 1L));
            initData.add(new Book(2, "test2", "tester", 222, 2L));
            initData.add(new Book(3, "test3", "tester", 333, 3L));

            // when
            bookManager.init(initData);

            // then
            List<Book> bookList = bookManager.getBookList();

            assertThat(bookList).containsAll(initData);
        }
    }

    @Nested
    @Order(2)
    @DisplayName("도서 생성 테스트")
    class TestCreateBook {
        @Test
        @DisplayName("도서 생성 시 유일한 id의 도서가 추가돼야 한다")
        void testCreateWithUniqueId() {
            // given
            BookManager bookManager = createBookManager();

            // when
            bookManager.create("test1", "tester", 11);
            bookManager.create("test2", "tester", 22);

            // then
            List<Book> bookList = bookManager.getBookList();
            long idCount = bookList.stream().mapToInt(Book::getId).count();

            assertThat(idCount).isEqualTo(2);
        }

        @Test
        @DisplayName("데이터 로드 후 도서 생성 시 모든 도서는 유일한 id를 갖고 있어야 한다.")
        void testCreateWitUniqueIdAfterInit() {
            // given
            BookManager bookManager = createBookManager();
            List<Book> initData = new ArrayList<>();

            initData.add(new Book(1, "test1", "tester", 111, 1L));
            initData.add(new Book(5, "test2", "tester", 222, 2L));
            initData.add(new Book(10, "test3", "tester", 333, 3L));

            // when
            bookManager.init(initData);

            bookManager.create("test4", "tester", 11);
            bookManager.create("test5", "tester", 22);

            // then
            List<Book> bookList = bookManager.getBookList();
            long idCount = bookList.stream().mapToInt(Book::getId).count();

            assertThat(idCount).isEqualTo(5);
        }

        @Test
        @DisplayName("도서 생성에 성공하면 성공 메시지를 반환해야 한다.")
        void testCreateSuccess() {
            // given
            BookManager bookManager = createBookManager();

            // when
            String msg = bookManager.create("test1", "tester", 11);

            // then
            assertThat(msg).isEqualTo(SUCCESS_CREATE_BOOK.msg());
        }

        @Test
        @DisplayName("이미 추가된 제목의 도서 생성 시 실패 메시지를 반환해야 한다.")
        void testCreateFailExistTitle() {
            // given
            BookManager bookManager = createBookManager();

            bookManager.create("test1", "tester", 11);

            // when
            String msg = bookManager.create("test1", "tester", 22);

            // then
            assertThat(msg).isEqualTo(ALREADY_EXIST_TITLE.msg());
        }
    }

    @Nested
    @Order(3)
    @DisplayName("도서 목록 정보 테스트")
    class TestGetInfo {
        @Test
        @DisplayName("도서 목록 조회 시 요구된 형식을 동일해야 한다.")
        void testGetInfo() {
            // given
            BookManager bookManager = createBookManager();
            List<Book> initData = new ArrayList<>();

            initData.add(new Book(1, "test1", "tester", 111, 1L));
            initData.add(new Book(5, "test2", "tester", 222, 2L));
            initData.add(new Book(10, "test3", "tester", 333, 3L));

            // when
            bookManager.init(initData);
            String info = bookManager.getInfo();

            // then
            String requiredFormat = initData.stream()
                    .map(Book::toString)
                    .collect(Collectors.joining("\n------------------------------\n"));

            assertThat(info).isEqualTo(requiredFormat);
        }
    }

    @Nested
    @Order(4)
    @DisplayName("도서 제목 검색 테스트")
    class TestGetInfoByTitle {
        @Test
        @DisplayName("검색 시 주어진 단어를 포함하는 모든 도서를 반환해야 한다.")
        void testGetInfoByTitle() {
            // given
            BookManager bookManager = createBookManager();
            List<Book> initData = new ArrayList<>();

            initData.add(new Book(1, "banabana", "tester", 111, 1L));
            initData.add(new Book(2, "bana", "tester", 222, 2L));
            initData.add(new Book(3, "anab", "tester", 333, 3L));
            initData.add(new Book(4, "banan", "tester", 444, 4L));
            initData.add(new Book(5, "banana", "tester", 555, 5L));

            // when
            bookManager.init(initData);
            String infoByTitle = bookManager.getInfoByTitle("bana");

            // then
            String requiredData = initData.stream()
                    .filter(book -> book.getTitle().contains("bana"))
                    .map(Book::toString)
                    .collect(Collectors.joining("\n------------------------------\n"));

            assertThat(infoByTitle).isEqualTo(requiredData);
        }
    }

    @Nested
    @Order(5)
    @DisplayName("도서 대여 테스트")
    class TestRentById {
        @Test
        @DisplayName("도서 대여 시 해당 도서는 대여 중으로 상태가 변경돼야 한다")
        void testRentByIdChangeState() {
            // given
            BookManager bookManager = createBookManager();
            List<Book> initData = new ArrayList<>();
            Book book = new Book(1, "test1", "tester", 111, 1L);

            initData.add(book);
            bookManager.init(initData);

            // when
            bookManager.rentById(1);

            // then
            assertThat(book.getState()).isEqualTo(BookState.LOAN);
        }

        @Test
        @DisplayName("도서 대여 시 성공 메시지를 반환해야 한다.")
        void testRentByIdSuccess() {
            // given
            BookManager bookManager = createBookManager();
            List<Book> initData = new ArrayList<>();

            initData.add(new Book(1, "test1", "tester", 111, 1L));
            bookManager.init(initData);

            // when
            String msg = bookManager.rentById(1);

            // then
            assertThat(msg).isEqualTo(SUCCESS_RENT_BOOK.msg());
        }

        @Test
        @DisplayName("등록되지 않은 id의 도서를 대여 시 실패 메시지를 반환해야 한다.")
        void testRentByIdFailNotExistId() {
            // given
            BookManager bookManager = createBookManager();

            bookManager.create("test1", "tester", 11);

            // when
            String msg = bookManager.rentById(1000);

            // then
            assertThat(msg).isEqualTo(NOT_EXIST_ID.msg());
        }

        @Test
        @DisplayName("대여할 수 없는 도서를 대여할 때 상태에 맞는 실패 메시지를 반환해야 한다.")
        void testRentByIdFailNonAvailable() {
            // given
            BookManager bookManager = createBookManager();
            List<Book> initData = new ArrayList<>();
            Book book = new Book(1, "test1", "tester", 111, 1L);

            initData.add(book);
            bookManager.init(initData);

            // when
            List<String> msgList = new ArrayList<>();
            List<String> requiredMsgList = new ArrayList<>();

            book.setState(BookState.LOAN);
            requiredMsgList.add("%s (%s)".formatted(FAIL_RENT_BOOK.msg(), book.getState().label()));
            msgList.add(bookManager.rentById(1));

            book.setState(BookState.LOST);
            requiredMsgList.add("%s (%s)".formatted(FAIL_RENT_BOOK.msg(), book.getState().label()));
            msgList.add(bookManager.rentById(1));

            book.setState(BookState.PROCESSING);
            requiredMsgList.add("%s (%s)".formatted(FAIL_RENT_BOOK.msg(), book.getState().label()));
            msgList.add(bookManager.rentById(1));

            // then
            assertThat(msgList).isEqualTo(requiredMsgList);
        }
    }
}