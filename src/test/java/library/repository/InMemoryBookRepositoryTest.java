package library.repository;

import library.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(allBooks)
                .hasSize(1)
                .contains(book);
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
        assertThat(foundBook)
                .isPresent()
                .contains(book2);
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
        assertThat(foundBooks)
                .hasSize(2)
                .contains(book1, book3);
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
        assertThat(allBooks)
                .hasSize(1)
                .doesNotContain(book1);
    }

    @DisplayName("도서 번호 조회 시 마지막 도서 번호 + 1을 반환해야 합니다.")
    @ParameterizedTest(name = "마지막 도서 번호가 {0}이면 다음 도서 번호는 {1}입니다.")
    @CsvSource(delimiter = ',', value = {"59,60"})
    void testGetNextBookNumber(long lastBookNumber, long expectedNextBookNumber) {
        // Given
        Book book = Book.createBook(lastBookNumber, "Book 1", "Author 1", 100, null, null);
        bookRepository.add(book);

        // When
        long nextBookNumber = bookRepository.getNextBookNumber();

        // Then
        assertThat(nextBookNumber).isEqualTo(expectedNextBookNumber);
    }
}
