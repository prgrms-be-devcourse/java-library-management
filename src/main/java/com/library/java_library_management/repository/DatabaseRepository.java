package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.status.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseRepository implements Repository{

    public List<BookInfo> bookList = new ArrayList<>();
    @Override
    public String rentBook(int book_id){
        String message = "";
        for(BookInfo book : bookList){
            if(book.getBook_id() == book_id) {
                BookStatus status = book.getStatus();

                switch (status) {
                    case RENT:
                        message = "이미 대여중인 도서입니다.";
                        break;
                    case CLEANING:
                        message = "정리중인 도서입니다.";
                        break;
                    case LOST:
                        message = "분실된 도서입니다.";
                        break;
                    case AVAILABLE:
                        book.setStatus(BookStatus.RENT);
                        break;
                }
            }
        }
        return message;
    }

    @Override
    public void returnBook(int book_id) {
        Optional<BookInfo> returnBookInfo = bookList.stream()
                .filter(book -> book.getBook_id() == book_id)
                .findAny();

        returnBookInfo.get().setStatus(BookStatus.AVAILABLE);
    }

    @Override
    public BookInfo findByTitle(String title) {
        Optional<BookInfo> book = bookList.stream()
                .filter(bookinfo -> bookinfo.getTitle() == title)
                .findAny();
        return book.get();
    }

    @Override
    public void deleteById(int book_id) {
        Optional<BookInfo> book = bookList.stream()
                .filter(bookinfo -> bookinfo.getBook_id() == book_id)
                .findAny();
        bookList.remove(book);
    }

    @Override
    public void registerBook(String title, String author, int pageSize) {
        bookList.add(new BookInfo(bookList.size() + 1, author, title, pageSize, BookStatus.AVAILABLE));
    }
}
