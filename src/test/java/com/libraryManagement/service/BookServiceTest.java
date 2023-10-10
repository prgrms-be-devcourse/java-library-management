package com.libraryManagement.service;

import com.libraryManagement.domain.Book;
import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.util.MyScheduler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/*
java google style guide

@BeforeEach
단위테스트와 목
 */

class BookServiceTest {
    Repository repository = new MemoryRepository();
    BookService bookService = new BookService(repository);
    private MyScheduler myScheduler = new MyScheduler();

    public void initData() {
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
    void 분실신청() {
        // given
        this.initData();

        // when

        // then
    }

    @Test
    void 삭제신청() {
        // given
        this.initData();

        // when

        // then
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    void insertBook() {
    }

    @Test
    void findBooks() {
    }

    @Test
    void findBooksByTitle() {
    }

    @Test
    void rentBook() {
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

    @Test
    void getNumCreatedBooks() {
    }
}