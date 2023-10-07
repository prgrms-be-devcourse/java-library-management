package com.programmers.presentation;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.programmers.application.BookService;
import com.programmers.config.DependencyInjector;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.util.IdGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class BookControllerTest {

    private AutoCloseable closeable;
    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

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
    void testRegisterBook() {
        RegisterBookReq req = mock(RegisterBookReq.class);

        bookController.registerBook(req);

        verify(bookService, times(1)).registerBook(req);
    }

    @Test
    void testGetAllBooks() {
//        when(bookService.findAllBooks()).thenReturn(Arrays.asList(new Book(), new Book()));
//
//        List<Book> result = bookController.getAllBooks();
//
//        verify(bookService, times(1)).findAllBooks();
//        assertEquals(2, result.size());
    }

    @Test
    void testSearchBooksByTitle() {
//        when(bookService.searchBook("Test")).thenReturn(Arrays.asList(new Book()));
//
//        List<Book> result = bookController.searchBooksByTitle("Test");

//        verify(bookService, times(1)).searchBook("Test");
//        assertEquals(1, result.size());
    }

    @Test
    void testRentBook() {
        Long bookId = 1L;

        bookController.rentBook(bookId);

        verify(bookService, times(1)).rentBook(bookId);
    }

    @Test
    void testReturnBook() {
        Long bookId = 1L;

        bookController.returnBook(bookId);

        verify(bookService, times(1)).returnBook(bookId);
    }

    @Test
    void testReportLostBook() {
        Long bookId = 1L;

        bookController.reportLostBook(bookId);

        verify(bookService, times(1)).reportLostBook(bookId);
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;

        bookController.deleteBook(bookId);

        verify(bookService, times(1)).deleteBook(bookId);
    }
}

