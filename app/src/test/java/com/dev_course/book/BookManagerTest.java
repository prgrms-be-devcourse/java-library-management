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
        @DisplayName("초기 생성 시 도서 리스트는 비어있어야한다.")
        void getBookListBeforeInit() {
            // given
            BookManager bookManager = createBookManager();

            // when

            // then
            List<Book> bookList = bookManager.getBookList();

            assertThat(bookList).isEmpty();
        }

        @Test
        @DisplayName("init() 메서드 호출 시 도서 리스트는 init 데이터가 반영되야한다.")
        void getBookListAfterInit() {
            // given
            BookManager bookManager = createBookManager();

            // when
            List<Book> initData = new ArrayList<>();

            initData.add(new Book(1, "test1", "tester", 111, 1L));
            initData.add(new Book(2, "test2", "tester", 222, 2L));
            initData.add(new Book(3, "test3", "tester", 333, 3L));

            bookManager.init(initData);

            // then
            List<Book> bookList = bookManager.getBookList();

            assertThat(bookList).containsAll(initData);
        }
    }
}