package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.repository.LibraryFileRepository;
import com.programmers.library.repository.LibraryRepository;
import com.programmers.library.utils.StatusType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class LibraryServiceTest {

    private LibraryRepository libraryRepository;
    private LibraryService libraryService;

    @BeforeEach
    void setUp() {
        libraryRepository = new LibraryFileRepository();
//        libraryRepository = new LibraryMemoryRepository();
        libraryService = new LibraryService(libraryRepository);
    }

    @AfterEach
    void tearDown() {
        libraryRepository.clearAll();
    }

    @Order(1)
    @Test
    @DisplayName("도서 등록에 성공한다.")
    void addBookTest() {
        // given
        int bookId = 1;
        String title = "오브젝트";
        String author = "조영호";
        int pages = 456;

        // when
        libraryService.addBook(title, author, pages);

        // then
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(findBook.getBookId()).isEqualTo(bookId);
    }

    @Order(2)
    @Test
    @DisplayName("전체 도서 목록 조회에 성공한다.")
    void viewAllBooksTest() {
        // given
        Book book1 = new Book("오브젝트", "조영호", 456);
        int bookId1 = libraryRepository.save(book1);

        Book book2 = new Book("객체지향의 사실과 오해", "조영호", 123);
        int bookId2 = libraryRepository.save(book2);

        // when
        libraryService.viewAllBooks();

        // then
        List<Book> books = libraryRepository.findAll();
        assertThat(books).hasSize(2);
    }

    @Order(3)
    @Test
    @DisplayName("제목으로 도서 검색에 성공한다.")
    void searchBookByTitleTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);
        String searchTitle = "오브";

        // when
        libraryService.searchBookByTitle(searchTitle);

        // then
        List<Book> books = libraryRepository.findByTitle(searchTitle);
        assertThat(books).hasSize(1);
    }

    @Order(4)
    @Test
    @DisplayName("도서 상태가 '대여 가능'인 경우, 도서 대여에 성공한다.")
    void rentBookTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);

        // when
        libraryService.rentBook(bookId);

        // then
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(findBook.getStatus()).isEqualTo(StatusType.RENTING);
    }

    @Order(5)
    @Test
    @DisplayName("도서가 이미 '대여중'인 경우, 도서 대여에 실패한다.")
    void rentBookFailTest1() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);

        Book updatedBook = book.updateStatus(StatusType.RENTING);   // '대여중'으로 상태 변경
        libraryRepository.update(updatedBook);

        // when
        libraryService.rentBook(bookId);

        // then
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(findBook.getStatus()).isEqualTo(updatedBook.getStatus());    // 도서 상태가 변경이 없는지 확인 (= '대여중')
    }

    @Order(6)
    @Test
    @DisplayName("도서 상태가 '정리중/분실됨'인 경우, 도서 대여에 실패한다.")
    void rentBookFailTest2() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);
        Book updatedBook = book.updateStatus(StatusType.ORGANIZING);   // '도서 정리중/분실됨'으로 상태 변경
        libraryRepository.update(updatedBook);


        // when
        libraryService.rentBook(bookId);

        // then
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(findBook.getStatus()).isEqualTo(updatedBook.getStatus());    // 도서 상태가 변경이 없는지 확인 (= '도서 정리중')
    }

    @Order(12)
    @Test
    @DisplayName("도서 상태가 '대여중/분실됨'인 경우, 반납시 '도서 정리중'으로 변경되고 5분뒤 '대여 가능'으로 변경되어 반납에 성공한다.")
    void returnBookTest() throws InterruptedException {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);
        Book updatedBook = book.updateStatus(StatusType.RENTING);   // '대여중/분실됨'으로 상태 변경
        libraryRepository.update(updatedBook);

        // when
        libraryService.returnBook(bookId);

        // then
        Book afterReturn = libraryRepository.findById(bookId).get();
        assertThat(afterReturn.getStatus()).isEqualTo(StatusType.ORGANIZING);  // 반납 직후는 '도서 정리중' 상태

        Thread.sleep(5 * 60 * 1000);    // 5분 대기

        Book afterTimeout = libraryRepository.findById(bookId).get();
        assertThat(afterTimeout.getStatus()).isEqualTo(StatusType.AVAILABLE);  // '대여 가능' 상태로 변경되는지 확인
    }

    @Order(8)
    @Test
    @DisplayName("도서 상태가 '대여 가능/정리중'인 경우, 반납에 실패한다.")
    void returnBookFailTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);    // '대여 가능' 상태
//        Book updatedBook = book.updateStatus(StatusType.ORGANIZING);   // '도서 정리중'으로 상태 변경
//        libraryRepository.update(updatedBook);

        // when
        libraryService.returnBook(bookId);

        // then
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(findBook.getStatus()).isEqualTo(book.getStatus());
    }

    @Order(9)
    @Test
    @DisplayName("도서 상태가 '대여 가능/대여중/정리중'인 경우, 도서 분실 처리에 성공한다.")
    void lostBookTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);    // '대여 가능' 상태
//        Book updatedBook = book.updateStatus(StatusType.ORGANIZING);   // '대여중/도서 정리중'으로 상태 변경
//        libraryRepository.update(updatedBook);

        // when
        libraryService.lostBook(bookId);

        // then
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(findBook.getStatus()).isEqualTo(StatusType.LOST);
    }

    @Order(10)
    @Test
    @DisplayName("도서가 이미 '분실됨'인 경우, 도서 분실 처리에 실패한다.")
    void lostBookFailTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);    // '대여 가능' 상태
        Book updatedBook = book.updateStatus(StatusType.LOST);   // '분실됨'으로 상태 변경
        libraryRepository.update(updatedBook);

        // when
        libraryService.lostBook(bookId);

        // then
        Book findBook = libraryRepository.findById(bookId).get();
        assertThat(findBook.getStatus()).isEqualTo(updatedBook.getStatus());
    }

    @Order(11)
    @Test
    @DisplayName("도서 삭제에 성공한다.")
    void deleteBookTest() {
        // given
        Book book = new Book("오브젝트", "조영호", 456);
        int bookId = libraryRepository.save(book);

        // when
        libraryService.deleteBook(bookId);

        // then
        assertThat(libraryRepository.findAll()).hasSize(0);
    }
}