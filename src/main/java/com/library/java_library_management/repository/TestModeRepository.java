package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.status.BookStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class TestModeRepository implements Repository{
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private HashMap<Integer, BookInfo> bookMap = new HashMap<>();
    private int book_number_cnt = 1;


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
            return "[System] 존재하지 않는 도서입니다.";
        }

    }

    @Override
    public void returnBook(int book_id) {
        try {
            BookInfo book = bookMap.get(book_id);
            if (book.getStatus().equals(BookStatus.RENT) || book.getStatus().equals(BookStatus.LOST)) {
                book.setStatus(BookStatus.CLEANING);
                scheduler.schedule(() -> {
                    book.setStatus(BookStatus.AVAILABLE);
                }, 5, TimeUnit.MINUTES);
                System.out.println("반납 처리 되었습니다.");
            } else {
                System.out.println("원래 대여 가능한 도서입니다");
            }
        } catch (NullPointerException e) {
            System.out.println("존재하지 않는 도서입니다.");
        }
    }

    @Override
    public void missBook(int book_id) {
        try{
            BookInfo book = bookMap.get(book_id);
            if(book.getStatus() != BookStatus.LOST){
                book.setStatus(BookStatus.LOST);
                System.out.println("[System] 도서가 분실 처리 되었습니다.");
            }
            else{
                throw new RuntimeException();
            }
        }catch (NullPointerException e){
            System.out.println("존재하지 않는 도서번호입니다.");
        }
        catch (RuntimeException e){
            System.out.println("이미 분실 처리된 도서입니다.");
        }
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
