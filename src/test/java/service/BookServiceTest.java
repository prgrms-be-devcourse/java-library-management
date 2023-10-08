package service;

import domain.Book;
import domain.BookStatusType;
import dto.CreateBookRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.Repository;
import repository.TestRepository;

import java.util.List;

class BookServiceTest {
    private final Repository repository = new TestRepository();
    private BookService bookService = new BookService(repository);

    @BeforeEach
    void beforeEach() {
        repository.clear();
    }

    @DisplayName("도서 등록 테스트")
    @Test
    void addTest(){
        // given
        CreateBookRequestDTO bookDTO = CreateBookRequestDTO.builder().title("test1").author("test").pageNum(123).build();

        // when
        bookService.addBook(bookDTO);

        // then
        Assertions.assertEquals(repository.bookList().size(), 1);
    }

    @DisplayName("도서 대여 테스트")
    @Test
    void borrowTest(){
        // given
        CreateBookRequestDTO bookDTO = CreateBookRequestDTO.builder().title("test1").author("test").pageNum(123).build();
        bookService.addBook(bookDTO);
        List<Book> testList = repository.findByTitle("test1");
        Book test = testList.get(0);

        // when
        bookService.borrowBook(test.getId());

        // then
        Assertions.assertEquals(test.getBookStatusType(), BookStatusType.BORROWED);
    }

    @DisplayName("도서 대여 실패 테스트")
    @Test
    void borrowFailTest(){
        // given
        CreateBookRequestDTO bookDTO = CreateBookRequestDTO.builder().title("test1").author("test").pageNum(123).build();
        bookService.addBook(bookDTO);
        List<Book> testList = repository.findByTitle("test1");
        Book test = testList.get(0);
        bookService.borrowBook(test.getId());

        // expected
        Assertions.assertThrows(RuntimeException.class, () -> bookService.borrowBook(test.getId()));
    }

    @DisplayName("도서 삭제 테스트")
    @Test
    void deleteTest(){
        // given
        CreateBookRequestDTO bookDTO = CreateBookRequestDTO.builder().title("test1").author("test").pageNum(123).build();
        bookService.addBook(bookDTO);
        List<Book> testList = repository.findByTitle("test1");
        Book test = testList.get(0);

        // when
        bookService.deleteBook(test.getId());

        // then
        Assertions.assertEquals(repository.bookList().size(), 0);
    }

    @DisplayName("도서 분실 테스트")
    @Test
    void lostTest(){
        // given
        CreateBookRequestDTO bookDTO = CreateBookRequestDTO.builder().title("test1").author("test").pageNum(123).build();
        bookService.addBook(bookDTO);
        List<Book> testList = repository.findByTitle("test1");
        Book test = testList.get(0);

        // when
        bookService.lostBook(test.getId());

        // then
        Assertions.assertEquals(test.getBookStatusType(), BookStatusType.LOST);
    }

    @DisplayName("도서 반납 테스트")
    @Test
    void returnTest(){
        // given
        CreateBookRequestDTO bookDTO = CreateBookRequestDTO.builder().title("test1").author("test").pageNum(123).build();
        bookService.addBook(bookDTO);
        List<Book> testList = repository.findByTitle("test1");
        Book test = testList.get(0);
        bookService.borrowBook(test.getId());

        // when
        bookService.returnBook(test.getId());

        // then
        Assertions.assertEquals(test.getBookStatusType(), BookStatusType.ORGANIZING);
    }

    @DisplayName("도서 실패 테스트")
    @Test
    void returnFailTest(){
        // given
        CreateBookRequestDTO bookDTO = CreateBookRequestDTO.builder().title("test1").author("test").pageNum(123).build();
        bookService.addBook(bookDTO);
        List<Book> testList = repository.findByTitle("test1");
        Book test = testList.get(0);

        // expected
        Assertions.assertThrows(RuntimeException.class, () -> bookService.returnBook(test.getId()));
    }
}