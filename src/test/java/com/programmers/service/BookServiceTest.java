package com.programmers.service;

import com.programmers.common.ErrorMessages;
import com.programmers.domain.Book;
import com.programmers.domain.BookState;
import com.programmers.provider.BookIdProvider;
import com.programmers.repository.BookRepository;
import com.programmers.repository.MemBookRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookServiceTest {
    private final BookRepository bookRepository;
    private final BookService bookService;

    private Book availableBook;
    private Book rentedBook;
    private Book lostBook;

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

    @AfterEach
    void tearDown() {
        bookRepository.clearBooks();
    }


    @Test
    void 도서등록() {
        Book book = new Book(BookIdProvider.generateBookId(), "새로운도서", "작가", 100, BookState.AVAILABLE);
        bookService.registerBook(book);
        Assertions.assertEquals(book, bookRepository.findBookByTitle("새로운도서").get(0));
        Assertions.assertEquals(4, bookRepository.findBookByTitle("도서").size());
    }

    @Test
    void 전체조회() {
        Assertions.assertEquals(bookService.getAllBooks(), bookRepository.getAllBooks());
        Book book = new Book(BookIdProvider.generateBookId(), "새로운도서", "작가", 100, BookState.AVAILABLE);
        bookService.registerBook(book);
        Assertions.assertEquals(bookService.getAllBooks(), bookRepository.getAllBooks());
    }

    @Test
    void 제목으로도서검색() {
        Assertions.assertEquals(3, bookRepository.findBookByTitle("도서").size());
    }

    @Test
    void 대여기능_성공() {
        bookService.rentBook(availableBook.getId());
        Assertions.assertEquals(BookState.RENTED, availableBook.getState());
    }

    @Test
    void 대여기능_실패_대여중인도서() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.rentBook(rentedBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_ALREADY_RENTED.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void 대여기능_실패_분실된도서() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.rentBook(lostBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_NOW_LOST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void 대여기능_실패_없는도서() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.rentBook(123));
        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void 반납기능_성공_빌렸던도서() {
        bookService.returnBook(rentedBook.getId());
        Assertions.assertEquals(BookState.ORGANIZING, rentedBook.getState());
        try {
            Thread.sleep(10010);
            Assertions.assertEquals(BookState.AVAILABLE, rentedBook.getState());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 반납기능_성공_분실했던도서() {
        bookService.returnBook(lostBook.getId());
        Assertions.assertEquals(BookState.ORGANIZING, lostBook.getState());
        try {
            Thread.sleep(10010);
            Assertions.assertEquals(BookState.AVAILABLE, lostBook.getState());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 반납기능_실패_대여가능한경우() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.returnBook(availableBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_ALREADY_AVAILABLE.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void 반납기능_실패_반납중인경우() {
        bookService.returnBook(rentedBook.getId());
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.returnBook(rentedBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_BEING_ORGANIZED.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void 분실처리_성공() {
        bookService.lostBook(availableBook.getId());
        Assertions.assertEquals(BookState.LOST, availableBook.getState());

        bookService.lostBook(rentedBook.getId());
        Assertions.assertEquals(BookState.LOST, rentedBook.getState());
    }

    @Test
    void 분실처리_실패() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.lostBook(lostBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_ALREADY_LOST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void 도서삭제_성공_대여가능한도서() {
        bookService.deleteBook(availableBook.getId());
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.deleteBook(availableBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void 도서삭제_성공_대여중인도서() {
        bookService.deleteBook(rentedBook.getId());
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.deleteBook(rentedBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();
        Assertions.assertEquals(expectedMessage, actualMessage);
    }

    @Test
    void 도서삭제_성공_분실된도서() {
        bookService.deleteBook(lostBook.getId());
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.deleteBook(lostBook.getId()));
        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }


    @Test
    void 도서삭제_실패_없는도서() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> bookService.deleteBook(123));
        String expectedMessage = ErrorMessages.BOOK_NOT_EXIST.getMessage();
        String actualMessage = exception.getMessage();

        Assertions.assertEquals(expectedMessage, actualMessage);
    }
}
