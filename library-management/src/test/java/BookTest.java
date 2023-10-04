import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static devcourse.backend.model.BookStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    void setUp() {
        // 각 테스트 전 새로운 Book 객체 생성
        book = new Book.Builder("이펙티브 자바", "조슈아 블로크", 520).build();
    }

    @Test
    void id_중복_허용_X() {
        // id는 sequence static 변수를 사용해 auto increment
        Book book2 = new Book.Builder("이펙티브 자바2", "조슈아 블로크", 520).build();
        assertEquals(book.getId() + 1, book2.getId());

        // sequence보다 작은 값을 설정하면, 설정을 무시하고 auto increment
        Book book3 = new Book.Builder("이펙티브 자바3", "조슈아 블로크", 520).id(1L).build();
        assertEquals(book2.getId() + 1, book3.getId());

        // sequence보다 큰 값을 설정하면, sequence를 해당 값 + 1로 업데이트
        Book book4 = new Book.Builder("이펙티브 자바4", "조슈아 블로크", 520).id(100L).build();
        Book book5 = new Book.Builder("이펙티브 자바5", "조슈아 블로크", 520).build();
        assertEquals(100L, book4.getId());
        assertEquals(101L, book5.getId());
    }

    @Test
    void testToRecord() {
        // toRecord() 메서드 테스트
        String expectedRecord = book.getId() + ";이펙티브 자바;조슈아 블로크;520;대여 가능";
        String actualRecord = book.toRecord();
        assertEquals(expectedRecord, actualRecord);
    }

    @Test
    void testLike() {
        // like() 메서드 테스트
        assertTrue(book.like("자바"));
        assertFalse(book.like("Java"));
    }

    @Test
    @DisplayName("[대여 가능] 상태 일 때만 [대여 중]상태로 바꿀 수 있습니다.")
    void 대여_중_상태로_변경() {
        // [대여 가능] -> [대여 중] O
        Book available = getBook(AVAILABLE);
        available.changeStatus(BORROWED);
        assertEquals(available.getStatus(), BORROWED);

        // [대여 중, 도서 정리 중, 분실됨] -> [대여 중] X
        Book lent = getBook(BORROWED);
        Book toArrange = getBook(ARRANGING);
        Book lost = getBook(LOST);

        assertThrows(IllegalArgumentException.class, () -> lent.changeStatus(BORROWED));
        assertThrows(IllegalArgumentException.class, () -> toArrange.changeStatus(BORROWED));
        assertThrows(IllegalArgumentException.class, () -> lost.changeStatus(BORROWED));
    }

    private static Book getBook(BookStatus status) {
        return new Book.Builder("book", "author", 100)
                .bookStatus(status.toString())
                .build();
    }

    @Test
    @DisplayName("[대여 중, 분실됨] 상태 일 때만 [도서 정리 중] 상태로 바꿀 수 있습니다.")
    void 도서_정리_상태로_변경() {
        // [대여 중, 분실됨] -> [도서 정리 중] O
        Book lent = getBook(BORROWED);
        lent.changeStatus(ARRANGING);
        assertEquals(lent.getStatus(), ARRANGING);

        Book lost = getBook(LOST);
        lost.changeStatus(ARRANGING);
        assertEquals(lost.getStatus(), ARRANGING);

        // [대여 가능, 도서 정리 중] -> [도서 정리 중] X
        Book available = getBook(AVAILABLE);
        Book toArrange = getBook(ARRANGING);
        assertThrows(IllegalArgumentException.class, () -> toArrange.changeStatus(ARRANGING));
        assertThrows(IllegalArgumentException.class, () -> available.changeStatus(ARRANGING));
    }

    @Test
    @DisplayName("[도서 정리 중] 상태 일 때만 [대여 가능] 상태로 바꿀 수 있습니다.")
    void 대여_가능_상태로_변경() {
        // [도서 정리 중] -> [대여 가능] O
        Book toArrange = getBook(ARRANGING);
        toArrange.changeStatus(AVAILABLE);
        assertEquals(toArrange.getStatus(), AVAILABLE);

        // [대여 가능, 대여 중, 분실됨] -> [대여 가능] X
        Book available = getBook(AVAILABLE);
        Book lent = getBook(BORROWED);
        Book lost = getBook(LOST);
        assertThrows(IllegalArgumentException.class, () -> available.changeStatus(AVAILABLE));
        assertThrows(IllegalArgumentException.class, () -> lent.changeStatus(AVAILABLE));
        assertThrows(IllegalArgumentException.class, () -> lost.changeStatus(AVAILABLE));
    }

    @Test
    @DisplayName("[분실됨] 상태 일 때만 [분실됨] 상태로 바꿀 수 없습니다.")
    void 분실됨_상태로_변경() {
        // [대여 가능, 대여 중, 도서 정리 중] -> [분실됨] O
        Book available = getBook(AVAILABLE);
        available.changeStatus(LOST);
        assertEquals(available.getStatus(), LOST);

        Book lent = getBook(BORROWED);
        lent.changeStatus(LOST);
        assertEquals(lent.getStatus(), LOST);

        Book toArrange = getBook(ARRANGING);
        toArrange.changeStatus(LOST);
        assertEquals(toArrange.getStatus(), LOST);

        // [분실됨] -> [분실됨] X
        Book lost = getBook(LOST);
        assertThrows(IllegalArgumentException.class, () -> lost.changeStatus(LOST));
    }

    @Test
    void testEquals() {
        // equals() 메서드 테스트
        Book equalBook = new Book.Builder("이펙티브 자바", "조슈아 블로크", 520).build();
        Book differentBook = new Book.Builder("가상 면접 사례로 배우는 대규모 시스템 설계 기초", "알렉스 쉬", 320).build();

        assertTrue(book.equals(equalBook));
        assertFalse(book.equals(differentBook));
    }

    @Test
    void testCopy() {
        // copy() 메서드 테스트
        Book copy = book.copy();

        assertEquals(book, copy);
        assertNotSame(book, copy);
    }
}
