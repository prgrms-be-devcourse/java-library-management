package com.libraryManagement.controller;

import com.libraryManagement.domain.Book;
import com.libraryManagement.domain.DtoBook;
import com.libraryManagement.service.BookService;
import com.libraryManagement.io.BookIO;

import java.io.IOException;
import java.util.List;

import static com.libraryManagement.domain.BookStatus.*;
import static com.libraryManagement.domain.ChangeBookStatus.*;
import static com.libraryManagement.util.GlobalVariables.numCreatedBooks;

public class BookController {
    private final BookService bookService;
    private final BookIO bookIO;

    public BookController(BookService bookService, BookIO bookIO) {
        this.bookService = bookService;
        this.bookIO = bookIO;
    }

    public void insertBook() throws IOException {
        bookService.insertBook(createBook());
    }

    public Book createBook() throws IOException {
        long id = ++numCreatedBooks;

        DtoBook dtoBook = bookIO.inputBookInsert();
        String title = dtoBook.getTitle();
        String author = dtoBook.getAuthor();
        int pages = dtoBook.getPages();

        String status = POSSIBLERENT.getName();  // 대여가능 상태로 생성

        return new Book
                .Builder()
                .id(id)
                .title(title)
                .author(author)
                .pages(pages)
                .status(status)
                .build();
    }

    public void findBooks() {
        List<Book> bookList = bookService.findBooks();
        bookIO.outputBookList(bookList);
    }

    public void findBookByTitle() throws IOException {
        String str = bookIO.inputBookTitleFind();
        List<Book> bookList = bookService.findBookByTitle(str);
        bookIO.outputBookList(bookList);
    }

    public void updateBookStatus(String updateType) throws IOException {
        Boolean isPossible = null;
        String bookStatus = null;

        if(updateType.equals(APPLYRENT.name())){
            long id = bookIO.inputRentBookId();
            bookStatus = bookService.updateBookStatus(APPLYRENT.name(), id);
            isPossible = bookService.isPossibleUpdateBookStatus(APPLYRENT.name(), id);
        }else if(updateType.equals(APPLYRETURN.name())) {
            long id = bookIO.inputReturnBookId();
            bookService.updateBookStatus(APPLYRETURN.name(), id);
            isPossible = bookService.isPossibleUpdateBookStatus(APPLYRETURN.name(), id);
        }else if(updateType.equals(APPLYLOST.name())) {
            long id = bookIO.inputLostBookId();
            bookService.updateBookStatus(APPLYLOST.name(), id);
            isPossible = bookService.isPossibleUpdateBookStatus(APPLYLOST.name(), id);
        }else if(updateType.equals(APPLYDELETE.name())) {
            long id = bookIO.inputDeleteBookId();
            bookService.updateBookStatus(APPLYDELETE.name(), id);
            isPossible = bookService.isPossibleUpdateBookStatus(APPLYDELETE.name(), id);
        }

        bookIO.outputUpdateMsg(updateType, isPossible, bookStatus);
    }

}
