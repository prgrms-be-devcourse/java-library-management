package library.service;

import library.domain.Book;
import library.domain.BookStatus;
import library.dto.BookFindResponse;
import library.dto.BookSaveRequest;
import library.exception.BookErrorMessage;
import library.exception.BookException;
import library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;

    @BeforeEach
    void setup() {
        bookRepository = mock(BookRepository.class);
        bookService = new BookService(bookRepository);
    }

    @Test
    @DisplayName("도서를 추가할 수 있어야 합니다.")
    void testAdd() {
        // Given
        BookSaveRequest bookSaveRequest = new BookSaveRequest("Title", "Author", 100);

        // When
        bookService.addBook(bookSaveRequest);
        bookService.addBook(bookSaveRequest);

        // Then
        verify(bookRepository, times(2)).add(any());
    }

    @Test
    @DisplayName("도서 제목으로 도서를 찾을 수 있어야 합니다.")
    void testFindByBookNumber() {
        // Given
        String title = "Ogu and the Secret Forest";
        when(bookRepository.findListContainTitle(title))
                .thenReturn(Collections.singletonList(Book.createAvailableBook(1, title, "Author", 100)));

        // When
        List<BookFindResponse> findByTitle = bookService.findBookListContainTitle(title);

        // Then
        assertThat(findByTitle)
                .hasSize(1)
                .extracting("title")
                .containsExactly(title);
    }

    @Test
    @DisplayName("도서 번호를 통해 상태를 변경할 수 있어야 합니다.")
    void testChangesBookStatus() {
        // Given
        Book book = Book.createAvailableBook(1, "Title", "Author", 100);
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(book));
        when(bookRepository.findByBookNumber(anyLong())).thenReturn(Optional.of(book));

        long bookNumber = book.getBookNumber();
        int callTimes = 0;

        // When, Then
        bookService.rentBook(bookNumber);
        assertThat(book.getStatus().name()).isEqualTo(BookStatus.RENTED.name());
        verify(bookRepository, times(++callTimes)).persist();

        bookService.returnBook(bookNumber);
        assertThat(book.getStatus().name()).isEqualTo(BookStatus.IN_CLEANUP.name());
        verify(bookRepository, times(++callTimes)).persist();

        bookService.lostBook(bookNumber);
        assertThat(book.getStatus().name()).isEqualTo(BookStatus.LOST.name());
        verify(bookRepository, times(++callTimes)).persist();
    }

    @Test
    @DisplayName("존재하지 않는 도서 번호로 상태를 변경할 수 없습니다")
    void testChangesBookStatus_WithNotExistingBookNumber_ThrowsException() {
        // Given
        when(bookRepository.findByBookNumber(anyLong())).thenReturn(Optional.empty());

        // When, Then
        assertThatExceptionOfType(BookException.class)
                .isThrownBy(() -> bookService.rentBook(1L))
                .withMessage(BookErrorMessage.BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("도서 번호를 통해 도서를 삭제할 수 있어야 합니다.")
    void testDeleteBook() {
        // Given
        Book book = Book.createAvailableBook(1, "Title", "Author", 100);
        when(bookRepository.findByBookNumber(book.getBookNumber())).thenReturn(Optional.of(book));
        long bookNumber = book.getBookNumber();

        // When
        bookService.deleteBook(bookNumber);

        // Then
        verify(bookRepository, times(1)).delete(book);
        assertThat(bookRepository.findAll()).isEmpty();
    }
}
