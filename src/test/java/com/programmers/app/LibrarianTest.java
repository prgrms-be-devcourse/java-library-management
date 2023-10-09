package com.programmers.app;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programmers.app.book.domain.Book;
import com.programmers.app.book.domain.BookStatus;
import com.programmers.app.book.dto.BookRequest;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.repository.NormalBookRepository;
import com.programmers.app.book.repository.TestBookRepository;
import com.programmers.app.book.service.BookService;
import com.programmers.app.exception.ActionNotAllowedException;
import com.programmers.app.exception.BookNotFoundException;
import com.programmers.app.exception.messages.ExceptionMessages;
import com.programmers.app.file.BookFileManager;
import com.programmers.app.file.FileManager;

public class LibrarianTest {

    static final String ACTUAL_FILE_PATH = "src/main/resources/books.json";
    static final String TEST_FILE_PATH = "src/test/resources/booksTest.json";

    final BookRepository bookRepository = new TestBookRepository();
    final BookService bookService = new BookService(bookRepository);

    @AfterEach
    public void clearRepository() {
        bookRepository.findAllBooks()
                .orElse(Collections.emptyList())
                .forEach(bookRepository::delete);
    }

    @Test
    @DisplayName("새로운 도서가 대여 가능 상태로 생성된다.")
    public void successfullyRegisterBookInPlace() {
        //given
        BookRequest bookRequest = new BookRequest("오픈 인 더 게임", "나심 탈레브", 444);

        //when
        bookService.register(bookRequest);
        List<Book> bookList = bookRepository.findAllBooks().get();

        //then
        assertEquals(1, bookList.size());
        bookList.forEach(book -> assertTrue(book.isInPlace()));
    }

    @Test
    @DisplayName("도서가 등록될 때마다 증가된 번호를 부여받는다.")
    public void generateIncrementedBookNumber() {
        //given
        BookRequest bookRequest1 = new BookRequest("오픈 인 더 게임", "나심 탈레브", 444);
        BookRequest bookRequest2 = new BookRequest("위기의 역사", "오건영", 480);

        //when
        bookService.register(bookRequest1);
        bookService.register(bookRequest2);

        //then
        assertEquals(2, bookRepository.getLastBookNumber());
    }

    @Test
    @DisplayName("도서 전체 조회에 성공한다.")
    public void successfullyFindAllBooks() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        bookService.register(new BookRequest("위기의 역사", "오건영", 480));

        //when
        List<Book> bookList = bookService.findAllBooks();

