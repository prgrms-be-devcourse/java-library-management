package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookStatus;
import org.example.exception.ExceptionCode;
import org.example.repository.FileRepository;
import org.example.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ServiceWithFileRepositoryTest {
    private Repository repository;
    private LibraryManagementService libraryManagementService;
    private Integer nextBookId;
    private String bookInfoTestCsvPath = "src/test/resources/bookInfoTest.csv";

    @BeforeEach
    void beforeEach() {
        File file = new File(bookInfoTestCsvPath);
        if (file.exists()) file.delete();
        repository = new FileRepository(bookInfoTestCsvPath);
        libraryManagementService = new LibraryManagementService(repository);
        nextBookId = libraryManagementService.getNextBookId();
    }

    @Test
    @DisplayName("도서 등록 성공")
    void registerBook_Success() {
        //given
        Integer initialNextBookId = nextBookId;

        //when
        for (int i = 1; i <= 6; i++) {
            libraryManagementService.registerBook(createBook());
        }

        //then
        assertEquals(repository.findAllBooks().size(), 6);
        assertEquals(nextBookId, initialNextBookId + 6);
    }

    @Test
    @DisplayName("전체 도서 목록 조회 성공")
    void searchBooks_Success() {
        //given
        Integer initialNextBookId = nextBookId;
        for (int i = 1; i <= 6; i++) {
            libraryManagementService.registerBook(createBook());
        }

        //when
        List<Book> books = libraryManagementService.searchBooks();

        //then
        assertEquals(repository.findAllBooks().size(), 6);
        assertEquals(books.size(), initialNextBookId + 5);
    }

    @Test
    @DisplayName("제목으로 도서 검색 성공")
    void searchBookByTitle_Success() {
        //given
        libraryManagementService.registerBook(createBook("abc"));
        libraryManagementService.registerBook(createBook("cka"));
        libraryManagementService.registerBook(createBook("def"));
        libraryManagementService.registerBook(createBook("ghi"));

        //when
        List<Book> books = libraryManagementService.searchBookByTitle("a");

        //then
        assertEquals(books.size(), 2);
    }

    @Test
    @DisplayName("도서 대여 성공")
    void borrowBook_Success() {
        //given
        libraryManagementService.registerBook(createBook());

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.borrowBook(1);

        //then
        assertEquals(repository.findBookById(1).get().getStatus(), BookStatus.BORROWING);
        assertEquals(exceptionCode, Optional.empty());
    }

    @Test
    @DisplayName("도서 대여 실패 - 대여중인 도서인 경우")
    void borrowBook_Fail_Borrowing() {
        //given
        libraryManagementService.registerBook(createBook(BookStatus.BORROWING));

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.borrowBook(1);

        //then
        assertEquals(exceptionCode.get().getMessage(), ExceptionCode.ALREADY_BORROWED.getMessage());
    }

    @Test
    @DisplayName("도서 대여 실패 - 정리중인 도서인 경우")
    void borrowBook_Fail_Organizing() {
        //given
        libraryManagementService.registerBook(createBook(BookStatus.ORGANIZING, Instant.now()));

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.borrowBook(1);

        //then
        assertEquals(exceptionCode.get().getMessage(), ExceptionCode.BEING_ORGANIZED.getMessage());
    }

    @Test
    @DisplayName("도서 대여 실패 - 분실된 도서인 경우")
    void borrowBook_Fail_Lost() {
        //given
        libraryManagementService.registerBook(createBook(BookStatus.LOST));

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.borrowBook(1);

        //then
        assertEquals(exceptionCode.get().getMessage(), ExceptionCode.LOST.getMessage());
    }

    @Test
    @DisplayName("도서 반납 성공")
    void returnBook_Success() {
        //given
        libraryManagementService.registerBook(createBook(BookStatus.BORROWING));

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.returnBook(1);

        //then
        assertEquals(repository.findBookById(1).get().getStatus(), BookStatus.ORGANIZING);
        assertEquals(exceptionCode, Optional.empty());
    }

    @Test
    @DisplayName("도서 반납 실패 - 원래 대여가 가능한 도서인 경우")
    void returnBook_Fail_BorrrowAvailable() {
        //given
        libraryManagementService.registerBook(createBook(BookStatus.BORROW_AVAILABE));

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.returnBook(1);

        //then
        assertEquals(exceptionCode.get().getMessage(), ExceptionCode.AVAILABLE_FOR_BORROW.getMessage());
    }

    @Test
    @DisplayName("도서 분실 성공")
    void lostBook_Success() {
        //given
        libraryManagementService.registerBook(createBook());

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.lostBook(1);

        //then
        assertEquals(repository.findBookById(1).get().getStatus(), BookStatus.LOST);
        assertEquals(exceptionCode, Optional.empty());
    }

    @Test
    @DisplayName("도서 분실 실패 - 이미 분실 처리된 도서인 경우")
    void lostBook_Fail_Lost() {
        //given
        libraryManagementService.registerBook(createBook(BookStatus.LOST));

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.lostBook(1);

        //then
        assertEquals(exceptionCode.get().getMessage(), ExceptionCode.LOST.getMessage());
    }

    @Test
    @DisplayName("도서 삭제 성공")
    void deleteBook_Success() {
        //given
        libraryManagementService.registerBook(createBook());

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.deleteBook(1);

        //then
        assertFalse(repository.findBookById(1).isPresent());
        assertEquals(exceptionCode, Optional.empty());
    }

    @Test
    @DisplayName("도서 삭제 실패 - 존재하지 않는 도서인 경우")
    void deleteBook_Fail_NotExist() {
        //given

        //when
        Optional<ExceptionCode> exceptionCode = libraryManagementService.lostBook(1);

        //then
        assertEquals(exceptionCode.get().getMessage(), ExceptionCode.INVALID_BOOK.getMessage());
    }

    private Book createBook() {
        return new Book(nextBookId++, "testTitle", "testAuthor", 123);
    }

    private Book createBook(String title) {
        return new Book(nextBookId++, title, "testAuthor", 123);
    }

    private Book createBook(BookStatus status) {
        return new Book(nextBookId++, "testTitle", "testAuthor", 123, status);
    }

    private Book createBook(BookStatus status, Instant returnTime) {
        return new Book(nextBookId++, "testTitle", "testAuthor", 123, status, Instant.now());
    }
}