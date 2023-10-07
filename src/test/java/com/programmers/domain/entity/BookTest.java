package com.programmers.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.programmers.BookEntities;
import com.programmers.config.DependencyInjector;
import com.programmers.domain.enums.BookStatusType;
import com.programmers.domain.status.Available;
import com.programmers.exception.unchecked.BookAvailableFailedException;
import com.programmers.exception.unchecked.BookLostFailedException;
import com.programmers.exception.unchecked.BookRentFailedException;
import com.programmers.exception.unchecked.BookReturnFailedException;
import com.programmers.util.IdGenerator;
import java.lang.reflect.Field;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("Book 테스트")
class BookTest {
    private AutoCloseable closeable;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();
    private BookEntities bookEntities;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        when(idGenerator.generateId()).thenReturn(1L);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    @DisplayName("Book 생성 테스트")
    void testBookCreation() throws NoSuchFieldException, IllegalAccessException {
        Field privateField = DependencyInjector.class.getDeclaredField("isInitialized");

        // private 필드에 대한 접근 권한을 설정 (이 부분이 접근하는 핵심)
        privateField.setAccessible(true);

        // private 필드의 값을 변경
        privateField.set(privateField, true);

        Book book = Book.builder()
            .title("Test Title")
            .author("Test Author")
            .status(new Available())
            .pages(123)
            .build();

        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(BookStatusType.AVAILABLE, book.getStatus().getBookStatusName());
        assertEquals(123, book.getPages());
    }

    // 따로 빼기? 한번에 넣기
    @Test
    @DisplayName("Book 상태 변경 테스트 - [AVAILABLE] -> [RENTED]")
    void testUpdateBookStatusToRent_sucess() {
        Book book = bookEntities.getBook(1L);

        book.updateBookStatusToRent();

        assertEquals(BookStatusType.RENTED, book.getStatus().getBookStatusName());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [LOST,ORGANIZING,RENTED] -> [RENTED]")
    void testUpdateBookStatusToRent_fail() {
        Book book1 = bookEntities.getBook(2L);

        assertThrows(BookRentFailedException.class, book1::updateBookStatusToRent);

        Book book2 = bookEntities.getBook(4L);

        assertThrows(BookRentFailedException.class, book2::updateBookStatusToRent);

        Book book3 = bookEntities.getBook(3L);

        assertThrows(BookRentFailedException.class, book3::updateBookStatusToRent);
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [RENTED,LOST] -> [ORGANIZING]")
    void testUpdateBookStatusToOrganizing_sucess() {
        Book book = bookEntities.getBook(3L);

        book.updateBookStatusToOrganizing();

        assertEquals(BookStatusType.ORGANIZING, book.getStatus().getBookStatusName());

        Book book2 = bookEntities.getBook(2L);
        book2.updateBookStatusToOrganizing();
        assertEquals(BookStatusType.ORGANIZING, book2.getStatus().getBookStatusName());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [AVAILABLE,ORGANIZING] -> [ORGANIZING]")
    void testUpdateBookStatusToOrganizing_fail() {
        Book book1 = bookEntities.getBook(1L);

        assertThrows(BookReturnFailedException.class, book1::updateBookStatusToOrganizing);

        Book book3 = bookEntities.getBook(4L);

        assertThrows(BookReturnFailedException.class, book3::updateBookStatusToOrganizing);
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [RENTED,ORGANIZING,AVAILABLE] -> [LOST]")
    void testUpdateBookStatusToLost_success() {

        Book book1 = bookEntities.getBook(3L);

        book1.updateBookStatusToLost();

        assertEquals(BookStatusType.LOST, book1.getStatus().getBookStatusName());

        Book book2 = bookEntities.getBook(4L);
        book2.updateBookStatusToLost();
        assertEquals(BookStatusType.LOST, book2.getStatus().getBookStatusName());


        Book book3 = bookEntities.getBook(1L);
        book3.updateBookStatusToLost();
        assertEquals(BookStatusType.LOST, book3.getStatus().getBookStatusName());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [LOST] -> [LOST]")
    void testUpdateBookStatusToLost_fail() {
        Book book3 = bookEntities.getBook(2L);

        assertThrows(BookLostFailedException.class, book3::updateBookStatusToLost);
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [ORGANIZING] -> [AVAILABLE]")
    void testUpdateBookStatusToAvailable_success() {
        Book book = bookEntities.getBook(4L);

        book.updateBookStatusToAvailable();

        assertEquals(BookStatusType.AVAILABLE, book.getStatus().getBookStatusName());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [AVAILABLE,LOST,RENTED] -> [AVAILABLE]")
    void testUpdateBookStatusToAvailable_fail() {
        Book book1 = bookEntities.getBook(1L);

        assertThrows(BookAvailableFailedException.class, book1::updateBookStatusToAvailable);

        Book book2 = bookEntities.getBook(2L);

        assertThrows(BookAvailableFailedException.class, book2::updateBookStatusToAvailable);

        Book book3 = bookEntities.getBook(3L);

        assertThrows(BookAvailableFailedException.class, book3::updateBookStatusToAvailable);
    }

}

