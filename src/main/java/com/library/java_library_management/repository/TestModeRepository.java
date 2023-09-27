package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.response.ApiResponse;
import com.library.java_library_management.response.ApiStatus;
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
        Optional<BookInfo> book = bookList.stream()
                .filter(
                        bookinfo -> bookinfo.getBook_id() == book_id)
                .findAny();
        return book.get().getStatus().rentBook(book.get());


//                .ifPresentOrElse(bookInfo -> {
//                    bookInfo.setStatus(BookStatus.RENT);
//                    return bookInfo.getStatus();
//                }, () -> {
//
//                    throw new RuntimeException("이미 대여중인 도서입니다.");
//                        });

    }

    @Override
    public void returnBook(int book_id) {
        bookList.stream()
                .filter(book -> book.getBook_id() == book_id
                && book.getStatus().equals(BookStatus.RENT) || book.getStatus().equals(BookStatus.LOST))
                .findAny()
                .ifPresentOrElse(book -> {
                    book.setStatus(BookStatus.AVAILABLE);
                },() -> {
                    throw new RuntimeException("원래 대여 가능한 도서입니다.");
                });


    }

    @Override
    public void missBook(int book_id) {
        bookList.stream()
                .filter(bookInfo -> bookInfo.getBook_id() == book_id
                && bookInfo.getStatus() != BookStatus.LOST)
                .findAny()
                .ifPresentOrElse(book -> {
                    book.setStatus(BookStatus.LOST);
                }, () -> {
                    throw new RuntimeException("[System] 이미 분실 처리된 도서입니다.");
                });

    }



    @Override
    public void deleteById(int book_id) {
        bookList.stream()
                .filter(bookinfo -> bookinfo.getBook_id() == book_id)
                .findAny()
                .ifPresentOrElse(bookInfo -> {
                    bookList.remove(bookInfo);
                },
                        () -> {
                    throw new RuntimeException("[System] 존재하지 않는 도서입니다.");
                        });

    }

    @Override
    public Optional<BookInfo> findSameBook(String title) {
        return bookList.stream().filter(bookInfo ->
            bookInfo.getTitle().equals(title)
        ).findAny();
    }
}
