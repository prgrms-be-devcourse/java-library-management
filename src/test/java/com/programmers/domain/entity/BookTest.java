package com.programmers.domain.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.programmers.config.DependencyInjector;
import com.programmers.domain.enums.BookStatus;
import com.programmers.util.IdGenerator;
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
    void testBookCreation() {
        Book book = Book.builder()
            .title("Test Title")
            .author("Test Author")
            .status(BookStatus.AVAILABLE)
            .pages(123)
            .build();

        assertNotNull(book);
        assertEquals(1L, book.getId());
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());
        assertEquals(123, book.getPages());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [AVAILABLE] -> [RENTED]")
    void testUpdateBookStatusToRent() {
        Book book = Book.builder()
            .status(BookStatus.AVAILABLE)
            .build();

        book.updateBookStatusToRent();

        assertEquals(BookStatus.RENTED, book.getStatus());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [RENTED] -> [ORGANIZING]")
    void testUpdateBookStatusToOrganizing() {
        Book book = Book.builder()
            .status(BookStatus.RENTED)
            .build();

        book.updateBookStatusToOrganizing();

        assertEquals(BookStatus.ORGANIZING, book.getStatus());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [RENTED] -> [LOST]")
    void testUpdateBookStatusToLost() {
        Book book = Book.builder()
            .status(BookStatus.RENTED)
            .build();

        book.updateBookStatusToLost();

        assertEquals(BookStatus.LOST, book.getStatus());
    }

    @Test
    @DisplayName("Book 상태 변경 테스트 - [ORGANIZING] -> [AVAILABLE]")
    void testUpdateBookStatusToAvailable() {
        Book book = Book.builder()
            .status(BookStatus.ORGANIZING)
            .build();

        book.updateBookStatusToAvailable();

        assertEquals(BookStatus.AVAILABLE, book.getStatus());
    }

}

