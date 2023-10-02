package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.programmers.library.domain.BookStatusType.ORGANIZING;
import static com.programmers.library.domain.BookStatusType.RENTABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RepositoryTest {
    Repository repository = new TestRepository();
    Book book;

    @BeforeEach
    public void beforeEach(){
        book = new Book(1L, "제목1", "작가1", 142, RENTABLE);
    }

    @Test
    @DisplayName("책 정보 저장 테스트")
    public void registerTest(){
        // Given

        // When
        repository.register(book);

        // Then
        assertEquals(repository.findBookById(1L).get().getAuthor(), "작가1");
    }


    @Test
    @DisplayName("책 리스트 조회 테스트")
    public void findAllBooksTest(){
        // Given
        Book book2 = new Book(2L, "제목2", "작가2", 200, RENTABLE);
        Book book3 = new Book(3L, "제목3", "작가3", 300, RENTABLE);


        // When
        repository.register(book);
        repository.register(book2);
        repository.register(book3);

        // Then
        assertEquals(repository.findAllBooks().size(), 3);
    }


    @Test
    @DisplayName("책 삭제 테스트")
    public void deleteTest(){
        // Given
        repository.register(book);

        // When
        repository.deleteBook(1L);

        // Then
        assertEquals(repository.findAllBooks().size(), 0);
    }

    @Test
    @DisplayName("제목으로 책 찾기 테스트")
    public void findBooksByTitleTest(){
        // Given
        repository.register(book);

        // When
        List<Book> books = repository.findBooksByTitle("제목1");

        // Then
        assertEquals(books.get(0).getTitle(), "제목1");
    }

    @Test
    @DisplayName("책 번호로 책 찾기 테스트")
    public void findBookByIdTest(){
        // Given
        repository.register(book);

        // When
        Book findBook = repository.findBookById(1L).get();

        // Then
        assertEquals(findBook.getTitle(), "제목1");
    }

    @Test
    @DisplayName("책 상태 업데이트 테스트")
    public void updateStatusTest(){
        // Given
        repository.register(book);

        // When
        Book findBook = repository.findBookById(1L).get();
        repository.updateStatus(findBook, ORGANIZING);

        // Then
        assertEquals(findBook.getBookStatus().getDescription(), "정리중");
    }

    @Test
    @DisplayName("가장 마지막에 저장된 책 번호 조회 테스트")
    public void findLastIdTest(){
        // Given
        Book book2 = new Book(2L, "제목2", "작가2", 200, RENTABLE);
        Book book3 = new Book(3L, "제목3", "작가3", 300, RENTABLE);

        repository.register(book);
        repository.register(book2);
        repository.register(book3);
        // When
        Long lastId = repository.findLastId();

        // Then
        assertEquals(lastId, 3L);
    }
}