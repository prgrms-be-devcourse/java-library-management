package com.programmers.controller;

import com.programmers.domain.Book;
import com.programmers.exception.ErrorChecking;
import com.programmers.exception.ErrorCode;
import com.programmers.exception.LibraryException;
import com.programmers.front.BookConsole;
import com.programmers.service.BookService;

import java.util.List;

public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * 1. 도서 등록
     */
    public void enrollBook() throws LibraryException{
            BookConsole.startEnrollMode();
            String bookName = BookConsole.inputBookName();
            System.out.println();
            String author = BookConsole.inputAuthorName();
            String page = BookConsole.inputBookTotalPage();
            if(!ErrorChecking.checkNumber(page)) throw new LibraryException(ErrorCode.NOT_NUMBER);
            if(!ErrorChecking.minusChecking(Integer.parseInt(page))) throw new LibraryException(ErrorCode.NOT_NUMBER);
            bookService.enrollBook(Book.enrollingBook(bookName, author, Integer.parseInt(page)));
    }

    /**
     * 2. 전체 도서 목록 조회
     */
    public void findAllBooks(){
        List<Book> allBooks = bookService.findAllBooks();
        if(allBooks == null) BookConsole.nothingFound();
        else BookConsole.showAllBooks(allBooks);
    }

    /**
     * 3. 제목으로 도서 검색
     */
    public void findBookByTitle(){
        String keyWord = BookConsole.searchBookMode();
        List<Book> bookByTitle = bookService.findBookByTitle(keyWord);
        if(bookByTitle == null) BookConsole.nothingFound();
        else BookConsole.showAllBooks(bookByTitle);
    }

    /**
     * 4. 도서 대여
     */
    public void rentBook() throws LibraryException{
        String bookId = BookConsole.rentBookMode();
        bookService.rentBook(bookId);
        BookConsole.rentSuccess();
    }

    /**
     *  5. 도서 반납
     */
    public void returnBook() throws LibraryException{
        String bookId = BookConsole.returnBookMode();
        bookService.returnBook(bookId);
        BookConsole.returnSuccess();
    }

    /**
     * 6. 도서 분실
     */
    public void loseBook() throws LibraryException{
        String bookId = BookConsole.loseBookMode();
        bookService.loseBook(bookId);
        BookConsole.loseBookFinished();
    }

    /**
     * 7. 도서 삭제
     */
    public void deleteBook() throws LibraryException{
        String bookId = BookConsole.deleteBookMode();
        bookService.deleteBook(bookId);
        BookConsole.deleteBookFinished();
    }
}
