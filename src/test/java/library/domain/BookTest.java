package library.domain;

import library.exception.BookErrorMessage;
import library.exception.BookException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static library.domain.BookStatus.*;
import static library.exception.BookErrorMessage.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookTest {

    private static Stream<Arguments> provideBookStatusForRentTest() {
        return Stream.of(
                Arguments.of(RENTED, null, BOOK_ALREADY_RENTED),
                Arguments.of(IN_CLEANUP, LocalDateTime.now(), BOOK_IN_CLEANUP),
                Arguments.of(LOST, null, BOOK_LOST)
        );
    }

    private static Stream<Arguments> provideBookStatusForReturnTest() {
        return Stream.of(
                Arguments.of(AVAILABLE, null, BOOK_ALREADY_AVAILABLE),
                Arguments.of(IN_CLEANUP, LocalDateTime.now(), BOOK_IN_CLEANUP)
        );
    }

    private static Stream<Arguments> provideReturnTimeForRentTest() {
        return Stream.of(
                Arguments.of(LocalDateTime.now().minusMinutes(5), AVAILABLE),
                Arguments.of(LocalDateTime.now().minusMinutes(3), IN_CLEANUP)
        );
    }

    private static Stream<Arguments> provideBookStatusForLostTest() {
        return Stream.of(
                Arguments.of(AVAILABLE),
                Arguments.of(IN_CLEANUP),
                Arguments.of(RENTED)
        );
    }

    @Test
    @DisplayName("도서가 '대여 가능' 상태 일 때만 도서를 대여할 수 있고, 도서가 대여되면 상태를 '대여중'으로 바꿔야합니다.")
    void test_givenAvailableBook_whenRent_thenGoesForRent() {
        // Given
        Book availableBook = Book.createBook(1L, "Title", "Author", 1, null, AVAILABLE);

        // When
        availableBook.toRent();

        // Then
        assertThat(availableBook.getStatus()).isEqualTo(RENTED);
    }

    @DisplayName("도서가 '대여 가능' 상태가 아닌 경우 해당 도서를 대여할 수 없는 이유에 대해 알려줘야합니다.")
    @ParameterizedTest(name = "도서가 {0} 상태 일 때는 도서를 대여할 수 없습니다. 이때 도서를 대여할 수 없는 이유는 {1}입니다.")
    @MethodSource("provideBookStatusForRentTest")
    void test_givenBookStatus_whenRent_thenThrowsException(BookStatus bookStatus, LocalDateTime returnDueDate, BookErrorMessage bookErrorMessage) {
        // Given
        Book book = Book.createBook(1L, "Title", "Author", 1, returnDueDate, bookStatus);

        // When, Then
        assertThat(assertThrows(BookException.class, book::toRent).getMessage())
                .isEqualTo(bookErrorMessage.getMessage());
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

    @DisplayName("도서가 대여가능/정리 중 상태인 경우 반납할 수 없는 이유에 대해 알려줘야합니다.")
    @ParameterizedTest(name = "도서가 {0} 상태 일 때는 도서를 반납할 수 없습니다. 이때 도서를 반납할 수 없는 이유는 {1}입니다.")
    @MethodSource("provideBookStatusForReturnTest")
    void test_givenBookStatus_whenReturn_thenThrowsException(BookStatus bookStatus, LocalDateTime returnDueDate, BookErrorMessage bookErrorMessage) {
        // Given
        Book book = Book.createBook(1L, "Title", "Author", 1, returnDueDate, bookStatus);

        // When, Then
        assertThat(assertThrows(BookException.class, book::toReturn).getMessage())
                .isEqualTo(bookErrorMessage.getMessage());
    }

    @DisplayName("'도서 정리중' 상태에서 5분이 지난 도서는 '대여 가능'으로 바뀌어야합니다.")
    @ParameterizedTest(name = "도서가 {0} 상태이고, 반납 예정일이 {1}일 때, 5분이 지나면 도서의 상태는 {2}이어야 합니다.")
    @MethodSource("provideReturnTimeForRentTest")
    void test_givenBookInCleanUp_when5MinutesPassed_thenGoesForAvailable(LocalDateTime returnDateTime, BookStatus expectedBookStatus) {
        // Given
        Book book = Book.createBook(1L, "Title1", "Author1", 59, returnDateTime, IN_CLEANUP);

        // When, Then
        assertThat(book.getStatus()).isEqualTo(expectedBookStatus);
    }

    @DisplayName("도서를 분실 처리하면 도서는 '분실됨' 상태가 되어야합니다.")
    @ParameterizedTest(name = "도서가 {0} 상태 일 때는 도서를 분실 처리할 수 있습니다.")
    @MethodSource("provideBookStatusForLostTest")
    void test_givenBook_whenLost_thenGoesForLost(BookStatus bookStatus) {
        // Given
        Book book = Book.createBook(1L, "Title1", "Author1", 59, null, bookStatus);

        // When, Then
        book.toLost();
        assertThat(book.getStatus()).isEqualTo(LOST);
    }

    @Test
    @DisplayName("도서가 이미 분실 중이면 에러가 발생해야합니다.")
    void test_givenLostBook_whenLost_thenThrowsException() {
        // Given
        Book book = Book.createBook(123L, "Title", "Author", 59, null, LOST);

        // When, Then
        assertThat(assertThrows(BookException.class, book::toLost).getMessage())
                .isEqualTo(BOOK_ALREADY_LOST.getMessage());
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
