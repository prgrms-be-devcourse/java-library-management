package library.service;

import library.domain.BookStatus;
import library.dto.BookFindResponse;
import library.dto.BookSaveRequest;
import library.exception.BookException;
import library.repository.BookRepository;
import library.repository.InMemoryBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookServiceTest {

    private BookRepository bookRepository;
    private BookService bookService;
    private BookService testBookService;

    @BeforeEach
    void setup() {
        bookRepository = new InMemoryBookRepository();
        bookService = new BookService(bookRepository);
        testBookService = new BookService(new ReturnSameNumberBookRepository());
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
        assertThat(bookRepository.findAll()).hasSize(2);
    }

    @Test
    @DisplayName("도서 번호가 중복되는 경우 도서를 추가할 수 없어야 합니다.")
    void testAdd_WithDuplicatedBookNumber_ThrowsException() {
        // Given
        BookSaveRequest bookSaveRequest = new BookSaveRequest("Title", "Author", 100);
        testBookService.addBook(bookSaveRequest);

        // When, Then
        assertThrows(BookException.class,
                () -> testBookService.addBook(bookSaveRequest));
    }

    @Test
    @DisplayName("도서 제목으로 도서를 찾을 수 있어야 합니다.")
    void testFindByBookNumber() {
        // Given
        bookService.addBook(new BookSaveRequest("Ogu and the Secret Forest", "Moonlab", 59));
        bookService.addBook(new BookSaveRequest("Ogu and the Blind Forest", "Moonlab", 59));
        bookService.addBook(new BookSaveRequest("Ogu and the Moon Forest", "Moonlab", 59));

        // When
        List<BookFindResponse> findByForest = bookService.findBookListContainTitle("Forest");
        List<BookFindResponse> findBySecret = bookService.findBookListContainTitle("Secret");

        // Then
        assertThat(findByForest).hasSize(3);
        assertThat(findBySecret).hasSize(1);
    }

    @Test
    @DisplayName("도서 번호를 통태 상태를 변경할 수 있어야 합니다.")
    void testChangesBookStatus() {
        // Given
        BookSaveRequest bookSaveRequest = new BookSaveRequest("Title", "Author", 100);
        bookService.addBook(bookSaveRequest);
        long bookNumber = bookRepository.findAll().get(0).getBookNumber();

        // When, Then
        bookService.rentBook(bookNumber);
        assertThat(bookRepository.findByBookNumber(bookNumber).orElseThrow().getStatus()).isEqualTo(BookStatus.RENTED);
        bookService.returnBook(bookNumber);
        assertThat(bookRepository.findByBookNumber(bookNumber).orElseThrow().getStatus()).isEqualTo(BookStatus.IN_CLEANUP);
        bookService.lostBook(bookNumber);
        assertThat(bookRepository.findByBookNumber(bookNumber).orElseThrow().getStatus()).isEqualTo(BookStatus.LOST);
    }

    @Test
    @DisplayName("존재하지 않는 도서 번호로 상태를 변경할 수 없습니다")
    void testChangesBookStatus_WithNotExistingBookNumber_ThrowsException() {
        // Given
        BookSaveRequest bookSaveRequest = new BookSaveRequest("Title", "Author", 100);
        bookService.addBook(bookSaveRequest);
        long bookNumber = bookRepository.findAll().get(0).getBookNumber();

        // When, Then
        assertThrows(BookException.class,
                () -> bookService.rentBook(bookNumber + 1));
    }

    @Test
    @DisplayName("도서 번호를 통해 도서를 삭제할 수 있어야 합니다.")
    void testDeleteBook() {
        // Given
        BookSaveRequest bookSaveRequest = new BookSaveRequest("Title", "Author", 100);
        bookService.addBook(bookSaveRequest);
        long bookNumber = bookRepository.findAll().get(0).getBookNumber();

        // When
        bookService.deleteBook(bookNumber);

        // Then
        assertThat(bookRepository.findAll()).isEmpty();
    }

    static class ReturnSameNumberBookRepository extends InMemoryBookRepository {
        @Override
        public long getNextBookNumber() {
            return 1L;
        }
    }
}
