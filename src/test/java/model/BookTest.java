package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BookTest {
    private final List<Book> books = new ArrayList<>();

    @BeforeEach
    void setUp() {
        books.add(new Book(1L, "대여가능도서", "작가1", 255, Status.AVAILABLE));
        books.add(new Book(2L, "대여중도서", "작가2", 255, Status.BORROWED));
        books.add(new Book(3L, "정리중도서", "작가3", 255, Status.ORGANIZING));
        books.add(new Book(4L, "분실된도서", "작가4", 255, Status.LOST));
    }

    @Test
    @DisplayName("대여 가능 여부 확인 테스트")
    void isAvailableToBorrow() {
        assertTrue(books.get(0).isAvailableToBorrow());
        assertThrows(IllegalStateException.class, () -> books.get(1).isAvailableToBorrow());
        assertThrows(IllegalStateException.class, () -> books.get(2).isAvailableToBorrow());
        assertThrows(IllegalStateException.class, () -> books.get(3).isAvailableToBorrow());
    }

    @Test
    @DisplayName("반납 가능 여부 확인 테스트")
    void isAvailableToReturn() {
        assertThrows(IllegalStateException.class, () -> books.get(0).isAvailableToReturn());
        assertTrue(books.get(1).isAvailableToReturn());
        assertThrows(IllegalStateException.class, () -> books.get(2).isAvailableToBorrow());
        assertTrue(books.get(3).isAvailableToReturn());
    }

    @Test
    @DisplayName("분실 처리 가능 여부 확인 테스트")
    void isAvailableToChangeLost() {
        assertTrue(books.get(0).isAvailableToChangeLost());
        assertTrue(books.get(1).isAvailableToChangeLost());
        assertTrue(books.get(2).isAvailableToChangeLost());
        assertThrows(IllegalStateException.class, () -> books.get(3).isAvailableToChangeLost());
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
