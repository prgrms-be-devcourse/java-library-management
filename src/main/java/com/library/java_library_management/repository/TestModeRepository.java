package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.response.ApiResponse;
import com.library.java_library_management.status.BookStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestModeRepository implements Repository{

    public List<BookInfo> bookList = new ArrayList<>();
    int book_number_cnt = 1;
    @Override
    public void registerBook(String title, String author, int pageSize) {
        bookList.add(new BookInfo(book_number_cnt, author, title, pageSize, BookStatus.AVAILABLE));
        book_number_cnt++;
    }
    @Override
    public List<BookInfo> getTotalBook() {
        return bookList;
    }
    @Override
    public List<BookInfo> findByTitle(String title) {
        return bookList.stream()
                .filter(bookinfo -> bookinfo.getTitle().contains(title))
                .collect(Collectors.toList());
    }
    @Override
    public String rentBook(int book_id){
        Optional<BookInfo> book = bookList.stream().filter(bookinfo -> bookinfo.getBook_id() == book_id).findAny();
        return book.get().getStatus().rentBook(book.get());
    }

    @Override
    public ApiResponse returnBook(int book_id) {
        Optional<BookInfo> returnBookInfo = bookList.stream()
                .filter(book -> book.getBook_id() == book_id)
                .findAny();

        if(returnBookInfo.get().getStatus().equals(BookStatus.RENT) || returnBookInfo.get().getStatus().equals(BookStatus.LOST)){
            returnBookInfo.get().setStatus(BookStatus.AVAILABLE);
            return ApiResponse.success("반납 처리 되었습니다.");
        }
        return ApiResponse.error(returnBookInfo.get().getStatus().rentBook(returnBookInfo.get()));

    }

    @Override
    public String missBook(int book_id) {
        Optional<BookInfo> book = bookList.stream().filter(bookInfo -> bookInfo.getBook_id() == book_id).findAny();
        if(book.get().getStatus().equals(BookStatus.LOST)){
            return "[System]이미 분실 처리된 도서입니다.";
        }

        book.get().setStatus(BookStatus.LOST);
        return "[System]도서가 분실처리 되었습니다.";
    }



    @Override
    public void deleteById(int book_id) {
        Optional<BookInfo> book = bookList.stream()
                .filter(bookinfo -> bookinfo.getBook_id() == book_id)
                .findAny();
        bookList.remove(book.get());
    }






}
