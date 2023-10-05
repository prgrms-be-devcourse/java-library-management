package com.programmers.librarymanagement;

import com.programmers.librarymanagement.application.LibraryManagementService;
import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.exception.BookAlreadyLostException;
import com.programmers.librarymanagement.exception.BookAlreadyRentException;
import com.programmers.librarymanagement.exception.BookAlreadyReturnException;
import com.programmers.librarymanagement.exception.BookNotFoundException;
import com.programmers.librarymanagement.repository.NormalBookRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.programmers.librarymanagement.domain.Status.*;

@DisplayName("Service test for NormalBookRepository")
public class NormalManagementServiceTest {

    static NormalBookRepository normalBookRepository = new NormalBookRepository();
    static LibraryManagementService libraryManagementService = new LibraryManagementService(normalBookRepository);

    @AfterEach
    void clearRepository() {
        List<Book> bookList = normalBookRepository.findAll();
        for (Book book : bookList) {
            normalBookRepository.deleteBook(book);
        }
    }

    @Test
    void successAddBook() {

        // given
        String title = "데브코스에서 살아남기";
        String author = "스펜서";
        int page = 365;

        // when
        libraryManagementService.addBook(title, author, page);

        // then
        Book book = normalBookRepository.findByTitle(title).get(0);
        Assertions.assertEquals(title, book.getTitle());
        Assertions.assertEquals(author, book.getAuthor());
        Assertions.assertEquals(page, book.getPage());
    }

    @Test
    void successGetAllBooks() {

        // given
        String title1 = "신촌 맛집 탐방기";
        String author1 = "쿠쿠";
        int page1 = 100;

        String title2 = "오늘 뭐 먹지";
        String author2 = "뫄뫄";
        int page2 = 200;

        // when
        libraryManagementService.addBook(title1, author1, page1);
        libraryManagementService.addBook(title2, author2, page2);

        // then
        List<Book> bookList = normalBookRepository.findAll();
        Assertions.assertEquals("쿠쿠", bookList.get(0).getAuthor());
        Assertions.assertEquals("뫄뫄", bookList.get(1).getAuthor());
    }

    @Test
    void successFindByTitle() {

        // given
        String title1 = "인프런에서 살아남기 1";
        String title2 = "인프런에서 살아남기 2";
        String author = "김영한";
        int page = 389;

        // when
        libraryManagementService.addBook(title1, author, page);
        libraryManagementService.addBook(title2, author, page);

        // then
        List<Book> bookList = normalBookRepository.findByTitle("살아남기"); // 인프런에서 살아남기 1, 인포런에서 살아남기 2
        Assertions.assertEquals(2, bookList.size());
    }

    @Test
    void successRentBook() {

        // given
        libraryManagementService.addBook("쉽게 알아보는 디자인 패턴", "푸", 521);
        Long bookNum = normalBookRepository.findByTitle("패턴").get(0).getId();

        // when
        libraryManagementService.rentBook(bookNum);

        // then
        Book book = normalBookRepository.findByTitle("패턴").get(0);
        Assertions.assertEquals(ALREADY_RENT, book.getStatus());
    }

    @Test
    void failRentBook() {

        // given
        libraryManagementService.addBook("쉽게 알아보는 디자인 패턴", "푸", 521);
        Long bookNum = normalBookRepository.findByTitle("패턴").get(0).getId();
        libraryManagementService.rentBook(bookNum);

        // when - then
        Assertions.assertThrows(BookAlreadyRentException.class, () -> libraryManagementService.rentBook(bookNum));
    }

    @Test
    void successReturnBook() {

        // given
        libraryManagementService.addBook("종이접기 100선", "빙봉", 521);
        Long bookNum = normalBookRepository.findByTitle("종이접기").get(0).getId();
        libraryManagementService.rentBook(bookNum);

        // when
        libraryManagementService.returnBook(bookNum);

        // then
        Assertions.assertEquals(normalBookRepository.findById(bookNum).get().getStatus(), ARRANGE);
    }

    @Test
    void failReturnBook() {

        // given
        libraryManagementService.addBook("종이접기 100선", "빙봉", 521);
        Long bookNum = normalBookRepository.findByTitle("종이접기").get(0).getId();

        // when - then
        Assertions.assertThrows(BookAlreadyReturnException.class, () -> libraryManagementService.returnBook(bookNum));
    }

    @Test
    void successLostBook() {

        // given
        libraryManagementService.addBook("자바의 정석", "남궁성", 521);
        Long bookNum = normalBookRepository.findByTitle("정석").get(0).getId();

        // when
        libraryManagementService.lostBook(bookNum);

        // then
        Assertions.assertEquals(normalBookRepository.findById(bookNum).get().getStatus(), LOST);
    }

    @Test
    void failLostBook() {

        // given
        libraryManagementService.addBook("자바의 정석", "남궁성", 521);
        Long bookNum = normalBookRepository.findByTitle("정석").get(0).getId();
        libraryManagementService.lostBook(bookNum);

        // when - then
        Assertions.assertThrows(BookAlreadyLostException.class, () -> libraryManagementService.lostBook(bookNum));
    }

    @Test
    void successDeleteBook() {

        // given
        libraryManagementService.addBook("프로그래밍 언어 개념", "원유헌", 521);
        Long bookNum = normalBookRepository.findByTitle("프로그래밍").get(0).getId();

        // when
        libraryManagementService.deleteBook(bookNum);

        // then
        Assertions.assertFalse(normalBookRepository.findById(bookNum).isPresent());
    }

    @Test
    void failDeleteBook() {

        // given
        libraryManagementService.addBook("프로그래밍 언어 개념", "원유헌", 521);
        Long bookNum = normalBookRepository.findByTitle("프로그래밍").get(0).getId();

        // when - then
        Assertions.assertThrows(BookNotFoundException.class, () -> libraryManagementService.deleteBook(bookNum + 1));
    }
}
