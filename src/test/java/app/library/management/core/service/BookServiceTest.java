package app.library.management.core.service;

import app.library.management.core.domain.Book;
import app.library.management.core.domain.BookStatus;
import app.library.management.core.domain.util.BookStatusManager;
import app.library.management.core.repository.BookRepository;
import app.library.management.core.service.response.dto.BookServiceResponse;
import app.library.management.core.service.response.dto.status.ResponseState;
import app.library.management.core.service.response.dto.status.Stage;
import app.library.management.infra.port.dto.request.BookRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import static app.library.management.core.domain.BookStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceTest {

    private BookService bookService;
    private BookRepository bookRepositoryMock;
    private BookStatusManager bookStatusManagerMock;

    @BeforeEach
    public void setUp() {
        bookRepositoryMock = mock(BookRepository.class);
        bookStatusManagerMock = mock(BookStatusManager.class);
        bookService = new BookService(bookRepositoryMock, bookStatusManagerMock);
    }

    @DisplayName("도서를 등록할 수 있다.")
    @Test
    void register() {
        // given
        String title = "책1";
        String author = "작가1";
        int pages = 100;
        BookRequestDto bookRequestDto = new BookRequestDto(title, author, pages);
        ArgumentCaptor<Book> bookCaptor = ArgumentCaptor.forClass(Book.class);

        // when
        bookService.register(bookRequestDto);

        // then
        verify(bookRepositoryMock, times(1)).save(bookCaptor.capture());
        Book savedBook = bookCaptor.getValue();
        assertAll(
                () -> assertThat(savedBook.getTitle()).isEqualTo(title),
                () -> assertThat(savedBook.getAuthor()).isEqualTo(author),
                () -> assertThat(savedBook.getPages()).isEqualTo(pages)
        );
    }

    @DisplayName("도서 목록 전체를 조회할 수 있다.")
    @Test
    void findAll() {
        // given

        // when
        bookService.findAll();

        // then
        verify(bookRepositoryMock, times(1)).findAll();
    }

    @DisplayName("제목으로 도서를 검색할 수 있다.")
    @Test
    void findAllByTitle() {
        // given
        String title = "책제목";

        // when
        bookService.findAllByTitle(title);

        // then
        verify(bookRepositoryMock, times(1)).findByTitle(title);
    }

    /**
     * 대여
     */

    @DisplayName("id 값으로 [대여가능한 도서]를 대여할 수 있다.")
    @Test
    void rent() {
        // given
        long id = 1L;
        Book book = new Book(id, "책제목", "작가", 100, AVAILABLE, LocalDateTime.now());
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.of(book));

        // when
        BookServiceResponse bookServiceResponse = bookService.rent(id, updatedTime);

        // then
        verify(bookRepositoryMock, times(1)).update(any(Book.class));
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.SUCCESS),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.RENT),
                () -> assertThat(bookServiceResponse.getBookStatus()).isEqualTo(RENTED)
        );
    }

    @DisplayName("id 값으로 도서를 대여할 때, [없는 도서]에 대해서는 NOTFOUND_EXCEPTION 예외를 반환한다.")
    @Test
    void rentWithNotFoundExeption() {
        // given
        long id = 1L;
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when
        BookServiceResponse bookServiceResponse = bookService.rent(id, updatedTime);

        // then
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.NOTFOUND_EXCEPTION),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.RENT),
                () -> assertThat(bookServiceResponse.getBookStatus()).isNull()
        );
    }

    @DisplayName("id 값으로 도서를 대여할 때, [대여가능하지 않은 도서]에 대해서는 VALIDATION_EXCEPTION 예외를 반환한다.")
    @Test
    void rentWithValidationExeption() {
        // given
        long id = 1L;
        BookStatus bookStatus = LOST;
        Book book = new Book(id, "책제목", "작가", 100, bookStatus, LocalDateTime.now());
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.of(book));

        // when
        BookServiceResponse bookServiceResponse = bookService.rent(id, updatedTime);

        // then
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.VALIDATION_EXCEPTION),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.RENT),
                () -> assertThat(bookServiceResponse.getBookStatus()).isEqualTo(bookStatus)
        );
    }

    /**
     * 반납
     */

    @DisplayName("id 값으로 도서 상태가 [RENTED, LOST]인 도서를 반납할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideBookStatusWithRENTEDAndLOST")
    void returnBook(BookStatus bookStatus) {
        // given
        long id = 1L;
        Book book = new Book(id, "책제목", "작가", 100, bookStatus, LocalDateTime.now());
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.of(book));

        // when
        BookServiceResponse bookServiceResponse = bookService.returnBook(id, updatedTime);

        // then
        verify(bookRepositoryMock, times(1)).update(any(Book.class));
        verify(bookStatusManagerMock, times(1)).execute(any(Book.class), any(LocalDateTime.class));
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.SUCCESS),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.RETURN),
                () -> assertThat(bookServiceResponse.getBookStatus()).isEqualTo(ORGANIZING)
        );
    }

    @DisplayName("id 값으로 도서를 반납할 때, [없는 도서]에 대해서는 NOTFOUND_EXCEPTION 예외를 반환한다.")
    @Test
    void returnBookWithNotFoundExeption() {
        // given
        long id = 1L;
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when
        BookServiceResponse bookServiceResponse = bookService.returnBook(id, updatedTime);

        // then
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.NOTFOUND_EXCEPTION),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.RETURN),
                () -> assertThat(bookServiceResponse.getBookStatus()).isNull()
        );
    }

    @DisplayName("id 값으로 도서를 반납할 때, 도서 상태가 [AVAILABLE, ORGANIZING]인 도서에 대해서는 VALIDATION_EXCEPTION 예외를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideBookStatusWithAVAILABLEAndORGANIZING")
    void returnBookWithValidationExeption(BookStatus bookStatus) {
        // given
        long id = 1L;
        Book book = new Book(id, "책제목", "작가", 100, bookStatus, LocalDateTime.now());
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.of(book));

        // when
        BookServiceResponse bookServiceResponse = bookService.returnBook(id, updatedTime);

        // then
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.VALIDATION_EXCEPTION),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.RETURN),
                () -> assertThat(bookServiceResponse.getBookStatus()).isEqualTo(bookStatus)
        );
    }


    /**
     * 분실
     */

    @DisplayName("id 값으로 도서 상태가 [AVAILABLE, ORGANIZING, RENTED]인 도서를 분실신고할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideBookStatusWithAVAILABLEandORGANIZINGandRENTED")
    void reportLost(BookStatus bookStatus) {
        // given
        long id = 1L;
        Book book = new Book(id, "책제목", "작가", 100, bookStatus, LocalDateTime.now());
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.of(book));

        // when
        BookServiceResponse bookServiceResponse = bookService.reportLost(id, updatedTime);

        // then
        verify(bookRepositoryMock, times(1)).update(any(Book.class));
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.SUCCESS),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.LOST),
                () -> assertThat(bookServiceResponse.getBookStatus()).isEqualTo(LOST)
        );
    }

    @DisplayName("id 값으로 도서를 분실신고할 때, [없는 도서]에 대해서는 NOTFOUND_EXCEPTION 예외를 반환한다.")
    @Test
    void reportLostWithNotFoundExeption() {
        // given
        long id = 1L;
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when
        BookServiceResponse bookServiceResponse = bookService.reportLost(id, updatedTime);

        // then
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.NOTFOUND_EXCEPTION),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.LOST),
                () -> assertThat(bookServiceResponse.getBookStatus()).isNull()
        );
    }

    @DisplayName("id 값으로 도서를 분실신고할 때, [이미 분실된 도서]에 대해서는 VALIDATION_EXCEPTION 예외를 반환한다.")
    @Test
    void reportLostWithValidationExeption() {
        // given
        long id = 1L;
        BookStatus bookStatus = LOST;
        Book book = new Book(id, "책제목", "작가", 100, bookStatus, LocalDateTime.now());
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.of(book));

        // when
        BookServiceResponse bookServiceResponse = bookService.reportLost(id, updatedTime);

        // then
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.VALIDATION_EXCEPTION),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.LOST),
                () -> assertThat(bookServiceResponse.getBookStatus()).isEqualTo(bookStatus)
        );
    }


    /**
     * 삭제
     */
    @DisplayName("id 값으로 도서를 삭제할 수 있다.")
    @Test
    void delete() {
        // given
        long id = 1L;
        BookStatus bookStatus = AVAILABLE;
        Book book = new Book(id, "책제목", "작가", 100, bookStatus, LocalDateTime.now());
        LocalDateTime updatedTime = LocalDateTime.now();

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.of(book));

        // when
        BookServiceResponse bookServiceResponse = bookService.delete(id);

        // then
        verify(bookRepositoryMock, times(1)).delete(any(Book.class));
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.SUCCESS),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.DELETE),
                () -> assertThat(bookServiceResponse.getBookStatus()).isNull()
        );
    }

    @DisplayName("id 값으로 도서를 삭제할 때, [없는 도서]에 대해서는 NOTFOUND_EXCEPTION 예외를 반환한다.")
    @Test
    void deleteWithNotFoundExeption() {
        // given
        long id = 1L;

        when(bookRepositoryMock.findById(id)).thenReturn(Optional.empty());

        // when
        BookServiceResponse bookServiceResponse = bookService.delete(id);

        // then
        assertAll(
                () -> assertThat(bookServiceResponse.getResponseState()).isEqualTo(ResponseState.NOTFOUND_EXCEPTION),
                () -> assertThat(bookServiceResponse.getStage()).isEqualTo(Stage.DELETE),
                () -> assertThat(bookServiceResponse.getBookStatus()).isNull()
        );
    }


    private static Stream<Arguments> provideBookStatusWithRENTEDAndLOST() {
        return Stream.of(
                Arguments.of(RENTED),
                Arguments.of(LOST)
        );
    }

    private static Stream<Arguments> provideBookStatusWithAVAILABLEAndORGANIZING() {
        return Stream.of(
                Arguments.of(AVAILABLE),
                Arguments.of(ORGANIZING)
        );
    }

    private static Stream<Arguments> provideBookStatusWithAVAILABLEandORGANIZINGandRENTED() {
        return Stream.of(
                Arguments.of(AVAILABLE),
                Arguments.of(ORGANIZING),
                Arguments.of(RENTED)
        );
    }
}