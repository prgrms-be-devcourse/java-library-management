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
        String expectedRecord = book.getId() + ";이펙티브 자바;조슈아 블로크;520;대여 가능;" + book.getUpdateAt();
        String actualRecord = book.toRecord();
        assertEquals(expectedRecord, actualRecord);
    }

    @Test
    void testLike() {
        // like() 메서드 테스트
        assertTrue(book.like("자바"));
        assertFalse(book.like("Java"));
    }
}
