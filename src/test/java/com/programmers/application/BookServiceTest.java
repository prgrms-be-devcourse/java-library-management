package com.programmers.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.programmers.config.DependencyInjector;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.domain.entity.Book;
import com.programmers.domain.enums.BookStatus;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.unchecked.BookNotFoundException;
import com.programmers.util.BookScheduler;
import com.programmers.util.IdGenerator;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

@DisplayName("BookService 테스트")
class BookServiceTest {

    BookRepository mockRepository = mock(BookRepository.class);
    BookScheduler mockScheduler = mock(BookScheduler.class);

    BookService bookService = new BookService(mockRepository, mockScheduler);

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
    @DisplayName("책 등록 테스트")
    void registerBookTest() {
        RegisterBookReq request = RegisterBookReq.from("New Book", "Author", 123);

        Book book = Book.builder()
            .title("New Book")
            .author("Author")
            .pages(123)
            .status(BookStatus.AVAILABLE)
            .build();

        when(mockRepository.findByTitle("New Book")).thenReturn(List.of());
        when(mockRepository.save(any(Book.class))).thenReturn(book);
        Long bookId = bookService.registerBook(request);

        assertEquals(book.getId(), bookId);
        verify(mockRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("책 모두 조회 테스트")
    void findAllBooksTest() {
        List<Book> books = Arrays.asList(
            Book.builder().author("Author 1").title("Book 1").pages(600)
                .status(BookStatus.AVAILABLE).build(),
            Book.builder().author("Author 2").title("Book 2").pages(456)
                .status(BookStatus.AVAILABLE).build(),
            Book.builder().author("Author 3").title("Book 3").pages(789)
                .status(BookStatus.AVAILABLE).build()
        );

        when(mockRepository.findAll()).thenReturn(books);
        List<Book> result = bookService.findAllBooks();

        assertEquals(3, result.size());
        verify(mockRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("책 검색 테스트")
    void searchBook() {
        String title = "Sample Title";

        bookService.searchBook(title);

        verify(mockRepository).findByTitle(title);
    }

    @Test
    @DisplayName("책 삭제 테스트-[책이 존재할 때]")
    public void deleteBookWhenBookExists() {
        Long id = 1L;

        when(mockRepository.deleteById(id)).thenReturn(1);

        assertDoesNotThrow(() -> bookService.deleteBook(id));
        verify(mockRepository).deleteById(id);
    }

    @Test
    @DisplayName("책 삭제 테스트-[책이 존재하지 않을 때]")
    public void deleteBookWhenBookDoesNotExist() {
        Long id = 1L;

        when(mockRepository.deleteById(id)).thenReturn(0);

        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(id));
    }

    @Test
    @DisplayName("책 대여 테스트-[책이 존재할 때]")
    public void rentBookWhenBookExist() {
        Long id = 1L;
        Book mockBook = mock(Book.class);

        when(mockRepository.findById(id)).thenReturn(Optional.of(mockBook));
        bookService.rentBook(id);

        verify(mockBook).updateBookStatusToRent();
        verify(mockRepository).update(mockBook);
    }

    @Test
    @DisplayName("책 대여 테스트-[책이 존재하지 않을 때]")
    public void rentBookWhenBookDoesNotExist() {
        Long id = 1L;

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.rentBook(id));
    }

    @Test
    @DisplayName("책 반납 테스트-[책이 존재할 때]")
    public void returnBookWhenBookExists() {
        Long id = 1L;
        Book mockBook = mock(Book.class);

        when(mockRepository.findById(id)).thenReturn(Optional.of(mockBook));

        bookService.returnBook(id);
        verify(mockBook).updateBookStatusToOrganizing();
        verify(mockRepository).update(mockBook);
    }

    @Test
    @DisplayName("책 반납 테스트-[책이 존재하지 않을 때]")
    public void returnBookWhenBookNotExists() {
        Long id = 1L;

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.returnBook(id));
    }

    @Test
    @DisplayName("책 분실 테스트-[책이 존재할 때]")
    public void reportLostBookWhenBookExists() {
        Long id = 1L;
        Book mockBook = mock(Book.class);

        when(mockRepository.findById(id)).thenReturn(Optional.of(mockBook));
        bookService.reportLostBook(id);

        verify(mockBook).updateBookStatusToLost();
        verify(mockRepository).update(mockBook);
    }

    @Test
    @DisplayName("책 분실 테스트-[책이 존재하지 않을 때]")
    public void reportLostBookWhenBookNotExists() {
        Long id = 1L;

        when(mockRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.reportLostBook(id));
    }
}