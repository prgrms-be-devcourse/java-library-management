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
        Boolean isPossible1 = bookService.isPossibleUpdateBookStatus(APPLYLOST.name(), 1);
        Boolean isPossible2 = bookService.isPossibleUpdateBookStatus(APPLYLOST.name(), 2);
        Boolean isPossible3 = bookService.isPossibleUpdateBookStatus(APPLYLOST.name(), 3);
        Boolean isPossible4 = bookService.isPossibleUpdateBookStatus(APPLYLOST.name(), 4);
        Boolean isPossible5 = bookService.isPossibleUpdateBookStatus(APPLYLOST.name(), 5);

        // then
        assertEquals(true, isPossible1);
        assertEquals(true, isPossible2);
        assertEquals(true, isPossible3);
        assertEquals(false, isPossible4);
        assertEquals(true, isPossible5);
    }

    @Test
    void 삭제신청할때_도서상태에따른_가능여부() {
        // given
        this.initData();

        // when
        Boolean isPossible1 = bookService.isPossibleUpdateBookStatus(APPLYDELETE.name(), 1);
        Boolean isPossible2 = bookService.isPossibleUpdateBookStatus(APPLYDELETE.name(), 2);
        Boolean isPossible3 = bookService.isPossibleUpdateBookStatus(APPLYDELETE.name(), 3);
        Boolean isPossible4 = bookService.isPossibleUpdateBookStatus(APPLYDELETE.name(), 4);
        Boolean isPossible5 = bookService.isPossibleUpdateBookStatus(APPLYDELETE.name(), 5);

        // then
        assertEquals(true, isPossible1);
        assertEquals(true, isPossible2);
        assertEquals(true, isPossible3);
        assertEquals(true, isPossible4);
        assertEquals(false, isPossible5);
    }

    @Test
    void 대여신청() throws InterruptedException {
        // given
        this.initData();

        // when
        String returnStr1 = bookService.updateBookStatus(APPLYRENT.name(), 1);
        String returnStr2 = bookService.updateBookStatus(APPLYRENT.name(), 2);
        String returnStr3 = bookService.updateBookStatus(APPLYRENT.name(), 3);
        String returnStr4 = bookService.updateBookStatus(APPLYRENT.name(), 4);
        String returnStr5 = bookService.updateBookStatus(APPLYRENT.name(), 5);

        // then

        // update 된 도서 상태 검증
        assertEquals(NOPOSSIBLERENT.getName(), repository.findBookById(1).getStatus());
        assertEquals(NOPOSSIBLERENT.getName(), repository.findBookById(2).getStatus());
        assertEquals(READY.getName(), repository.findBookById(3).getStatus());
        assertEquals(LOST.getName(), repository.findBookById(4).getStatus());
        assertEquals(DELETE.getName(), repository.findBookById(5).getStatus());

        // updateBookStatus 에서 applyType 이 APPLYRENT 일 때의 반환값 검증
        // 대여할 수 있다면 대여중으로 update 하고, POSSIBLERENT("대여가능") 을 반환
        assertEquals(POSSIBLERENT.getName(), returnStr1);
        assertEquals(NOPOSSIBLERENT.getName(), returnStr2);
        assertEquals(READY.getName(), returnStr3);
        assertEquals(LOST.getName(), returnStr4);
        assertEquals(DELETE.getName(), returnStr5);
    }

    @Test
    void 반납신청() {

    }

    @Test
    void 분실신청() {

    }

    @Test
    void 삭제신청() {

    }
}