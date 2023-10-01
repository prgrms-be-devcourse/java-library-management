package com.dev_course.book;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

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
        void getBookListBeforeInit() {
            // given
            BookManager bookManager = createBookManager();

            // when
            List<Book> bookList = bookManager.getBookList();

            // then
            assertThat(bookList).isEmpty();
        }

        @Test
        @DisplayName("init() 메서드 호출 시 도서 리스트는 init 데이터가 반영돼야 한다.")
        void getBookListAfterInit() {
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
        void createWithUniqueId() {
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
        void createWitUniqueIdAfterInit() {
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
}