package repository;

import domain.Book;
import domain.BookState;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryRepositoryTest {

    Repository repository = new MemoryRepository();

    @BeforeEach
    void beforeEach() {
        List<Book> books = repository.getList();
        books.add(new Book(1, "rolling girl", "miku", 543, BookState.AVAILABLE));
        books.add(new Book(2, "yellow", "coldplay", 45, BookState.RENTING));
        books.add(new Book(3, "1998", "라쿠나", 4543, BookState.LOST));
    }

    @AfterEach
    void AfterEach() {
        List<Book> books = repository.getList();
        books.clear();
    }
    @Test
    @DisplayName("도서 등록")
    void register() {
        Book book = new Book("welcome to the black parade", "MCR", 324);
        repository.register(book);

        List<Book> books = repository.getList();
        assertThat(book).isEqualTo(books.get(books.size() - 1));
        assertThat(4).isEqualTo(books.size());
    }

    @Test
    @DisplayName("도서 목록")
    void getList() {
        List<Book> books = repository.getList();
        assertThat(3).isEqualTo(books.size());
    }

    @Test
    @DisplayName("도서 검색")
    void search() {
        List<Book> selectedBooks = repository.search("ll");

        assertThat(2).isEqualTo(selectedBooks.size());
        assertThat("rolling girl").isEqualTo(selectedBooks.get(0).getTitle());
        assertThat("yellow").isEqualTo(selectedBooks.get(1).getTitle());
    }

    @Test
    @DisplayName("도서 대여: 대여 성공")
    void rentalSuccess() {
        List<Book> books = repository.getList();
        repository.rental(1);

        assertThat(books.get(0).getState()).isEqualTo(BookState.RENTING);
    }

    @Test
    @DisplayName("도서 대여: 대여 실패")
    void rentalFail() {
        List<Book> books = repository.getList();
        BookState before = books.get(1).getState();
        repository.rental(2);
        BookState after = books.get(1).getState();

        assertThat(after).isEqualTo(before);
    }

    @Test
    void returnBook() {

    }

    @Test
    void lostBook() {
    }

    @Test
    void deleteBook() {
    }
}
