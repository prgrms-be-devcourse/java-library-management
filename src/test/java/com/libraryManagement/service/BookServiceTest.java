package com.libraryManagement.service;

import com.libraryManagement.domain.Book;
import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;
import org.junit.jupiter.api.Test;

import static com.libraryManagement.domain.BookStatus.*;
import static com.libraryManagement.domain.ChangeBookStatus.*;
import static org.junit.Assert.*;

class BookServiceTest {
    static Repository repository = new MemoryRepository();
    static BookService bookService = new BookService(repository);

    public void initData() {
        Book book1 = new Book
                .Builder()
                .id(1).title("제목1").author("작가1").pages(1).status(POSSIBLERENT.getName())
                .build();

        Book book2 = new Book
                .Builder()
                .id(2).title("제목2").author("작가2").pages(2).status(NOPOSSIBLERENT.getName())
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
    void 대여신청할때_도서상태에따른_가능여부() {
        // given
        this.initData();

        // when
        Boolean isPossible1 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 1);
        Boolean isPossible2 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 2);
        Boolean isPossible3 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 3);
        Boolean isPossible4 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 4);
        Boolean isPossible5 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 5);

        // then
        assertEquals(true, isPossible1);
        assertEquals(false, isPossible2);
        assertEquals(false, isPossible3);
        assertEquals(false, isPossible4);
        assertEquals(false, isPossible5);
    }

    @Test
    void 반납신청할때_도서상태에따른_가능여부() {
        // given
        this.initData();

        // when
        Boolean isPossible1 = bookService.isPossibleUpdateBookStatus(APPLYRETURN.name(), 1);
        Boolean isPossible2 = bookService.isPossibleUpdateBookStatus(APPLYRETURN.name(), 2);
        Boolean isPossible3 = bookService.isPossibleUpdateBookStatus(APPLYRETURN.name(), 3);
        Boolean isPossible4 = bookService.isPossibleUpdateBookStatus(APPLYRETURN.name(), 4);
        Boolean isPossible5 = bookService.isPossibleUpdateBookStatus(APPLYRETURN.name(), 5);

        // then능
        assertEquals(false, isPossible1);
        assertEquals(true, isPossible2);
        assertEquals(true, isPossible3);
        assertEquals(true, isPossible4);
        assertEquals(true, isPossible5);
    }

    @Test
    void 분실신청할때_도서상태에따른_가능여부() {
        // given
        this.initData();

        // when
        Boolean isPossible1 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 1);
        Boolean isPossible2 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 2);
        Boolean isPossible3 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 3);
        Boolean isPossible4 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 4);
        Boolean isPossible5 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 5);

        // then
        assertEquals(true, isPossible1);
        assertEquals(false, isPossible2);
        assertEquals(false, isPossible3);
        assertEquals(false, isPossible4);
        assertEquals(false, isPossible5);
    }

    @Test
    void 삭제신청할때_도서상태에따른_가능여부() {
        // given
        this.initData();

        // when
        Boolean isPossible1 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 1);
        Boolean isPossible2 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 2);
        Boolean isPossible3 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 3);
        Boolean isPossible4 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 4);
        Boolean isPossible5 = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), 5);

        // then
        assertEquals(true, isPossible1);
        assertEquals(false, isPossible2);
        assertEquals(false, isPossible3);
        assertEquals(false, isPossible4);
        assertEquals(false, isPossible5);
    }

    @Test
    void 도서상태_수정() {
        // given

        // when

        // then
    }

}