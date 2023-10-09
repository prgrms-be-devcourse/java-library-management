package app.library.management.core.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static app.library.management.core.domain.BookStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    @DisplayName("최근 변경된 시간을 업데이트하면 현재 시간으로 업데이트된다.")
    @Test
    void updateLastModifiedTime() {
        // given
        Book book = new Book("토비의 스프링", "토비", 1000);
        LocalDateTime now = LocalDateTime.now();

        // when
        book.updateLastModifiedTime(now);

        // then
        assertThat(book.getLastModifiedTime()).isEqualTo(now);
    }

    @DisplayName("도서를 대여하면 책의 상태가 'RENT'로 변경된다.")
    @Test
    void rent() {
        // given
        Book book = new Book("토비의 스프링", "토비", 1000);
        LocalDateTime now = LocalDateTime.now();

        // when
        book.rent(now);

        // then
        assertThat(book.getStatus()).isEqualTo(RENTED);
    }

    @DisplayName("도서를 분실하면 책의 상태가 'LOST'로 변경된다.")
    @Test
    void lost() {
        // given
        Book book = new Book("토비의 스프링", "토비", 1000);
        LocalDateTime now = LocalDateTime.now();

        // when
        book.lost(now);

        // then
        assertThat(book.getStatus()).isEqualTo(LOST);
    }

    @DisplayName("도서를 반납하면 책의 상태가 'ORGANIZING'으로 변경된다.")
    @Test
    void returnBook() {
        // given
        Book book = new Book("토비의 스프링", "토비", 1000);
        LocalDateTime now = LocalDateTime.now();

        // when
        book.returnBook(now);

        // then
        assertThat(book.getStatus()).isEqualTo(ORGANIZING);
    }

    @DisplayName("도서가 대여가능하면 책의 상태가 'AVAILABLE'으로 변경된다.")
    @Test
    void available() {
        // given
        Book book = new Book("토비의 스프링", "토비", 1000);
        LocalDateTime now = LocalDateTime.now();

        // when
        book.available(now);

        // then
        assertThat(book.getStatus()).isEqualTo(AVAILABLE);
    }

    @DisplayName("도서의 상태가 'RENTED' 또는 'LOST'이면, 도서를 반납할 수 있다.")
    @Test
    void isBookReturnable() {
        // given
        Book book1 = new Book("토비의 스프링", "토비", 1000);
        Book book2 = new Book("토비의 스프링", "토비", 1000);
        LocalDateTime now = LocalDateTime.now();

        // when
        book1.rent(now);
        book2.lost(now);

        // then
        assertAll(
                () -> assertThat(book1.isBookReturnable()).isTrue(),
                () -> assertThat(book2.isBookReturnable()).isTrue()
        );
    }

    @DisplayName("도서의 상태가 'AVAILABLE' 또는 'ORGANIZING'이면, 도서를 반납할 수 없다.")
    @Test
    void isNotBookReturnable() {
        // given
        Book book1 = new Book("토비의 스프링", "토비", 1000);
        Book book2 = new Book("토비의 스프링", "토비", 1000);
        LocalDateTime now = LocalDateTime.now();

        // when
        book1.available(now);
        book2.returnBook(now);

        // then
        assertAll(
                () -> assertThat(book1.isBookReturnable()).isFalse(),
                () -> assertThat(book2.isBookReturnable()).isFalse()
        );
    }

}