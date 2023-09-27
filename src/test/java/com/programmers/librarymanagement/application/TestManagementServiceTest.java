package com.programmers.librarymanagement.application;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.repository.TestBookRepository;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.programmers.librarymanagement.domain.Status.CANNOT_RENT;

@DisplayName("Service test for TestBookRepository")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestManagementServiceTest {

    static LibraryManagementService libraryManagementService;
    static TestBookRepository testBookRepository;

    @BeforeAll
    static void injectRepository() {
        testBookRepository = new TestBookRepository();
        libraryManagementService = new LibraryManagementService(testBookRepository);
    }

    @Order(1)
    @Test
    void successAddBookAndFindByTitle() {

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

    @Order(2)
    @Test
    void successGetAllBooks() {

        // given
        String title = "신촌 맛집 탐방기";
        String author = "쿠쿠";
        int page = 100;

        // when
        libraryManagementService.addBook(title, author, page);

        // then
        List<Book> bookList = testBookRepository.findAll();
        Assertions.assertEquals("스펜서", bookList.get(0).getAuthor());
        Assertions.assertEquals("쿠쿠", bookList.get(1).getAuthor());
    }

    @Order(3)
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
        List<Book> bookList = testBookRepository.findByTitle("살아남기"); // 데브코스에서 살아남기, 인프런에서 살아남기 1, 인포런에서 살아남기 2
        Assertions.assertEquals(3, bookList.size());
    }

    @Order(4)
    @Test
    void successRentBook() {

        // given
        libraryManagementService.addBook("쉽게 알아보는 디자인 패턴", "푸", 521);
        Long bookNum = testBookRepository.findByTitle("패턴").get(0).getId();

        // when
        libraryManagementService.rentBook(bookNum);

        // then
        Book book = testBookRepository.findByTitle("패턴").get(0);
        Assertions.assertEquals(CANNOT_RENT, book.getStatus());
    }

    @Order(5)
    @Test
    void failRentBook() {

        // given
        Long bookNum = testBookRepository.findByTitle("패턴").get(0).getId();
        libraryManagementService.rentBook(bookNum);

        // when
        String result = libraryManagementService.rentBook(bookNum);

        // then
        Assertions.assertEquals("[System] 이미 대여중인 도서입니다. \n", result);
    }

    @Order(6)
    @Test
    void successReturnBook() {

        // given
        libraryManagementService.addBook("종이접기 100선", "빙봉", 521);
        Long bookNum = testBookRepository.findByTitle("종이접기").get(0).getId();
        libraryManagementService.rentBook(bookNum);

        // when
        String result = libraryManagementService.returnBook(bookNum);

        // then
        Assertions.assertEquals("[System] 도서가 반납 처리 되었습니다. \n", result);
    }

    @Order(7)
    @Test
    void failReturnBook() {

        // given
        Long bookNum = testBookRepository.findByTitle("종이접기").get(0).getId();

        // when
        String result = libraryManagementService.returnBook(bookNum);

        // then
        Assertions.assertEquals("[System] 정리 중인 도서입니다. \n", result);
    }

    @Order(8)
    @Test
    void successLostBook() {

        // given
        libraryManagementService.addBook("자바의 정석", "남궁성", 521);
        Long bookNum = testBookRepository.findByTitle("정석").get(0).getId();

        // when
        String result = libraryManagementService.lostBook(bookNum);

        // then
        Assertions.assertEquals("[System] 도서가 분실 처리 되었습니다. \n", result);
    }

    @Order(9)
    @Test
    void failLostBook() {

        // given
        Long bookNum = testBookRepository.findByTitle("정석").get(0).getId();

        // when
        String result = libraryManagementService.lostBook(bookNum);

        // then
        Assertions.assertEquals("[System] 이미 분실 처리된 도서입니다. \n", result);
    }

    @Order(10)
    @Test
    void successDeleteBook() {

        // given
        libraryManagementService.addBook("프로그래밍 언어 개념", "원유헌", 521);
        Long bookNum = testBookRepository.findByTitle("프로그래밍").get(0).getId();

        // when
        String result = libraryManagementService.deleteBook(bookNum);

        // then
        Assertions.assertEquals("[System] 도서가 삭제 처리 되었습니다. \n", result);
    }

    @Order(11)
    @Test
    void failDeleteBook() {

        // given
        libraryManagementService.addBook("프로그래밍 언어 개념", "원유헌", 521);
        Long bookNum = testBookRepository.findByTitle("프로그래밍").get(0).getId();

        // when
        String result = libraryManagementService.deleteBook(bookNum + 1);

        // then
        Assertions.assertEquals("[System] 존재하지 않는 도서번호 입니다. \n", result);
    }
}
