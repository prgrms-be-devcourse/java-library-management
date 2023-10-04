package library.repository;

import library.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryBookRepositoryTest {

    private InMemoryBookRepository bookRepository;

    @BeforeEach
    void setUp() {
        bookRepository = new InMemoryBookRepository();
    }

    @Test
    @DisplayName("도서를 추가할 수 있어야 합니다.")
    void testAdd() {
        // Given
        Book book = Book.createBook(1, "Book 1", "Author 1", 100, null, null);

        // When
        bookRepository.add(book);

        // Then
        List<Book> allBooks = bookRepository.findAll();
        assertThat(allBooks).hasSize(1);
        assertThat(allBooks).contains(book);
    }

    @Test
    @DisplayName("도서 번호로 도서를 찾을 수 있어야 합니다.")
    void testFindByBookNumber() {
        // Given
        Book book1 = Book.createBook(1, "Book 1", "Author 1", 100, null, null);
        Book book2 = Book.createBook(2, "Book 2", "Author 2", 200, null, null);
        bookRepository.add(book1);
        bookRepository.add(book2);

        // When
        Optional<Book> foundBook = bookRepository.findByBookNumber(2);

        // Then
        assertTrue(foundBook.isPresent());
        assertThat(foundBook).contains(book2);
    }

    @Test
    @DisplayName("제목으로 도서를 찾을 수 있어야 합니다.")
    void testFindListContainTitle() {
        // Given
        Book book1 = Book.createBook(1, "Sample Book 1", "Author 1", 100, null, null);
        Book book2 = Book.createBook(2, "Book 2", "Author 2", 200, null, null);
        Book book3 = Book.createBook(3, "Sample Book 3", "Author 3", 300, null, null);
        bookRepository.add(book1);
        bookRepository.add(book2);
        bookRepository.add(book3);

        // When
        List<Book> foundBooks = bookRepository.findListContainTitle("Sample");

        // Then
        assertThat(foundBooks).hasSize(2);
        assertThat(foundBooks).contains(book1, book3);
    }

    @Test
    @DisplayName("도서를 삭제할 수 있어야 합니다.")
    void testDelete() {
        // Given
        Book book1 = Book.createBook(1, "Book 1", "Author 1", 100, null, null);
        Book book2 = Book.createBook(2, "Book 2", "Author 2", 200, null, null);
        bookRepository.add(book1);
        bookRepository.add(book2);

        // When
        bookRepository.delete(book1);

        // Then
        List<Book> allBooks = bookRepository.findAll();
        assertThat(allBooks).hasSize(1);
        assertThat(allBooks).doesNotContain(book1);
    }

    @Test
    @DisplayName("다음 도서 번호 조회 시 마지막 도서 번호 + 1을 반환해야 합니다.")
    void testGetNextBookNumber() {
        long bookNumber = 59;
        // Given
        Book book = Book.createBook(bookNumber, "Book 1", "Author 1", 100, null, null);
        bookRepository.add(book);

        // When
        long nextBookNumber = bookRepository.getNextBookNumber();

        // Then
        assertEquals(bookNumber + 1, nextBookNumber);
    }
}
