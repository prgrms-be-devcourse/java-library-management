package library.domain;

import library.exception.BookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static library.domain.BookStatus.*;
import static library.exception.BookErrorMessage.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    @Test
    @DisplayName("도서가 '대여 가능' 상태 일 때만 도서를 대여할 수 있고, 도서가 대여되면 상태를 '대여중'으로 바꿔야합니다.")
    void test_givenAvailableBook_whenRent_thenGoesForRent() {
        // Given
        Book availableBook = Book.createBook(1L, "Title", "Author", 1, null, AVAILABLE);

        // When, Then
        availableBook.toRent();

        // Then
        assertThat(availableBook.getStatus()).isEqualTo(RENTED);
    }

    @Test
    @DisplayName("도서가 '대여 가능' 상태가 아닌 경우 해당 도서를 대여할 수 없는 이유에 대해 알려줘야합니다.")
    void test_givenBookNotAvailable_whenRent_thenThrowsException() {
        // Given
        Book rentedBook = Book.createBook(1L, "Title1", "Author1", 59, null, RENTED);
        Book inCleanUpBook = Book.createBook(2L, "Title2", "Author2", 59, LocalDateTime.now(), IN_CLEANUP);
        Book lostBook = Book.createBook(3L, "Title3", "Author3", 59, null, LOST);

        // When, Then
        assertThat(assertThrows(BookException.class, rentedBook::toRent).getMessage())
                .isEqualTo(BOOK_ALREADY_RENTED.getMessage());
        assertThat(assertThrows(BookException.class, inCleanUpBook::toRent).getMessage())
                .isEqualTo(BOOK_IN_CLEANUP.getMessage());
        assertThat(assertThrows(BookException.class, lostBook::toRent).getMessage())
                .isEqualTo(BOOK_LOST.getMessage());
    }

    @Test
    @DisplayName("도서가 '대여중' 상태 일 때는 도서를 반납할 수 있습니다. 이때 도서가 반납되면 도서의 상태는 '도서 정리중' 상태로 바뀌어야합니다.")
    void test_givenRentedBook_whenReturn_thenGoesForCleanUp() {
        // Given
        Book book = Book.createBook(1L, "Title", "Author", 59, LocalDateTime.now().minusHours(1), RENTED);

        // When
        book.toReturn();

        // Then
        assertThat(book.getStatus()).isEqualTo(IN_CLEANUP);
    }

    @Test
    @DisplayName("도서가 대여가능/정리 중 상태인 경우 반납할 수 없는 이유에 대해 알려줘야합니다.")
    void test_givenBookNotRented_whenReturn_thenThrowsException() {
        // Given
        Book availableBook = Book.createBook(1L, "Title2", "Author2", 59, null, AVAILABLE);
        Book inCleanUpBook = Book.createBook(2L, "Title2", "Author2", 59, LocalDateTime.now(), IN_CLEANUP);

        // When, Then
        assertThat(assertThrows(BookException.class, availableBook::toReturn).getMessage())
                .isEqualTo(BOOK_ALREADY_AVAILABLE.getMessage());
        assertThat(assertThrows(BookException.class, inCleanUpBook::toReturn).getMessage())
                .isEqualTo(BOOK_IN_CLEANUP.getMessage());
    }

    @Test
    @DisplayName("'도서 정리중' 상태에서 5분이 지난 도서는 '대여 가능'으로 바뀌어야합니다.")
    void test_givenBookInCleanUp_when5MinutesPassed_thenGoesForAvailable() {
        // Given
        Book afterThan5Minutes = Book.createBook(1L, "Title1", "Author1", 59, LocalDateTime.now().minusMinutes(5).minusSeconds(1), IN_CLEANUP);
        Book afterThan3Minutes = Book.createBook(2L, "Title2", "Author2", 59, LocalDateTime.now().minusMinutes(3), IN_CLEANUP);

        // When, Then
        assertThat(afterThan5Minutes.getStatus()).isEqualTo(AVAILABLE);
        assertThat(afterThan3Minutes.getStatus()).isEqualTo(IN_CLEANUP);
    }

    @Test
    @DisplayName("도서를 분실 처리하면 도서는 '분실됨' 상태가 되어야합니다. (도서가 이미 분실 중이면 에러 발생.)")
    void test_givenBook_whenLost_thenGoesForLost() {
        // Given
        Book book1 = Book.createBook(1L, "Title1", "Author1", 59, null, AVAILABLE);
        Book book2 = Book.createBook(2L, "Title2", "Author2", 59, null, IN_CLEANUP);
        Book book3 = Book.createBook(3L, "Title3", "Author3", 59, null, RENTED);
        Book book4 = Book.createBook(4L, "Title4", "Author4", 59, null, LOST);

        // When, Then
        book1.toLost();
        assertThat(book1.getStatus()).isEqualTo(LOST);
        book2.toLost();
        assertThat(book2.getStatus()).isEqualTo(LOST);
        book3.toLost();
        assertThat(book3.getStatus()).isEqualTo(LOST);
        assertThrows(BookException.class, book4::toLost);
    }

    @Test
    @DisplayName("만약 분실된 도서를 찾게된 경우 반납 처리를 하면 도서를 찾은 것으로 간주합니다. 이 경우 도서를 반납하는 것과 동일한 절차가 진행되어야합니다.")
    void test_givenLostBook_whenReturn_thenGoesForCleanUp() {
        Book book = Book.createBook(123L, "Title", "Author", 59, LocalDateTime.now().minusHours(1), LOST);

        book.toReturn();

        assertThat(book.getStatus()).isEqualTo(IN_CLEANUP);
    }

    @Test
    @DisplayName("equals & hashCode 테스트")
    void test_equalsAndHashCode() {
        // Given
        Book book1 = Book.createBook(1L, "Title1", "Author1", 59, null, AVAILABLE);
        Book book2 = Book.createBook(1L, "Title1", "Author1", 59, null, AVAILABLE);

        // When, Then
        assertThat(book1)
                .isEqualTo(book2)
                .hasSameHashCodeAs(book2);
    }
}
