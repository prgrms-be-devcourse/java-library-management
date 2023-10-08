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

import static org.assertj.core.api.Assertions.assertThat;

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

        List<Book> books = service.getBooks();
        assertThat(books).hasSize(1);
        assertThat(books.get(0)).extracting(
                Book::getId,
                Book::getName,
                Book::getAuthor,
                Book::getPageCount,
                Book::getStatus,
                Book::getReturnedAt
        ).containsExactly(
                1,
                "제목",
                "작가 이름",
                10,
                BookStatus.BORROWABLE,
                null
        );
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

        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("도서 제목을 기준으로 도서를 검색할 수 있어야합니다")
    public void getBooksByName() {
        String bookName = "도서 검색";
        String keyword = "검색";
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

        List<Book> books = service.getBooksByName(keyword);

        assertThat(books)
                .hasSize(1)
                .first()
                .satisfies(book -> {
                    assertThat(book.getId()).isEqualTo(2);
                    assertThat(book.getName()).isEqualTo(bookName);
                });
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
        assertThat(book)
                .isPresent()
                .hasValueSatisfying(b -> assertThat(b.getStatus()).isEqualTo(BookStatus.BORROWED));
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
        assertThat(book)
                .isPresent()
                .hasValueSatisfying(b -> {
                    assertThat(b.getStatus()).isEqualTo(BookStatus.ORGANIZING);
                    assertThat(b.getReturnedAt()).isNotNull();
                });
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
        assertThat(book)
                .isPresent()
                .hasValueSatisfying(b -> assertThat(b.getStatus()).isEqualTo(BookStatus.LOST));
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
        assertThat(book).isEmpty();
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

        assertThat(books)
                .hasSize(2);
        assertThat(books.get(0))
                .satisfies(b -> {
                    assertThat(b.getId()).isEqualTo(1);
                    assertThat(b.getStatus()).isEqualTo(BookStatus.ORGANIZING);
                });
        assertThat(books.get(1))
                .satisfies(b -> {
                    assertThat(b.getId()).isEqualTo(2);
                    assertThat(b.getStatus()).isEqualTo(BookStatus.BORROWABLE);
                });
    }
}