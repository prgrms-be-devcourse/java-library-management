package com.programmers.librarymanagement;

import com.programmers.librarymanagement.application.LibraryManagementService;
import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.domain.ReturnResult;
import com.programmers.librarymanagement.domain.Status;
import com.programmers.librarymanagement.repository.TestBookRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.programmers.librarymanagement.domain.Status.ALREADY_RENT;

@DisplayName("Service test for TestBookRepository")
public class TestManagementServiceTest {

    static TestBookRepository testBookRepository = new TestBookRepository();
    static LibraryManagementService libraryManagementService = new LibraryManagementService(testBookRepository);

    @AfterEach
    void clearRepository() {
        List<Book> bookList = testBookRepository.findAll();
        while (!bookList.isEmpty()) {
            testBookRepository.deleteBook(bookList.get(0));
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
        Book book = testBookRepository.findByTitle(title).get(0);
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
        List<Book> bookList = testBookRepository.findAll();
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
        List<Book> bookList = testBookRepository.findByTitle("살아남기"); // 인프런에서 살아남기 1, 인포런에서 살아남기 2
        Assertions.assertEquals(2, bookList.size());
    }

    @Test
    void successRentBook() {

        // given
        libraryManagementService.addBook("쉽게 알아보는 디자인 패턴", "푸", 521);
        Long bookNum = testBookRepository.findByTitle("패턴").get(0).getId();

        // when
        libraryManagementService.rentBook(bookNum);

        // then
        Book book = testBookRepository.findByTitle("패턴").get(0);
        Assertions.assertEquals(ALREADY_RENT, book.getStatus());
    }

    @Test
    void failRentBook() {

        // given
        libraryManagementService.addBook("쉽게 알아보는 디자인 패턴", "푸", 521);
        Long bookNum = testBookRepository.findByTitle("패턴").get(0).getId();
        libraryManagementService.rentBook(bookNum);

        // when
        Status result = libraryManagementService.rentBook(bookNum);

        // then
        Assertions.assertEquals(ALREADY_RENT, result);
    }

    @Test
    void successReturnBook() {

        // given
        libraryManagementService.addBook("종이접기 100선", "빙봉", 521);
        Long bookNum = testBookRepository.findByTitle("종이접기").get(0).getId();
        libraryManagementService.rentBook(bookNum);

        // when
        ReturnResult result = libraryManagementService.returnBook(bookNum);

        // then
        Assertions.assertEquals(ReturnResult.SUCCESS_RETURN, result);
    }

    @Test
    void failReturnBook() {

        // given
        libraryManagementService.addBook("종이접기 100선", "빙봉", 521);
        Long bookNum = testBookRepository.findByTitle("종이접기").get(0).getId();

        // when
        ReturnResult result = libraryManagementService.returnBook(bookNum);

        // then
        Assertions.assertEquals(ReturnResult.ALREADY_RETURN, result);
    }

    @Test
    void successLostBook() {

        // given
        libraryManagementService.addBook("자바의 정석", "남궁성", 521);
        Long bookNum = testBookRepository.findByTitle("정석").get(0).getId();

        // when
        Boolean result = libraryManagementService.lostBook(bookNum);

        // then
        Assertions.assertEquals(true, result);
    }

    @Test
    void failLostBook() {

        // given
        libraryManagementService.addBook("자바의 정석", "남궁성", 521);
        Long bookNum = testBookRepository.findByTitle("정석").get(0).getId();
        libraryManagementService.lostBook(bookNum);

        // when
        Boolean result = libraryManagementService.lostBook(bookNum);

        // then
        Assertions.assertEquals(false, result);
    }

    @Test
    void successDeleteBook() {

        // given
        libraryManagementService.addBook("프로그래밍 언어 개념", "원유헌", 521);
        Long bookNum = testBookRepository.findByTitle("프로그래밍").get(0).getId();

        // when
        Boolean result = libraryManagementService.deleteBook(bookNum);

        // then
        Assertions.assertEquals(true, result);
    }

    @Test
    void failDeleteBook() {

        // given
        libraryManagementService.addBook("프로그래밍 언어 개념", "원유헌", 521);
        Long bookNum = testBookRepository.findByTitle("프로그래밍").get(0).getId();

        // when
        Boolean result = libraryManagementService.deleteBook(bookNum + 1);

        // then
        Assertions.assertEquals(false, result);
    }
}
