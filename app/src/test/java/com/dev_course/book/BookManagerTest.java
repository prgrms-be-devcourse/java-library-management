package com.dev_course.book;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    }

    @Nested
    @Order(3)
    @DisplayName("도서 목록 정보 테스트")
    class TestGetInfo {
        @Test
        @DisplayName("도서 목록 조회 시 요구된 형식을 동일해야 한다")
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
}