package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.mock.MockRepository;
import com.programmers.library.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.programmers.library.domain.Book.*;
import static com.programmers.library.domain.BookStatusType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LibraryServiceTest {

    LibraryService libraryService;
    Repository repository;


    @BeforeEach
    public void setUp(){
        repository = new MockRepository();
        libraryService = new LibraryService(repository);
    }

    @Test
    @DisplayName("도서 등록 메서드 호출 테스트")
    public void callRegisterBookTest(){
        // Given
        String title = "제목 1";
        String author ="저자 1";
        Integer page = 100;

        // When
        libraryService.registerBook(title,author,page);

        List<Book> findBook = libraryService.findBooksByTitle(title);
        // Then
        assertEquals(1L, findBook.get(0).getBookId());
    }

    @Test
    @DisplayName("제목 미입력으로 인한 도서 등록 실패 테스트")
    public void bookRegisterFailedByTitle(){
        // Given
        String title = "";
        String author ="저자 1";
        Integer page = 100;

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.registerBook(title,author,page);
        });

        // Then
        String errorMessage = "[System] 도서 제목을 정확하게 입력해주세요.";
        assertEquals(errorMessage,  result.getMessage());
    }

    @Test
    @DisplayName("저자 미입력으로 인한 도서 등록 실패 테스트")
    public void bookRegisterFailedByAuthor(){
        // Given
        String title = "제목 1";
        String author ="";
        Integer page = 100;

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.registerBook(title,author,page);
        });

        // Then
        String errorMessage = "[System] 도서의 저자를 정확하게 입력해주세요.";
        assertEquals(errorMessage,  result.getMessage());
    }

    @Test
    @DisplayName("도서 페이지 음수값 입력으로 인한 도서 등록 실패 테스트")
    public void bookRegisterFailedByPage(){
        // Given
        String title = "제목 1";
        String author ="저자 1";
        Integer page = -23;

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.registerBook(title,author,page);
        });

        // Then
        String errorMessage = "[System] 도서의 페이지 정보로 음수값은 등록할 수 없습니다.";
        assertEquals(errorMessage,  result.getMessage());
    }


    @Test
    @DisplayName("전체 도서 조회 메서드 호출 테스트")
    public void callFindAllBooksTest(){
        // Given
        List<Book> books = List.of(
                createRentableBook(1L, "제목 1", "저자 1", 100),
                createRentableBook(2L, "제목 2", "저자 2", 150)
        );

        repository.register(books.get(0));
        repository.register(books.get(1));

        // When
        List<Book> findBooks = libraryService.findAllBooks();

        // Then
        assertEquals(2, findBooks.size());
        assertEquals("제목 1", findBooks.get(0).getTitle());
        assertEquals("제목 2", findBooks.get(1).getTitle());
    }

    @Test
    @DisplayName("책 번호로 책 찾기 호출 테스트")
    public void findBookByIdTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author ="저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);

        // When
        Book findBook = libraryService.findBookById(bookId);

        // Then
        assertEquals(findBook.getTitle(), "제목 1");
    }

    @Test
    @DisplayName("도서 상태 변경 메서드 호출 테스트")
    public void updateStatusTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        Book book = createRentableBook(bookId, title, author, page);
        libraryService.registerBook(title,author,page);

        // When
        libraryService.updateStatus(book, LOST);

        // Then
        assertEquals(book.getBookStatus().getDescription(), "분실");
    }

    @Test
    @DisplayName("존재하지 않는 도서번호에 대한 예외처리 테스트")
    public void bookNotFoundExceptionTest(){
        // Given
        Long bookId = 100L;

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.findBookById(bookId);
        });

        // Then
        String bookNotFound = "[System] 입력하신 도서를 찾을 수 없습니다.";
        assertEquals(bookNotFound,  result.getMessage());
    }

    @Test
    @DisplayName("도서 대여 성공 테스트")
    public void rentalBookSuccessTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);

        // When
        libraryService.rentalBook(bookId);

        // Then
        Book findBook = libraryService.findBookById(bookId);
        assertEquals(RENTED, findBook.getBookStatus());
    }

    @Test
    @DisplayName("분실 처리로 인한 도서 대여 실패 테스트")
    public void rentalBookFailedByLostTest(){
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);

        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, LOST);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.rentalBook(bookId);
        });

        // Then
        String failedRental = "[System] 분실 처리된 도서로 대여가 불가능합니다.";
        assertEquals(result.getMessage(), failedRental);
    }

    @Test
    @DisplayName("이미 대여중으로 인한 도서 대여 실패 테스트")
    public void rentalBookFailedByRentedTest(){
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);

        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, RENTED);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.rentalBook(bookId);
        });

        // Then
        String failedRental = "[System] 이미 대여중인 도서입니다.";
        assertEquals(result.getMessage(), failedRental);
    }

    @Test
    @DisplayName("정리중으로 인한 도서 대여 실패 테스트")
    public void rentalBookFailedByOrganizingTest(){
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);

        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, ORGANIZING);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.rentalBook(bookId);
        });

        // Then
        String failedRental = "[System] 도서가 정리중입니다. 잠시 후 다시 시도해주세요.";
        assertEquals(result.getMessage(), failedRental);
    }

    @Test
    @DisplayName("도서 반납 성공 테스트")
    public void returnBookSuccessTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);
        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, RENTED);

        // When
        libraryService.returnBook(bookId);

        // Then
        assertEquals(ORGANIZING, findBook.getBookStatus());
    }

    @Test
    @DisplayName("대여 가능 도서 반납 실패 테스트")
    public void returnBookFailedByRentableTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);
        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, RENTABLE);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () ->{
            libraryService.returnBook(bookId);
        });

        // Then
        String failedReturn = "[System] 원래 대여가 가능한 도서입니다.";
        assertEquals(result.getMessage(), failedReturn);
    }

    @Test
    @DisplayName("정리중으로 인한 도서 반납 실패 테스트")
    public void returnBookFailedTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);
        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, ORGANIZING);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () ->{
            libraryService.returnBook(bookId);
        });

        // Then
        String failedReturn = "[System] 이미 반납되어 정리중인 도서입니다.";
        assertEquals(result.getMessage(), failedReturn);
    }

    @Test
    @DisplayName("도서 분실 처리 성공 테스트")
    public void lostBookSuccessTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);
        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, RENTED);

        // When
        libraryService.lostBook(bookId);

        // Then
        assertEquals(LOST, findBook.getBookStatus());
    }

    @Test
    @DisplayName("이미 분실 처리 된 도서 분실 처리 실패 테스트")
    public void lostBookFailedByLostTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);
        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, LOST);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () ->{
            libraryService.lostBook(bookId);
        });

        // Then
        String failedReturn = "[System] 이미 분실 처리된 도서입니다.";
        assertEquals(result.getMessage(), failedReturn);
    }
    @Test
    @DisplayName("대여 가능한 도서 분실 처리 실패 테스트")
    public void lostBookFailedTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);
        Book findBook = libraryService.findBookById(bookId);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () ->{
            libraryService.lostBook(bookId);
        });

        // Then
        String failedReturn = "[System] 분실 처리할 수 없는 도서입니다.";
        assertEquals(result.getMessage(), failedReturn);
    }

    @Test
    @DisplayName("정리중 도서 분실 처리 실패 테스트")
    public void lostBookFailedByRentableTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);
        Book findBook = libraryService.findBookById(bookId);
        libraryService.updateStatus(findBook, ORGANIZING);

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () ->{
            libraryService.lostBook(bookId);
        });

        // Then
        String failedReturn = "[System] 분실 처리할 수 없는 도서입니다.";
        assertEquals(result.getMessage(), failedReturn);
    }
}