package com.programmers.domain.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.programmers.config.DependencyInjector;
import com.programmers.domain.entity.Book;
import com.programmers.domain.enums.BookStatus;
import com.programmers.util.IdGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class RegisterBookReqTest {
    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();

    @Test
    void testBuilderCreation() {
        RegisterBookReq request = RegisterBookReq.builder()
            .title("Test Title")
            .author("Test Author")
            .pages(123)
            .build();

        assertNotNull(request);
        assertEquals("Test Title", request.getTitle());
        assertEquals("Test Author", request.getAuthor());
        assertEquals(123, request.getPages());
    }

    @Test
    void testFromMethod() {
        RegisterBookReq request = RegisterBookReq.from("Test Title", "Test Author", 123);

        assertNotNull(request);
        assertEquals("Test Title", request.getTitle());
        assertEquals("Test Author", request.getAuthor());
        assertEquals(123, request.getPages());
    }

    @Test
    void testToBookMethod() {
        AutoCloseable closeable = MockitoAnnotations.openMocks(this);
        when(idGenerator.generateId()).thenReturn(1L);

        RegisterBookReq request = RegisterBookReq.from("Test Title", "Test Author", 123);
        Book book = request.toBook();

        assertNotNull(book);
        assertEquals("Test Title", book.getTitle());
        assertEquals("Test Author", book.getAuthor());
        assertEquals(123, book.getPages());
        assertEquals(BookStatus.AVAILABLE, book.getStatus());

        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
