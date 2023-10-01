package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatus;
import com.programmers.library.dto.CreateBookRequestDto;
import com.programmers.library.repository.Repository;
import com.programmers.library.repository.TestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {
    private final Service service;
    private final Repository repository;

    public ServiceTest() {
        this.repository = new TestRepository();
        this.service = new Service(repository);
    }

    @AfterEach
    public void tearDown() throws Exception {
        repository.deleteAll();
    }

    @Test
    @DisplayName("도서를 등록할 수 있어야합니다")
    public void createBook() {
        CreateBookRequestDto request = CreateBookRequestDto.fixture();

        service.createBook(request);

        List<Book> books = repository.findAll();
        assertEquals(1, books.size());
        assertEquals(1, books.get(0).getId());
        assertEquals("제목", books.get(0).getName());
        assertEquals("작가 이름", books.get(0).getAuthor());
        assertEquals(10, books.get(0).getPageCount());
        assertEquals(BookStatus.BORROWABLE, books.get(0).getStatus());
        assertEquals(null, books.get(0).getReturnedAt());
    }

    @Test
    @DisplayName("도서 전체 목록을 볼 수 있어야합니다")
    public void getBooks() {
        CreateBookRequestDto request1 = CreateBookRequestDto.fixture();
        CreateBookRequestDto request2 = CreateBookRequestDto.fixture();
        repository.save(new Book(
                repository.generateId(),
                request1.getName(),
                request1.getAuthor(),
                request1.getPageCount(),
                BookStatus.BORROWABLE)
        );
        repository.save(new Book(
                repository.generateId(),
                request2.getName(),
                request2.getAuthor(),
                request2.getPageCount(),
                BookStatus.BORROWABLE)
        );

        List<Book> books = service.getBooks();

        assertEquals(2, books.size());
    }

    @Test
    @DisplayName("도서 제목을 기준으로 도서를 검색할 수 있어야합니다")
    public void getBooksByName() {
        CreateBookRequestDto request1 = CreateBookRequestDto.fixture();
        CreateBookRequestDto request2 = CreateBookRequestDto.fixture();
        repository.save(new Book(
                1,
                request1.getName(),
                request1.getAuthor(),
                request1.getPageCount(),
                BookStatus.BORROWABLE)
        );
        repository.save(new Book(
                2,
                "도서 검색",
                request2.getAuthor(),
                request2.getPageCount(),
                BookStatus.BORROWABLE)
        );

        List<Book> books = service.getBooksByName("도서 검색");

        assertEquals(1, books.size());
        assertEquals(2, books.get(0).getId());
        assertEquals("도서 검색", books.get(0).getName());
    }

    @Test
    @DisplayName("도서를 대여할 수 있어야합니다")
    public void borrowBook() {
        CreateBookRequestDto request = CreateBookRequestDto.fixture();
        repository.save(new Book(
                1,
                request.getName(),
                request.getAuthor(),
                request.getPageCount(),
                BookStatus.BORROWABLE)
        );

        service.borrowBook(1);

        Optional<Book> book = repository.findOneById(1);
        assertEquals(BookStatus.BORROWED, book.get().getStatus());
    }

    @Test
    @DisplayName("도서를 반납할 수 있어야합니다")
    public void returnBook() {
        CreateBookRequestDto request = CreateBookRequestDto.fixture();
        repository.save(new Book(
                1,
                request.getName(),
                request.getAuthor(),
                request.getPageCount(),
                BookStatus.BORROWED)
        );

        service.returnBook(1);

        Optional<Book> book = repository.findOneById(1);
        assertEquals(BookStatus.ORGANIZING, book.get().getStatus());
        assertNotNull(book.get().getReturnedAt());
    }

    @Test
    @DisplayName("도서를 분실 처리할 수 있어야합니다")
    public void reportLostBook() {
        CreateBookRequestDto request = CreateBookRequestDto.fixture();
        repository.save(new Book(
                1,
                request.getName(),
                request.getAuthor(),
                request.getPageCount(),
                BookStatus.BORROWABLE)
        );

        service.reportLostBook(1);

        Optional<Book> book = repository.findOneById(1);
        assertEquals(BookStatus.LOST, book.get().getStatus());
    }

    @Test
    @DisplayName("도서를 삭제할 수 있어야합니다")
    public void deleteBook() {
        CreateBookRequestDto request = CreateBookRequestDto.fixture();
        repository.save(new Book(
                1,
                request.getName(),
                request.getAuthor(),
                request.getPageCount(),
                BookStatus.BORROWABLE)
        );

        service.deleteBook(1);

        Optional<Book> book = repository.findOneById(1);
        assertTrue(book.isEmpty());
    }

    @Test
    @DisplayName("도서 전체 목록 조회 시 반납된 지 5분이 지난 도서는 대여 가능해야 합니다")
    public void testGetBooks() {
        CreateBookRequestDto request1 = CreateBookRequestDto.fixture();
        CreateBookRequestDto request2 = CreateBookRequestDto.fixture();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenMinutesAgo = now.minus(10, ChronoUnit.MINUTES);
        repository.save(new Book(
                1,
                request1.getName(),
                request1.getAuthor(),
                request1.getPageCount(),
                BookStatus.ORGANIZING,
                LocalDateTime.now())
        );
        repository.save(new Book(
                2,
                "도서 검색",
                request2.getAuthor(),
                request2.getPageCount(),
                BookStatus.ORGANIZING,
                tenMinutesAgo)
        );

        List<Book> books = service.getBooks();

        assertEquals(2, books.size());
        assertEquals(1, books.get(0).getId());
        assertEquals(BookStatus.ORGANIZING, books.get(0).getStatus());
        assertEquals(2, books.get(1).getId());
        assertEquals(BookStatus.BORROWABLE, books.get(1).getStatus());
    }
}