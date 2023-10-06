package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.status.BookStatus;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;

public class TestModeRepository implements Repository{
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Map<Integer, BookInfo> bookMap = new HashMap<>();
    private int book_number_cnt = 1;

    @Override
    public BookStatus getStatusById(int book_id) {
        BookInfo book = bookMap.get(book_id);
        if(book == null)
            return null;
        return book.getStatus();
    }

    @Override
    public void registerBook(String title, String author, int pageSize) {
        bookMap.put(book_number_cnt, new BookInfo(book_number_cnt, title, author, pageSize, BookStatus.AVAILABLE));
        book_number_cnt++;

    }
    @Override
    public List<BookInfo> getTotalBook() {
        List<BookInfo> bookList = new ArrayList<>(bookMap.values());
        return bookList;
    }

    @Override
    public List<BookInfo> findByTitle(String title) {
        List<BookInfo> bookList = getTotalBook();
        return bookList.stream()
                .filter(bookinfo -> bookinfo.getTitle().contains(title))
                .collect(Collectors.toList());
    }
    @Override
    public String rentBook(int book_id){
        try{
            BookInfo bookInfo = bookMap.get(book_id);
            return bookInfo.getStatus().rentBook(bookInfo);
        }catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public void returnBook(int book_id) {
        BookInfo book = bookMap.get(book_id);
        book.setStatus(BookStatus.AVAILABLE);
        bookMap.put(book_id, book);
    }

    @Override
    public void missBook(int book_id) {
        BookInfo book = bookMap.get(book_id);
        book.setStatus(BookStatus.LOST);
        bookMap.put(book_id, book);
    }




    @Override
    public void deleteById(int book_id) {
        try{
            BookInfo book = bookMap.get(book_id);
            if(book == null){
                throw new NullPointerException();
            }
            bookMap.remove(book_id);
        }catch (NullPointerException e){
            System.out.println("존재하지 않는 도서입니다.");
        }
    }

    @Override
    public Optional<BookInfo> findSameBook(String title) {
        List<BookInfo> bookList = getTotalBook();
        return bookList.stream()
                .filter(bookInfo -> bookInfo.getTitle().equals(title))
                .findAny();
    }
}
