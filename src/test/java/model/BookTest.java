package model;

import constant.ExceptionMsg;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {

    @Test
    @DisplayName("대여 가능 여부 확인 테스트")
    void isAvailableToBorrow() {
        Book availableBook = new Book(1L, "대여가능도서", "작가1", 255);
        Book notAvailableBook = new Book(2L, "대여중도서", "작가2", 255);
        notAvailableBook.toBorrowed();

        assertTrue(availableBook.isAvailableToBorrow());
        assertThatThrownBy(notAvailableBook::isAvailableToBorrow)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(ExceptionMsg.ALREADY_BORROWED.getMessage());
    }

    @Test
    @DisplayName("반납 가능 여부 확인 테스트")
    void isAvailableToReturn() {
        Book availableBook = new Book(1L, "대여가능도서", "작가1", 255);
        availableBook.toBorrowed();
        Book notAvailableBook = new Book(2L, "대여중도서", "작가2", 255);

        assertTrue(availableBook.isAvailableToReturn());
        assertThrows(IllegalStateException.class, notAvailableBook::isAvailableToReturn);
    }

    @Test
    @DisplayName("분실 처리 가능 여부 확인 테스트")
    void isAvailableToChangeLost() {
        Book availableBook = new Book(1L, "대여가능도서", "작가1", 255);
        Book notAvailableBook = new Book(2L, "대여중도서", "작가2", 255);
        notAvailableBook.toLost();

        assertTrue(availableBook.isAvailableToChangeLost());
        assertThrows(IllegalStateException.class, notAvailableBook::isAvailableToChangeLost);
    }

    @Test
    @DisplayName("상태 문구로 도서 상태 찾기 테스트")
    void findStatusByString() {
        Status checkAvailable = Status.findStatusByString("대여 가능");
        Status checkBorrowed = Status.findStatusByString("대여중");

        assertThat(checkAvailable).isEqualTo(Status.AVAILABLE);
        assertThat(checkBorrowed).isEqualTo(Status.BORROWED);
    }
}
