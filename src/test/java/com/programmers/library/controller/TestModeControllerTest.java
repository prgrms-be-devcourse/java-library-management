package com.programmers.library.controller;

import com.programmers.library.domain.Book;
import com.programmers.library.repository.LibraryMemoryRepository;
import com.programmers.library.repository.LibraryRepository;
import com.programmers.library.service.LibraryService;
import com.programmers.library.utils.StatusType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TestModeControllerTest {
    private LibraryService libraryService;
    private LibraryRepository libraryRepository;

    @BeforeEach
    public void setUp() {
        libraryRepository = new LibraryMemoryRepository();
        libraryService = new LibraryService(libraryRepository);
    }

    @AfterEach
    public void tearDown() {
        libraryRepository.clearAll();
    }

    @Test
    @DisplayName("1. 도서 등록에 성공한다.")
    public void addBookSuccess() {
        // given: 검증 데이터
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록

        // when: 검증하려는 것
        int bookId = libraryRepository.save(book);

        // then: 검증하는 부분
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(book.getTitle()).isEqualTo(findBook.getTitle());  // 등록한 도서의 제목인지 확인
    }

    @Test
    @DisplayName("2. 전체 도서 목록 조회에 성공한다.")
    public void viewAllBooksSuccess() {
        // given
        Book book1 = new Book("Book TEST", "Author1", 100);  // 도서 등록
        libraryRepository.save(book1);
        Book book2 = new Book("Book TEST2", "Author2", 200);  // 도서 등록
        libraryRepository.save(book2);

        // when
        List<Book> books = libraryRepository.findAll();

        // then
        assertThat(books.size()).isEqualTo(2);  // 전체 도서의 크기가 2인지 확인
    }

    @Test
    @DisplayName("3. 제목으로 도서 검색에 성공한다")
    public void searchBookSuccess() {
        // given
        Book book1 = new Book("Book TEST", "Author1", 100);  // 도서 등록
        libraryRepository.save(book1);
        Book book2 = new Book("Book TEST2", "Author2", 200);  // 도서 등록
        libraryRepository.save(book2);
        Book book3 = new Book("Book 3", "Author3", 300);
        libraryRepository.save(book3);

        // when
        List<Book> books = libraryRepository.findByTitle("TEST");

        // then
        assertThat(books.size()).isEqualTo(2);  // 'TEST'로 검색된 도서의 크기가 2인지 확인
    }

    @Test
    @DisplayName("4. 도서 대여를 성공한다.")
    public void rentBookSuccess() {
        // given
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록
        int bookId = libraryRepository.save(book);

        // when
        libraryService.rentBook(bookId);    // 도서 대여

        // then
        assertThat(book.getStatus()).isEqualTo(StatusType.RENTING);  // 도서 상태가 '대여중' 확인

    }

    @Test
    @DisplayName("4. 도서 대여에 실패한다.")
    public void rentBookFail() {
        // given
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록
        int bookId = libraryRepository.save(book);
        libraryRepository.updateStatus(bookId, StatusType.RENTING); // 도서 상태를 '대여중' 으로 변경
        StatusType status = book.getStatus();   // 대여 전 도서 상태 (RENTING)

        // when
        libraryService.rentBook(bookId);

        // then
        assertThat(status).isEqualTo(book.getStatus());  // 도서 상태가 변경이 없는지 확인 (= '대여중')
    }

    @Test
    @DisplayName("5. 도서 반납에 성공한다.")
    public void returnBookSuccess() {
        // given
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록
        int bookId = libraryRepository.save(book);
        libraryRepository.updateStatus(bookId, StatusType.RENTING); // 도서 상태를 '대여중' 으로 변경

        // when
        libraryService.returnBook(bookId);

        // then
        assertThat(book.getStatus()).isEqualTo(StatusType.ORGANIZING);  // 도서 상태가 '도서 정리중' 인지 확인
    }

    @Test
    @DisplayName("5. 도서 반납에 실패한다.")
    public void returnBookFail() {
        // given
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록
        int bookId = libraryRepository.save(book);
        libraryRepository.updateStatus(bookId, StatusType.ORGANIZING); // 도서 상태를 '도서 정리중' 으로 변경
        StatusType status = book.getStatus();   // 반납 전 도서 상태 (ORGANIZING)

        // when
        libraryService.returnBook(bookId);

        // then
        assertThat(status).isEqualTo(book.getStatus());  // 도서 상태가 변경이 없는지 확인 (= '도서 정리중')
    }

    @Test
    @DisplayName("6. 도서 분실 처리에 성공한다.")
    public void lostBookSuccess() {
        // given
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록
        int bookId = libraryRepository.save(book);

        // when
        libraryService.lostBook(bookId);

        // then
        assertThat(book.getStatus()).isEqualTo(StatusType.LOST);  // 도서 상태가 '분실됨' 인지 확인
    }

    @Test
    @DisplayName("6. 도서 분실 처리에 실패한다.")
    public void lostBookFail() {
        // given
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록
        int bookId = libraryRepository.save(book);
        libraryRepository.updateStatus(bookId, StatusType.LOST); // 도서 상태를 '분실됨' 으로 변경
        StatusType status = book.getStatus();   // 분실 처리 전 도서 상태

        // when
        libraryService.lostBook(bookId);

        // then
        assertThat(status).isEqualTo(book.getStatus());  // 도서 상태가 변경이 없는지 확인 (= '분실됨')
    }

    @Test
    @DisplayName("7. 도서 삭제 처리에 성공한다.")
    public void deleteBookSuccess() {
        // given
        Book book = new Book("Book TEST", "Author1", 100);  // 도서 등록
        int bookId = libraryRepository.save(book);

        // when
        libraryService.deleteBook(bookId);

        // then
        List<Book> books = libraryRepository.findAll();
        assertThat(books).isEmpty();  // 전체 도서 목록을 조회했을 때 크기가 0인지 확인
    }

    @Test
    @DisplayName("7. 도서 삭제 처리에 실패한다.")
    public void deleteBookFail() {
        // given
        List<Book> books = libraryRepository.findAll();

        // when
        libraryService.deleteBook(1);   // 존재하지도 않는 책 삭제

        // then
        assertThat(books).isEmpty();    // 전체 도서 목록을 조회했을 때 크기가 0인지 확인
    }
}
