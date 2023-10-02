package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatusType;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.mock.MockRepository;
import com.programmers.library.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LibraryServiceTest {

    LibraryService libraryService;
    Repository repository;


    @BeforeEach
    public void setUp(){
        repository = new MockRepository();
        libraryService = new LibraryService(repository);
    }

    @Test
    @DisplayName("도서 등록 메서드 호출 테스트")
    public void callRegisterBookTest(){
        // Given
        String title = "제목 1";
        String author ="저자 1";
        Integer page = 100;

        // When
        libraryService.registerBook(title,author,page);

        List<Book> findBook = libraryService.findBooksByTitle(title);
        // Then
        assertEquals(1L, findBook.get(0).getBookId());
    }

    @Test
    @DisplayName("전체 도서 조회 메서드 호출 테스트")
    public void callFindAllBooksTest(){
        // Given
        List<Book> books = List.of(
                new Book(1L, "제목 1", "저자 1", 100),
                new Book(2L, "제목 2", "저자 2", 150)
        );

        repository.register(books.get(0));
        repository.register(books.get(1));

        // When
        List<Book> findBooks = libraryService.findAllBooks();

        // Then
        assertEquals(2, findBooks.size());
        assertEquals("제목 1", findBooks.get(0).getTitle());
        assertEquals("제목 2", findBooks.get(1).getTitle());
    }

    @Test
    @DisplayName("책 번호로 책 찾기 호출 테스트")
    public void findBookByIdTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author ="저자 1";
        Integer page = 100;

        libraryService.registerBook(title,author,page);

        // When
        Book findBook = libraryService.findBookById(bookId);

        // Then
        assertEquals(findBook.getTitle(), "제목 1");
    }

    @Test
    @DisplayName("도서 상태 변경 메서드 호출 테스트")
    public void updateStatusTest(){
        // Given
        Long bookId = 1L;
        String title = "제목 1";
        String author = "저자 1";
        Integer page = 100;

        Book book = new Book(bookId, title, author, page);
        libraryService.registerBook(title,author,page);

        // When
        libraryService.updateStatus(book, BookStatusType.LOST);

        // Then
        assertEquals(book.getBookStatus().getDescription(), "분실");
    }

    @Test
    @DisplayName("존재하지 않는 도서번호에 대한 예외처리 테스트")
    public void bookNotFoundExceptionTest(){
        // Given
        Long bookId = 100L;

        // When
        ExceptionHandler result = assertThrows(ExceptionHandler.class, () -> {
            libraryService.findBookById(bookId);
        });

        // Then
        String bookNotFound = "\n[System] 입력하신 도서를 찾을 수 없습니다.";
        assertEquals(bookNotFound,  result.getMessage());
    }
}