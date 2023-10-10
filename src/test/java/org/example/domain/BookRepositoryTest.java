package org.example.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookRepositoryTest {
    BookRepository bookRepository = new BookRepository();

    @Test
    @DisplayName("repository 크기")
    void getSize() {
        //given
        List<Book> books = new ArrayList<>();

        //when
        books.add(new Book(1, "title1", "author1", 343, BookState.POSSIBLE));
        books.add(new Book(2, "title2", "author2", 100, BookState.POSSIBLE));
        bookRepository.updateList(books);

        //then
        assertThat(bookRepository.getSize()).isEqualTo(2);
    }

    @Test
    @DisplayName("repository에 도서 추가")
    void addBook() {
        //given
        Book book = new Book(3, "title1", "author1", 31, BookState.POSSIBLE);

        //when
        bookRepository.addBook(book);
        Book findBook = bookRepository.getAllBooks().get(0);

        //then
        assertThat(findBook.getId()).isEqualTo(book.getId());
    }

    @Test
    @DisplayName("모든 도서 목록 불러오기")
    void getAllBooks() {
        //given
        bookRepository.addBook(new Book(1, "title1", "author1", 232, BookState.POSSIBLE));
        bookRepository.addBook(new Book(2, "title2", "author2", 22, BookState.POSSIBLE));

        //when
        List<Book> getBookList = bookRepository.getAllBooks();

        //then
        assertThat(getBookList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("제목으로 도서 조회")
    void findByTitle() {
        //given
        bookRepository.addBook(new Book(1, "title1-apple", "author1", 232, BookState.POSSIBLE));
        bookRepository.addBook(new Book(2, "title2-banana", "author2", 2, BookState.POSSIBLE));
        bookRepository.addBook(new Book(3, "title3-bapple", "author3", 211, BookState.POSSIBLE));

        //when
        List<Book> findBookList = bookRepository.findByTitle("apple");

        //then
        assertThat(findBookList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("id로 도서 조회")
    void findById() {
        //given
        bookRepository.addBook(new Book(1, "title1", "author1", 323, BookState.POSSIBLE));
        bookRepository.addBook(new Book(2, "title2", "author2", 12, BookState.POSSIBLE));
        bookRepository.addBook(new Book(3, "title3", "author3", 321, BookState.POSSIBLE));

        //when
        Book findBook = bookRepository.findById(3);

        //then
        assertThat(findBook.getTitle()).isEqualTo("title3");
        assertThat(findBook.getAuthor()).isEqualTo("author3");
    }

    @Test
    @DisplayName("삭제한 id 다음부터 id 재정렬")
    void updateListId() {
        //given
        bookRepository.addBook(new Book(1, "title1", "author1", 323, BookState.POSSIBLE));
        bookRepository.addBook(new Book(2, "title2", "author2", 12, BookState.POSSIBLE));
        bookRepository.addBook(new Book(4, "title3", "author3", 321, BookState.POSSIBLE));

        //when
        bookRepository.updateListId(3);
        List<Book> findBookList = bookRepository.getAllBooks();
        Book findBook = findBookList.get(2);

        //then
        assertThat(findBook.getId()).isEqualTo(3);
    }

    @Test
    @DisplayName("도서 목록 업데이트")
    void updateList() {
        //given
        List<Book> bookList = new ArrayList<>();
        bookRepository.addBook(new Book(2, "title2", "author2", 12, BookState.POSSIBLE));
        bookRepository.addBook(new Book(3, "title3", "author3", 321, BookState.POSSIBLE));

        //when
        bookRepository.updateList(bookList);

        //then
        assertThat(bookRepository.getAllBooks().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("도서 id로 도서삭제")
    void deleteById() {
        //given
        bookRepository.addBook(new Book(1, "title2", "author2", 12, BookState.POSSIBLE));
        bookRepository.addBook(new Book(2, "title3", "author3", 321, BookState.POSSIBLE));

        //when
        Book deletedBook = bookRepository.deleteById(2);

        //then
        assertThat(deletedBook.getTitle()).isEqualTo("title3");
        assertThat(deletedBook.getAuthor()).isEqualTo("author3");
    }
}