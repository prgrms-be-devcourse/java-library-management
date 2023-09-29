package org.example.service;

import org.assertj.core.api.Assertions;
import org.example.domain.Book;
import org.example.domain.BookState;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class BookServiceTest {

    private BookService bookService = new BookService();

    @Test
    @DisplayName("정리중인 도서 상태 변화")
    void updateBooks() {
        //given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "test1", "writer1", 343, BookState.ORGANIZING));

        //when
        bookService.updateBooks(bookList);
        List<Book> updateBookList = bookService.getAllBooks();
        Book book = updateBookList.get(0);

        //then
        Assertions.assertThat(book.getState()).isEqualTo(BookState.POSSIBLE);
    }

    @Test
    @DisplayName("도서 등록")
    void createBook() throws IOException {
        //given
        String title = "test1";
        String author = "writer1";
        int pageNum = 123;

        //when
        bookService.createBook(title, author, pageNum);
        List<Book> bookList = bookService.getAllBooks();
        Book book = bookList.get(0);

        //then
        Assertions.assertThat(book.getTitle()).isEqualTo(title);
        Assertions.assertThat(book.getAuthor()).isEqualTo(author);
        Assertions.assertThat(book.getPageNum()).isEqualTo(pageNum);
    }

    @Test
    @DisplayName("모든 책 조회")
    void getAllBooks() {
        //given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "test1", "writer1", 343, BookState.POSSIBLE));
        bookList.add(new Book(2, "test2", "writer2", 31, BookState.POSSIBLE));
        bookList.add(new Book(3, "test3", "writer3", 23, BookState.POSSIBLE));
        bookService.updateBooks(bookList);

        //when
        List<Book> getBookList = bookService.getAllBooks();

        //then
        Assertions.assertThat(bookList.size()).isEqualTo(getBookList.size());
    }

    @Test
    @DisplayName("책 제목으로 조회")
    void findByTitle() throws IOException {
        //given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "apple", "writer1", 123, BookState.POSSIBLE));
        bookList.add(new Book(2, "banana", "writer2", 13, BookState.RENTING));
        bookList.add(new Book(3, "apple and water", "writer3", 413, BookState.POSSIBLE));
        bookService.updateBooks(bookList);

        //when
        List<Book> findBookList = bookService.findByTitle("apple");

        //then
        Assertions.assertThat(findBookList.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("도서 대여")
    void rentBook() throws IOException {
        //given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "Possible", "writer1", 123, BookState.POSSIBLE));
        bookList.add(new Book(2, "Renting", "writer2", 13, BookState.RENTING));
        bookService.updateBooks(bookList);

        //when
        Book book = bookService.rentBook(1);

        //then
        Assertions.assertThat(book.getState()).isEqualTo(BookState.RENTING);
        org.junit.jupiter.api.Assertions.assertThrows(NoSuchElementException.class, () -> {
            bookService.rentBook(5);
        });
    }

    @Test
    @DisplayName("도서 반납")
    void returnBook() throws IOException {
        //given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "Renting", "writer2", 13, BookState.RENTING));
        bookList.add(new Book(2, "Organizing", "writer3", 23, BookState.ORGANIZING));
        bookService.updateBooks(bookList);

        //when
        Book book = bookService.returnBook(1);

        //then
        Assertions.assertThat(book.getState()).isEqualTo(BookState.ORGANIZING);
        try{
            Thread.sleep(10003);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Assertions.assertThat(book.getState()).isEqualTo(BookState.POSSIBLE);
        }
    }

    @Test
    @DisplayName("도서 분실 처리")
    void lostBook() throws IOException {
        //given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "Renting", "writer2", 13, BookState.ORGANIZING));
        bookList.add(new Book(2, "Organizing", "writer3", 23, BookState.LOST));
        bookService.updateBooks(bookList);

        //when
        Book book1 = bookService.lostBook(1);
        Book book2 = bookService.returnBook(2);

        //then
        Assertions.assertThat(book1.getState()).isEqualTo(BookState.LOST);
        Assertions.assertThat(book2.getState()).isEqualTo(BookState.ORGANIZING);
        try{
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            Assertions.assertThat(book2.getState()).isEqualTo(BookState.POSSIBLE);
        }
    }

    @Test
    @DisplayName("도서 삭제 + id 재정렬")
    void deleteBook() throws IOException {
        //given
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "Renting", "writer2", 13, BookState.ORGANIZING));
        bookList.add(new Book(2, "Organizing", "writer3", 23, BookState.LOST));
        bookService.updateBooks(bookList);

        //when
        Book book = bookService.deleteBook(1);
        List<Book> deleteBookList = bookService.getAllBooks();

        //then
        Assertions.assertThat(deleteBookList.size()).isEqualTo(1);
        Assertions.assertThat(book.getId()).isEqualTo(1);
    }
}