        //then
        assertEquals(2, bookList.size());
    }

    @Test
    @DisplayName("도서가 존재하지 않아 전체 조회에 실패한다.")
    public void failToFindBooksAsNoBookLoaded() {
        //given

        //when
        final RuntimeException exception = assertThrows(
                BookNotFoundException.class,
                bookService::findAllBooks);

        //then
        assertEquals(ExceptionMessages.NO_BOOK_STORED.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("검색한 제목을 포함한 도서들 조회에 성공한다.")
    public void successfullyFindAllBooksByTitle() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        bookService.register(new BookRequest("위기의 역사", "오건영", 480));
        bookService.register(new BookRequest("게임이론", "로저 메케인", 449));

        //when
        final List<Book> bookList = bookService.findByTitle("게임");

        //then
        assertEquals(2, bookList.size());
    }

    @Test
    @DisplayName("검색 결과에 맞는 도서가 없어 조회에 실패한다.")
    public void failToFindTitleAsNoBookMatching() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));

        //when
        final RuntimeException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.findByTitle("역사"));

        //then
        assertEquals(ExceptionMessages.TITLE_NONEXISTENT.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("도서 조회시, 정리가 완료된 도서들의 상태 변경 확인에 성공한다.")
    public void successfullyCheckStatusUpdatedInFiveMinutesAfterReturned() throws InterruptedException {
        //given
        Book bookRaw = new Book(
                1,
                "오픈 인 더 게임",
                "나심 탈레브",
                444,
                BookStatus.ON_ARRANGEMENT,
                LocalDateTime.now().minusMinutes(4).minusSeconds(59));
        bookRepository.add(bookRaw);

        Book findAllResult = bookService.findAllBooks().get(0);
        Book findTitleResult = bookService.findByTitle("게임").get(0);
        assertTrue(findAllResult.isOnArrangement()); //guarantee it's on arrangement
        assertTrue(findTitleResult.isOnArrangement());

        //when
        sleep(1000);
        findAllResult = bookService.findAllBooks().get(0);
        findTitleResult = bookService.findByTitle("게임").get(0);

        //then
        assertTrue(findAllResult.isInPlace());
        assertTrue(findTitleResult.isInPlace());
    }

    @Test
    @DisplayName("도서 대여에 성공한다.")
    public void successfullyBorrowBook() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int bookNumber = bookRepository.getLastBookNumber();

        //when
        bookService.borrowBook(bookNumber);

        //then
        Book book = bookRepository.findByBookNumber(bookNumber).get();
        assertTrue(book.isBorrowed());
    }

    @Test
    @DisplayName("이미 대여중인 도서 대여에 실패한다.")
    public void failToBorrowAlreadyBorrowedBook() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int bookNumber = bookRepository.getLastBookNumber();

        //when
        bookService.borrowBook(bookNumber);
        RuntimeException exception = assertThrows(
                ActionNotAllowedException.class,
                () -> bookService.borrowBook(bookNumber));

        //then
        assertEquals(ExceptionMessages.ALREADY_BORROWED.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("분실 처리된 도서 대여에 실패한다.")
    public void failToBorrowLostBook() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int bookNumber = bookRepository.getLastBookNumber();
        bookService.reportLost(bookNumber);

        //when
        RuntimeException exception = assertThrows(
                ActionNotAllowedException.class,
                () -> bookService.borrowBook(bookNumber));

        //then
        assertEquals(ExceptionMessages.BOOK_LOSS_REPORTED.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("정리중인 도서 대여에 실패한다.")
    public void failToBorrowBookBeingArranged() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int bookNumber = bookRepository.getLastBookNumber();
        bookService.borrowBook(bookNumber);
        bookService.returnBook(bookNumber);

        //when
        RuntimeException exception = assertThrows(
                ActionNotAllowedException.class,
                () -> bookService.borrowBook(bookNumber));

        //then
        assertEquals(ExceptionMessages.BOOK_ON_ARRANGEMENT.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 도서 번호에 대한 대여를 실패한다.")
    public void failToBorrowAsBookNotExist() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));

        //when
        int incorrectBookNumber = bookRepository.getLastBookNumber() + 1;
        RuntimeException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.borrowBook(incorrectBookNumber));

        //then
        assertEquals(ExceptionMessages.BOOK_NUMBER_NONEXISTENT.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("도서 반납 후 5분이 지난 도서 대여에 성공한다.")
    public void borrowBookInFiveMinutesAfterReturned() throws InterruptedException {
        //given
        Book bookRaw = new Book(
                1,
                "오픈 인 더 게임",
                "나심 탈레브",
                444,
                BookStatus.ON_ARRANGEMENT,
                LocalDateTime.now().minusMinutes(4).minusSeconds(59));
        bookRepository.add(bookRaw);

        int bookNumber = bookRepository.getLastBookNumber();
        Book book = bookRepository.findByBookNumber(bookNumber).get();
        assertTrue(book.isOnArrangement()); //guarantee it's on arrangement

        //when
        sleep(1000);
        bookService.borrowBook(bookNumber);

        //then
        book = bookRepository.findByBookNumber(bookNumber).get();
        assertTrue(book.isBorrowed());
    }

    @Test
    @DisplayName("분실 신고되지 않은 도서들의 분실 신고에 성공한다.")
    public void successfullyReportLostBook() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        bookService.register(new BookRequest("위기의 역사", "오건영", 480));
        bookService.register(new BookRequest("게임이론", "로저 메케인", 449));
        int oldestBookNumber = bookRepository.getLastBookNumber() - 2; //book in place

        int bookNumber = bookRepository.getLastBookNumber() - 1; //book borrowed
        bookService.borrowBook(bookNumber);

        int newestBookNumber = bookRepository.getLastBookNumber(); //book returned
        bookService.borrowBook(newestBookNumber);
        bookService.returnBook(newestBookNumber);

        //when
        bookService.reportLost(oldestBookNumber);
        bookService.reportLost(bookNumber);
        bookService.reportLost(newestBookNumber);

        //then
        bookService.findAllBooks()
                .forEach(book -> assertTrue(book.isLost()));
    }

    @Test
    @DisplayName("이미 분실 처리된 도서 분실 신고를 실패한다")
    public void failToReportBookAlreadyReportedAsLost() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int bookNumber = bookRepository.getLastBookNumber();
        bookService.reportLost(bookNumber);

        //when
        RuntimeException exception = assertThrows(
                ActionNotAllowedException.class,
                () -> bookService.reportLost(bookNumber));

        //then
        assertEquals(ExceptionMessages.ALREADY_REPORTED_LOST.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 도서 번호에 대한 분실 신고 처리에 실패한다.")
    public void failToReportAsBookNotExist() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));

        //when
        int incorrectBookNumber = bookRepository.getLastBookNumber() + 1;
        RuntimeException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.reportLost(incorrectBookNumber));

        //then
        assertEquals(ExceptionMessages.BOOK_NUMBER_NONEXISTENT.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("대여중인 도서와 분실 신고된 도서 반납에 성공한다")
    public void successfullyReturnBook() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        bookService.register(new BookRequest("위기의 역사", "오건영", 480));
        int bookNumber = bookRepository.getLastBookNumber() - 1;
        bookService.borrowBook(bookNumber);

        int newestBookNumber = bookRepository.getLastBookNumber();
        bookService.reportLost(newestBookNumber);

        //when
        bookService.returnBook(bookNumber);
        bookService.returnBook(newestBookNumber);

        //then
        bookService.findAllBooks()
                .forEach(book -> {
                    assertTrue(book.isOnArrangement());
                    assertFalse(book.isDoneArranging());
                });
    }

    @Test
    @DisplayName("대여 가능한 도서 반납을 실패한다.")
    public void failToReturnBookAsInPlace() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int bookNumber = bookRepository.getLastBookNumber();

        //when
        RuntimeException exception = assertThrows(ActionNotAllowedException.class,
                () -> bookService.returnBook(bookNumber));

        //then
        assertEquals(ExceptionMessages.BOOK_IN_PLACE.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("이미 반납되어 정리중인 도서의 반납을 실패한다.")
    public void failToReturnBookAsAlreadyReturned() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int bookNumber = bookRepository.getLastBookNumber();
        bookService.borrowBook(bookNumber);
        bookService.returnBook(bookNumber);

        //when
        RuntimeException exception = assertThrows(ActionNotAllowedException.class,
                () -> bookService.returnBook(bookNumber));

        //then
        assertEquals(ExceptionMessages.ALREADY_RETURNED.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 도서 번호에 대한 반납 처리에 실패한다.")
    public void failToReturnAsBookNotExist() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));

        //when
        int incorrectBookNumber = bookRepository.getLastBookNumber() + 1;
        RuntimeException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.returnBook(incorrectBookNumber));

        //then
        assertEquals(ExceptionMessages.BOOK_NUMBER_NONEXISTENT.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("모든 상태의 도서들의 삭제처리를 성공한다.")
    public void successfullyDeleteBook() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        bookService.register(new BookRequest("위기의 역사", "오건영", 480));
        bookService.register(new BookRequest("게임이론", "로저 메케인", 449));
        bookService.register(new BookRequest("Dynamics and Controls", "Thomas Peacock", 2523));

        int oldestBookNumber = bookRepository.getLastBookNumber() - 3;

        int oldBookNumber = bookRepository.getLastBookNumber() - 2;
        bookService.borrowBook(oldBookNumber);

        int newBookNumber = bookRepository.getLastBookNumber() - 1;
        bookService.reportLost(newBookNumber);

        int newestBookNumber = bookRepository.getLastBookNumber();
        bookService.borrowBook(newestBookNumber);
        bookService.returnBook(newestBookNumber);

        //when
        bookService.deleteBook(oldestBookNumber);
        bookService.deleteBook(oldBookNumber);
        bookService.deleteBook(newBookNumber);
        bookService.deleteBook(newestBookNumber);

        //then
        RuntimeException exception = assertThrows(
                BookNotFoundException.class,
                bookService::findAllBooks);
        assertEquals(ExceptionMessages.NO_BOOK_STORED.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("존재하지 않는 도서 번호에 대한 삭제처리를 실패한다.")
    public void failToDeleteBookAsBookNumberNotExist() {
        //given
        bookService.register(new BookRequest("오픈 인 더 게임", "나심 탈레브", 444));
        int incorrectBookNumber = bookRepository.getLastBookNumber() + 1;

        //when
        RuntimeException exception = assertThrows(
                BookNotFoundException.class,
                () -> bookService.deleteBook(incorrectBookNumber));

        //then
        assertEquals(ExceptionMessages.BOOK_NUMBER_NONEXISTENT.getExceptionMessage(), exception.getMessage());
    }

    @Test
    @DisplayName("일반 모드로 실행 시, 저장된 책들을 받아 오는데 성공한다.")
    public void successfullyLoadDataFromFile() throws IOException {
        //given
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        FileManager<HashMap<Integer, Book>, List<Book>> fileManager = new BookFileManager(ACTUAL_FILE_PATH, gson);

        //when
        BookRepository normalBookRepository = new NormalBookRepository(fileManager);
        BookService normalBookService = new BookService(normalBookRepository);

        //then
        assertDoesNotThrow(normalBookService::findAllBooks);
    }

    @Test
    @DisplayName("일반 모드로 실행 시, 메모리에 있는 책들을 저장하는데 성공한다.")
    public void successfullyWriteStoredBooks() throws IOException {
        //given
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        FileManager<HashMap<Integer, Book>, List<Book>> fileManager = new BookFileManager(TEST_FILE_PATH, gson);

        //when
        BookRepository normalBookRepository = new NormalBookRepository(fileManager);
        Book book = new Book(
                1,
                "오픈 인 더 게임",
                "나심 탈레브",
                444,
                BookStatus.IN_PLACE,
                null);
        normalBookRepository.add(book);

        //then
        assertDoesNotThrow(normalBookRepository::save);
    }
}
