import devcourse.backend.medel.Book;
import devcourse.backend.medel.BookStatus;
import devcourse.backend.repository.MemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MemoryRepositoryTest {
    private MemoryRepository memoryRepository;

    @BeforeEach
    void 파일_리포지토리_초기화() throws IOException {
        // 각 테스트 전에 MemoryRepository 초기화
        memoryRepository = new MemoryRepository();

        // MemoryRepository에 미리 데이터 삽입
        List<Book> books = new ArrayList<>();
        memoryRepository.setBooks(books);
        books.add(getBooks().get(0).copy());
        books.add(getBooks().get(1).copy());
    }

    @Test
    void 전체_도서_목록_조회() {
        List<Book> result = memoryRepository.findAll();

        assertEquals(2, result.size());
        assertEquals(result.get(0), getBooks().get(0));
        assertEquals(result.get(1), getBooks().get(1));
    }

    @Test
    void 도서_제목으로_검색() {
        List<Book> result = memoryRepository.findByKeyword("자바");

        assertEquals(1, result.size());
        assertTrue(result.contains(getBooks().get(0))); // [에펙티브 자바]
    }

    @Test
    void 도서_번호로_도서_검색() {
        Book someBook = memoryRepository.getBooks().stream().findAny().orElseThrow();
        Book result = memoryRepository.findById(someBook.getId());
        assertEquals(someBook, result);
    }

    @Test
    void 도서_번호로_도서_삭제() {
        // deleteById() 메서드를 호출합니다.
        Book someBook = memoryRepository.getBooks().stream().findAny().orElseThrow();
        memoryRepository.deleteById(someBook.getId());
        assertFalse(memoryRepository.getBooks().contains(someBook));
    }

    @Test
    void 도서_추가() {
        // Mock 데이터
        Book newBook = new Book.Builder("친절한 SQL 튜닝", "조시형", 560).build();

        // addBook() 메서드 호출
        memoryRepository.addBook(newBook);

        // addBook()이 책을 추가했는지 검증
        assertTrue(memoryRepository.getBooks().contains(newBook));
    }

    @Test
    void 도서_상태_변경() {
        // Mock 데이터
        Book newBook = new Book.Builder("친절한 SQL 튜닝", "조시형", 560).build();
        memoryRepository.getBooks().add(newBook);

        // 도서 상태 변경
        memoryRepository.changeStatus(newBook.getId(), BookStatus.BORROWED);

        // changeStatus()가 상태를 변경했는지 검증
        assertEquals(BookStatus.BORROWED, newBook.getStatus());
    }

    private static List<Book> getBooks() {
        // Mock 데이터
        List<Book> books = new ArrayList<>();

        Book book1 = new Book.Builder("이펙티브 자바", "조슈아 블로크", 520).id(1).bookStatus(BookStatus.AVAILABLE.toString()).build();
        Book book2 = new Book.Builder("객체지향의 사실과 오해", "조영호", 260).id(2).bookStatus(BookStatus.AVAILABLE.toString()).build();
        books.add(book1);
        books.add(book2);

        return books;
    }

}