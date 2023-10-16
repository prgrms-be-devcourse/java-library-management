package com.libraryManagement.service;

import com.libraryManagement.domain.Book;
import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.util.MyScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/*
java google style guide
 */

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    Repository repository;
    BookService bookService;
    private MyScheduler myScheduler = new MyScheduler();

    @BeforeEach
    void setUp() {
        repository = new MemoryRepository();
        bookService = new BookService(repository);
        myScheduler = new MyScheduler();

        Book book1 = new Book
                .Builder()
                .id(1).title("제목1").author("작가1").pages(1).status(AVAILABLE.getName())
                .build();

        Book book2 = new Book
                .Builder()
                .id(2).title("제목2").author("작가2").pages(2).status(RENT.getName())
                .build();

        Book book3 = new Book
                .Builder()
                .id(3).title("제목3").author("작가3").pages(3).status(READY.getName())
                .build();

        Book book4 = new Book
                .Builder()
                .id(4).title("제목4").author("작가4").status(LOST.getName())
                .build();

        Book book5 = new Book
                .Builder()
                .id(5).title("제목5").author("작가5").pages(5).status(DELETE.getName())
                .build();

        repository.insertBook(book1);
        repository.insertBook(book2);
        repository.insertBook(book3);
        repository.insertBook(book4);
        repository.insertBook(book5);
    }

    @Test
    @DisplayName("")
    void insertBook() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("")
    void findBooks() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("")
    void findBooksByTitle() {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("")
    void rentBook() {
    }

    @Test
    @DisplayName("")
    void returnBook() {
    }

    @Test
    @DisplayName("")
    void lostBook() {
    }

    @Test
    @DisplayName("")
    void deleteBook() {
    }

    @Test
    @DisplayName("")
    void getNumCreatedBooks() {
    }
}