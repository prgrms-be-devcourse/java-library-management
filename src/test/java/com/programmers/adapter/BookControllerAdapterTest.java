package com.programmers.adapter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.mediator.dto.ConsoleResponse;
import com.programmers.presentation.BookController;
import com.programmers.util.Messages;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BookControllerAdapter 테스트")
class BookControllerAdapterTest {

    private BookController mockController;
    private BookControllerAdapter adapter;

    @BeforeEach
    void setUp() {
        mockController = mock(BookController.class);
        adapter = new BookControllerAdapter(mockController);
    }

    @Test
    @DisplayName("종료 컨트롤러 연결 테스트")
    void testExitApplication() {
        String request = "n";

        ConsoleResponse response = adapter.exitApplication(request);

        verify(mockController).exitApplication(request);
        assertEquals(Messages.RETURN_MENU.getMessage(), response.getMessage());
        Optional<?> body = (Optional<?>) response.getBody();
        assertFalse(body.isPresent());
    }

    @Test
    @DisplayName("책 등록 컨트롤러 연결 테스트")
    void testRegisterBook() {
        RegisterBookReq req = RegisterBookReq.from("New Book", "Author", 123);

        ConsoleResponse response = adapter.registerBook(req);

        verify(mockController).registerBook(req);
        assertEquals(Messages.BOOK_REGISTER_SUCCESS.getMessage(), response.getMessage());
        Optional<?> body = (Optional<?>) response.getBody();
        assertFalse(body.isPresent());
    }

//    @Test
//    @DisplayName("모든 책 조회 컨트롤러 연결 테스트")
//    void testGetAllBooks() {
//        List<Book> mockBooks = new ArrayList<>();
//
//        when(mockController.getAllBooks()).thenReturn(mockBooks);
//        ConsoleResponse response = adapter.getAllBooks();
//
//        assertEquals(Messages.BOOK_LIST_SUCCESS.getMessage(), response.getMessage());
//        Optional<List<Book>> body = (Optional<List<Book>>) response.getBody();
//        assertTrue(body.isPresent());
//    }
//
//    @Test
//    @DisplayName("책 제목으로 조회 컨트롤러 연결 테스트")
//    void testSearchBooksByTitle() {
//        String title = "Sample Title";
//        List<Book> mockBooks = new ArrayList<>();
//
//        when(mockController.searchBooksByTitle(title)).thenReturn(mockBooks);
//        ConsoleResponse response = adapter.searchBooksByTitle(title);
//
//        assertEquals(Messages.BOOK_SEARCH_SUCCESS.getMessage(), response.getMessage());
//        Optional<List<Book>> body = (Optional<List<Book>>) response.getBody();
//        assertTrue(body.isPresent());
//    }

    @Test
    @DisplayName("책 대여 컨트롤러 연결 테스트")
    void testRentBook() {
        Long bookId = 1L;

        ConsoleResponse response = adapter.rentBook(bookId);

        verify(mockController).rentBook(bookId);
        assertEquals(Messages.BOOK_RENT_SUCCESS.getMessage(), response.getMessage());
        Optional<?> body = (Optional<?>) response.getBody();
        assertFalse(body.isPresent());
    }

    @Test
    @DisplayName("책 반납 컨트롤러 연결 테스트")
    void testReturnBook() {
        Long bookId = 1L;

        ConsoleResponse response = adapter.returnBook(bookId);

        verify(mockController).returnBook(bookId);
        assertEquals(Messages.BOOK_RETURN_SUCCESS.getMessage(), response.getMessage());
        Optional<?> body = (Optional<?>) response.getBody();
        assertFalse(body.isPresent());
    }

    @Test
    @DisplayName("책 분실 컨트롤러 연결 테스트")
    void testReportLostBook() {
        Long bookId = 1L;

        ConsoleResponse response = adapter.reportLostBook(bookId);

        verify(mockController).reportLostBook(bookId);
        assertEquals(Messages.BOOK_LOST_SUCCESS.getMessage(), response.getMessage());
        Optional<?> body = (Optional<?>) response.getBody();
        assertFalse(body.isPresent());
    }

    @Test
    @DisplayName("책 삭제 컨트롤러 연결 테스트")
    void testDeleteBook() {
        Long bookId = 1L;

        ConsoleResponse response = adapter.deleteBook(bookId);

        verify(mockController).deleteBook(bookId);
        assertEquals(Messages.BOOK_DELETE_SUCCESS.getMessage(), response.getMessage());
        Optional<?> body = (Optional<?>) response.getBody();
        assertFalse(body.isPresent());
    }
}

