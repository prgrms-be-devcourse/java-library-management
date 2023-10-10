package com.programmers.service;

import com.programmers.common.ErrorMessages;
import com.programmers.domain.Book;
import com.programmers.domain.BookState;
import com.programmers.provider.BookIdProvider;
import com.programmers.repository.BookRepository;
import com.programmers.repository.MemBookRepository;
import org.junit.jupiter.api.*;

import java.util.NoSuchElementException;

class BookServiceTest {
    private final BookRepository bookRepository;
    private final BookService bookService;

    private final Book availableBook;
    private final Book rentedBook;
    private final Book lostBook;

    BookServiceTest() {
        bookRepository = MemBookRepository.getInstance();
        BookService.setBookRepository(bookRepository);
        bookService = BookService.getInstance();

        this.availableBook = new Book(0, "대여가능한도서", "작가", 100, BookState.AVAILABLE);
        this.rentedBook = new Book(1, "빌린도서", "작가", 100, BookState.RENTED);
        this.lostBook = new Book(2, "잃어버린도서", "작가", 100, BookState.LOST);
    }

    @BeforeEach
    void setUp() {
        bookService.registerBook(availableBook);
        bookService.registerBook(rentedBook);
        bookService.registerBook(lostBook);
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void testRegisterBook() {
        Book book = new Book(BookIdProvider.generateBookId(), "새로운도서", "작가", 100, BookState.AVAILABLE);

        bookService.registerBook(book);

        Assertions.assertEquals(book, bookRepository.findBookByTitle("새로운도서").get(0));
        Assertions.assertEquals(4, bookRepository.findBookByTitle("도서").size());
    }

    @Test
    @DisplayName("전체 조회 테스트")
    void testGetAllBook() {
        Book book = new Book(BookIdProvider.generateBookId(), "새로운도서", "작가", 100, BookState.AVAILABLE);

        bookService.registerBook(book);

        Assertions.assertEquals(4, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("제목에 특정 문장이 포함된 도서 검색")
    void testFindBookByTitle() {
        Assertions.assertEquals(3, bookRepository.findBookByTitle("도서").size());
    }

    @Test
    @DisplayName("대여가 성공한 경우: 대여가능 도서")
    void testRentBook_withAvailableState_success() {
        bookService.rentBook(availableBook.getId());
        Assertions.assertEquals(BookState.RENTED, availableBook.getState());
    }

    @Test
    @DisplayName("대여가 실패한 경우: 대여중인 도서")
    void testRentBook_withRentedState_throwsIllegalStateException() {
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> bookService.rentBook(rentedBook.getId()));

        String expectedMessage = ErrorMessages.BOOK_ALREADY_RENTED.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("대여가 실패한 경우: 분실된 도서")
    void testRentBook_withLostState_throwsIllegalStateException() {
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> bookService.rentBook(lostBook.getId()));

        String expectedMessage = ErrorMessages.BOOK_NOW_LOST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("대여가 실패한 경우: 없는 도서")
    void testRentBook_withWrongId__throwsNoSuchElementException() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> bookService.rentBook(-1));

        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("반납에 성공한 경우: 대여중인 도서 반납")
    void testReturnBook_withRentedState_success() {
        BookService.setOrganizingMilliseconds(1000); // 1초로 수정

        bookService.returnBook(rentedBook.getId());
        Assertions.assertEquals(BookState.ORGANIZING, rentedBook.getState());
        try {
            Thread.sleep(1005);
            Assertions.assertEquals(BookState.AVAILABLE, rentedBook.getState());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("반납에 성공한 경우: 분실되었던 도서 반납")
    void testReturnBook_withLostState_success() {
        BookService.setOrganizingMilliseconds(1000); // 1초로 수정

        bookService.returnBook(lostBook.getId());
        Assertions.assertEquals(BookState.ORGANIZING, lostBook.getState());
        try {
            Thread.sleep(1005);
            Assertions.assertEquals(BookState.AVAILABLE, lostBook.getState());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("반납에 실패한 경우: 대여가능한 도서")
    void testReturnBook_withAvailableState_throwsIllegalStateException() {
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> bookService.returnBook(availableBook.getId()));

        String expectedMessage = ErrorMessages.BOOK_ALREADY_AVAILABLE.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("반납에 실패한 경우: 정리중인 도서")
    void testReturnBook_withOrganizingState_throwsIllegalStateException() {
        bookService.returnBook(rentedBook.getId());
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> bookService.returnBook(rentedBook.getId()));

        String expectedMessage = ErrorMessages.BOOK_BEING_ORGANIZED.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }


    @Test
    @DisplayName("분실처리에 성공한 경우: 대여가능 도서")
    void testLostBook_withAvailableState_success() {
        bookService.lostBook(availableBook.getId());
        Assertions.assertEquals(BookState.LOST, availableBook.getState());
    }

    @Test
    @DisplayName("분실처리에 성공한 경우: 대여중인 도서")
    void testLostBook_withRentedState_success() {
        bookService.lostBook(rentedBook.getId());
        Assertions.assertEquals(BookState.LOST, rentedBook.getState());
    }

    @Test
    @DisplayName("분실처리에 실패한 경우: 대여중인 도서")
    void testLostBook_withRentedState_throwsIllegalStateException() {
        Exception exception = Assertions.assertThrows(IllegalStateException.class, () -> bookService.lostBook(lostBook.getId()));

        String expectedMessage = ErrorMessages.BOOK_ALREADY_LOST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("분실처리에 실패한 경우: 없는 도서")
    void testLostBook_withWrongId_throwsNoSuchElementException() {
        bookService.deleteBook(availableBook.getId());

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> bookService.deleteBook(availableBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    @DisplayName("도서 삭제에 성공한 경우")
    void testDeleteBook_success() {
        bookService.deleteBook(availableBook.getId());
        bookService.deleteBook(rentedBook.getId());
        bookService.deleteBook(lostBook.getId());

        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> bookService.deleteBook(rentedBook.getId()));

        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
        Assertions.assertEquals(0, bookService.getAllBooks().size());
    }

    @Test
    @DisplayName("도서 삭제에 실패한 경우")
    void testDeleteBook_withWrongId_throwsNoSuchElementException() {
        Exception exception = Assertions.assertThrows(NoSuchElementException.class, () -> bookService.deleteBook(-1));

        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @AfterEach
    void tearDown() {
        bookRepository.clearBooks();
    }
}
