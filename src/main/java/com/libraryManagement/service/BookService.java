package com.libraryManagement.service;

import com.libraryManagement.domain.Book;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.util.MyScheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledExecutorService;

import static com.libraryManagement.domain.BookStatus.*;
import static com.libraryManagement.domain.ChangeBookStatus.*;
import static com.libraryManagement.domain.ChangeBookStatus.APPLYDELETE;

public class BookService {
    private final Repository repository;
    private MyScheduler myScheduler = new MyScheduler();

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void insertBook(Book book) {
        repository.insertBook(book);
    }

    public List<Book> findBooks() {
        return repository.findAllBooks();
    }

    public List<Book> findBooksByTitle(String str) {
        return repository.findBooksByTitle(str);
    }

    public Boolean isPossibleUpdateBookStatus(String applyType, long id) {
        if(applyType.equals(APPLYRENT.name())){
            // 대여가능일 때만 대여가능
            if(repository.findBookById(id).getStatus().equals(POSSIBLERENT.getName())){
                return true;
            }
            return false;
        }else if(applyType.equals(APPLYRETURN.name())) {
            // 대여가능일 때만 반납 불가
            if(repository.findBookById(id).getStatus().equals(POSSIBLERENT.getName())){
                return false;
            }
            return true;
        }else if(applyType.equals(APPLYLOST.name())) {
            // 분실됨일 때만 분실처리 불가
            if(repository.findBookById(id).getStatus().equals(LOST.getName())){
                return false;
            }
            return true;
        }else if(applyType.equals(APPLYDELETE.name())) {
            // 존재하지 않는 도서일 때만 삭제처리 불가
            if(repository.findBookById(id).getStatus().equals(DELETE.getName())){
                return false;
            }
            return true;
        }

        return null;
    }

    // bookStatus 를 반환 -> update 불가능 원인을 알려주기 위해서 (대여신청일 때만 사용)
    public String updateBookStatus(String applyType, long id) throws InterruptedException {

        if(applyType.equals(APPLYRENT.name())){
            if(isPossibleUpdateBookStatus(applyType, id)){    // 대여할 수 있다면
                repository.updateBookStatus(id, NOPOSSIBLERENT.getName());
                return POSSIBLERENT.getName();
            }else{
                return repository.findBookById(id).getStatus();
            }
        }else if(applyType.equals(APPLYRETURN.name())) {
            if(isPossibleUpdateBookStatus(applyType, id)){    // 반납할 수 있다면
                repository.updateBookStatus(id, READY.getName());

                // 5분 후에 대여 가능 상태로 변경합니다.
                myScheduler.scheduleTask(() -> {
                    repository.updateBookStatus(id, POSSIBLERENT.getName());
                }, 5, TimeUnit.SECONDS);
            }
        }else if(applyType.equals(APPLYLOST.name())) {
            if(isPossibleUpdateBookStatus(applyType, id)){    // 분실처리할 수 있다면
                repository.updateBookStatus(id, LOST.getName());
            }
        }else if(applyType.equals(APPLYDELETE.name())) {
            if(isPossibleUpdateBookStatus(applyType, id)){    // 삭제처리할 수 있다면
                repository.updateBookStatus(id, DELETE.getName());
            }
        }

        return null;
    }

    public long getNumCreatedBooks() {
        return repository.getNumCreatedBooks();
    }
}
