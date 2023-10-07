package com.programmers.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.programmers.BookEntities;
import com.programmers.config.DependencyInjector;
import com.programmers.domain.dto.BookResponse;
import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.domain.entity.Book;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.unchecked.BookNotFoundException;
import com.programmers.util.BookScheduler;
import com.programmers.util.IdGenerator;
import java.lang.reflect.Field;
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

    BookEntities bookEntities;

    private AutoCloseable closeable;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private DependencyInjector dependencyInjector = DependencyInjector.getInstance();

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        when(idGenerator.generateId()).thenReturn(1L);
        this.bookEntities = new BookEntities();
    }

    @AfterEach
    void tearDown() throws Exception {
        if (closeable != null) {
            closeable.close();
        }
    }

    @Test
    @DisplayName("책 등록 테스트")
    void registerBookTest() throws NoSuchFieldException, IllegalAccessException {
        Field privateField = DependencyInjector.class.getDeclaredField("isInitialized");

        // private 필드에 대한 접근 권한을 설정 (이 부분이 접근하는 핵심)
        privateField.setAccessible(true);

        // private 필드의 값을 변경
        privateField.set(privateField, true);

        RegisterBookReq request = RegisterBookReq.from("New Book", "Author", 123);

        Book book = bookEntities.getBook(1L);

        when(mockRepository.findByTitle("New Book")).thenReturn(List.of());
        when(mockRepository.save(any(Book.class))).thenReturn(book);
        Long bookId = bookService.registerBook(request);

        assertEquals(book.getId(), bookId);
        verify(mockRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("책 모두 조회 테스트")
    void findAllBooksTest() {
        List<Book> books = bookEntities.getBooks();

        when(mockRepository.findAll()).thenReturn(books);
        List<BookResponse> result = bookService.findAllBooks();

        assertEquals(4, result.size());
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

        when(mockRepository.findById(id)).thenReturn(Optional.of(new Book()));
        when(mockRepository.deleteById(id)).thenReturn(1);

        assertDoesNotThrow(() -> bookService.deleteBook(id));
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