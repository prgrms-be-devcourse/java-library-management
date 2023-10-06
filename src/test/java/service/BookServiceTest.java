package service;

import model.Book;
import model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.Repository;
import util.BookScheduler;
import util.BookTestScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {
    @Mock
    private Repository repository;
    private BookScheduler bookScheduler;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bookService = new BookService(repository);
    }

    @Test
    @DisplayName("도서 저장 테스트")
    void testSaveBook() {
        String title = "도서1";
        String author = "작가1";
        int pageNum = 123;

        bookService.saveBook(title, author, pageNum);
        verify(repository, times(1)).saveBook(any(Book.class));
    }

    @Test
    @DisplayName("모든 도서 검색 테스트")
    void findAllBooksTest() {
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1L, "제목1", "작가1", 123, Status.AVAILABLE));
        expectedBooks.add(new Book(2L, "제목2", "작가2", 123, Status.AVAILABLE));

        when(repository.findAllBook()).thenReturn(expectedBooks);
        List<Book> actualBooks = bookService.findAllBook();

        assertThat(expectedBooks).isEqualTo(actualBooks);
        verify(repository, times(1)).findAllBook();
    }

    @Test
    @DisplayName("제목으로 도서 검색 테스트")
    void testFindBooksByTitle() {
        String title = "도서";
        List<Book> expectedBooks = new ArrayList<>();
        expectedBooks.add(new Book(1L, "제목1", "작가1", 123, Status.AVAILABLE));

        when(repository.findBookByTitle(title)).thenReturn(expectedBooks);
        List<Book> actualBooks = bookService.findBooksByTitle(title);

        assertThat(expectedBooks).isEqualTo(actualBooks);
        verify(repository, times(1)).findBookByTitle(title);
    }

    @Test
    @DisplayName("도서 대여 테스트")
    void testBorrowBook() {
        Long bookNo = 1L;
        Book book = new Book(1L, "제목1", "작가1", 123, Status.AVAILABLE);

        when(repository.findBookByBookNo(bookNo)).thenReturn(Optional.of(book));
        bookService.borrowBookByBookNo(bookNo);

        verify(repository, times(1)).saveBook(book);
    }

    @Test
    @DisplayName("도서 대여 실패 테스트")
    void testBorrowBookFail() {
        Long bookNo = 1L;
        Book book = new Book(1L, "제목1", "작가1", 123, Status.BORROWED);

        when(repository.findBookByBookNo(bookNo)).thenReturn(Optional.of(book));

        assertThrows(IllegalStateException.class, () -> bookService.borrowBookByBookNo(bookNo));
    }

    @Test
    @DisplayName("도서 반납 테스트")
    void testReturnBook() {
        Long bookNo = 1L;
        Book book = new Book(1L, "제목1", "작가1", 123, Status.BORROWED);
        bookScheduler = new BookTestScheduler();

        when(repository.findBookByBookNo(bookNo)).thenReturn(Optional.of(book));
        try {
            bookService.returnBookByBookNo(bookNo, bookScheduler);
            assertThat(book.getStatus()).isEqualTo(Status.ORGANIZING);
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            verify(repository, times(2)).saveBook(book);
            assertThat(book.getStatus()).isEqualTo(Status.AVAILABLE);
        }
    }

    @Test
    @DisplayName("도서 반납 실패 테스트")
    void testReturnFail_bookAvailable() {
        Long bookNo = 1L;
        Book book = new Book(1L, "제목1", "작가1", 123, Status.AVAILABLE);

        when(repository.findBookByBookNo(bookNo)).thenReturn(Optional.of(book));

        assertThrows(IllegalStateException.class, () -> bookService.returnBookByBookNo(bookNo, bookScheduler));
    }

    @Test
    @DisplayName("도서 분실 처리 테스트")
    void testLostBook() {
        Long bookNo = 1L;
        Book book = new Book(1L, "제목1", "작가1", 123, Status.AVAILABLE);

        when(repository.findBookByBookNo(bookNo)).thenReturn(Optional.of(book));
        bookService.lostBookByBookNo(bookNo);

        verify(repository, times(1)).saveBook(book);
        assertThat(book.getStatus()).isEqualTo(Status.LOST);
    }

    @Test
    @DisplayName("도서 분실 처리 실패 테스트")
    void testLostBookFail_alreadyLost() {
        Long bookNo = 1L;
        Book book = new Book(1L, "제목1", "작가1", 123, Status.LOST);

        when(repository.findBookByBookNo(bookNo)).thenReturn(Optional.of(book));

        assertThrows(IllegalStateException.class, () -> bookService.lostBookByBookNo(bookNo));
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        Long bookNo = 1L;
        Book book = new Book(1L, "제목1", "작가1", 123, Status.LOST);

        when(repository.findBookByBookNo(bookNo)).thenReturn(Optional.of(book));

        bookService.deleteBookByBookNo(bookNo);
        verify(repository, times(1)).deleteBook(bookNo);
    }
}
