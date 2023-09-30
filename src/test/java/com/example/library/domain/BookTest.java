package com.example.library.domain;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {

    Book book;

    @BeforeEach
    public void initBook() {
        book = new Book(9999L, "Java", "kim", "999 페이지", BookStatusType.대여가능, LocalDateTime.now());
    }

    @ParameterizedTest
    @ValueSource(strings = {"J", "a", "v", "a" })
    @DisplayName("객체 book의 이름에 파라미터 bookName이 포함된다.")
    public void isContainTrue(String bookName) {

        assertThat(book.isSame(bookName)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "k", "b", "javs" })
    @DisplayName("객체 book의 이름에 파라미터 bookName이 포함되는지 않는다.")
    public void isContainFalse(String bookName) {

        assertThat(book.isSame(bookName)).isFalse();
    }

    @Test
    @DisplayName("Book객체를 JsonObject타입으로 변경에 성공한다")
    public void convertBookToJson(){

        assertThat(book.convertJson()).isInstanceOf(JSONObject.class);

    }

    @Test
    @DisplayName("정리중인 도서가 5분이 지나면 대여 가능으로 바뀐다.")
    public void successChangeBookStatus() {

        Book testBook1 = new Book(1, "test1", "writer1", "111", BookStatusType.도서정리중, LocalDateTime.of(2023, 1, 1, 1, 1, 1, 111));

        assertThat(testBook1.isExceededfiveMinute()).isTrue();
    }
    @Test
    @DisplayName("정리중인 도서가 5분이 지나지 않으면 상태가 바뀌지 않는다.")
    public void failChangeBookStatus() {

        Book testBook2 = new Book(1, "test1", "writer1", "111", BookStatusType.도서정리중, LocalDateTime.now());

        assertThat(testBook2.isExceededfiveMinute()).isFalse();
    }
}
