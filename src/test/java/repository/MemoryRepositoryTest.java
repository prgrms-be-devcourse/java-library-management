package repository;

import model.Book;
import model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MemoryRepositoryTest {

    private MemoryRepository memoryRepository;

    @BeforeEach
    void setUp() {
        memoryRepository = new MemoryRepository();
    }

    @Test
    @DisplayName("도서 저장 테스트")
    void testSaveBook() {
        Book book = new Book(1L, "도서1", "작가1", 255, Status.AVAILABLE);
        memoryRepository.saveBook(book);

        List<Book> books = memoryRepository.findAllBook();

        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    @DisplayName("상태 변경 된 도서 저장 테스트")
    void testSaveBookChangedStatus() {
        Book book1 = new Book(1L, "도서1", "작가1", 255, Status.AVAILABLE);
        memoryRepository.saveBook(book1);

        Book book2 = new Book(1L, "도서1", "작가1", 255, Status.AVAILABLE);
        memoryRepository.saveBook(book2);

        List<Book> books = memoryRepository.findAllBook();
        assertEquals(1, books.size());
    }

    @Test
    @DisplayName("제목으로 도서 검색 테스트")
    void testFindBookByTitle() {
        Book book1 = new Book(1L, "해리포터", "J.K 롤링", 255, Status.AVAILABLE);
        Book book2 = new Book(2L, "토끼책", "조영호", 255, Status.AVAILABLE);

        memoryRepository.saveBook(book1);
        memoryRepository.saveBook(book2);

        String searchTitle = "해리";
        List<Book> searchedBooks = memoryRepository.findBookByTitle(searchTitle);
        assertEquals(1, searchedBooks.size());
        assertEquals(book1, searchedBooks.get(0));
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        Book book = new Book(1L, "Test Book", "Test Author", 255, Status.AVAILABLE);
        memoryRepository.saveBook(book);

        memoryRepository.deleteBook(book.getBookNo());
        List<Book> books = memoryRepository.findAllBook();
        assertTrue(books.isEmpty());
    }

    @Test
    @DisplayName("도서 번호로 도서 조회 테스트")
    void testFindBookByBookNo() {
        Book book = new Book(1L, "Test Book", "Test Author", 255, Status.AVAILABLE);
        memoryRepository.saveBook(book);

        Book bookFound = memoryRepository.findBookByBookNo(1L)
                .orElseThrow();

        assertEquals(book, bookFound);
    }

    @Test
    @DisplayName("bookNo 생성 메소드 테스트")
    void testCreateBookNo() {
        Book book = new Book(1L, "Test Book", "Test Author", 255, Status.AVAILABLE);
        memoryRepository.saveBook(book);

        Long createdBookNo = memoryRepository.createBookNo();
        assertEquals(2, createdBookNo);
    }
}
