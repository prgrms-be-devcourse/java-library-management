package com.libraryManagement.service;

import com.libraryManagement.domain.Book;
import com.libraryManagement.util.BookResponseDTO;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.util.MyScheduler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.libraryManagement.domain.BookStatus.*;
import static com.libraryManagement.domain.ChangeBookStatus.*;
import static com.libraryManagement.domain.ChangeBookStatus.APPLYDELETE;

public class BookService {
    private final Repository repository;
    private MyScheduler myScheduler = new MyScheduler();

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void insertBook(BookResponseDTO bookResponseDTO) throws IOException {
        repository.insertBook(createBook(bookResponseDTO));
    }

    private Book createBook(BookResponseDTO bookResponseDTO) throws IOException {

        return new Book
                .Builder()
                .id(getNumCreatedBooks() + 1)
                .title(bookResponseDTO.getTitle())
                .author(bookResponseDTO.getAuthor())
                .pages(bookResponseDTO.getPages())
                .status(AVAILABLE.getName())    // 대여가능 상태로 생성
                .build();
    }

    public List<Book> findBooks() {
        return repository.findAllBooks();
    }

    public List<Book> findBooksByTitle(String str) {
        return repository.findBooksByTitle(str);
    }

    // 상태가 추가될때.. 2가지 변경 필요해짐
    // bookstatus 가 book의 외부로 노출됨 -> book 에서 캡슐화 , book.rent book.lost
    // apply 를 update 로
    public String updateBookStatus(String applyType, long id) throws InterruptedException {

        if(applyType.equals(APPLYRENT.name())){
            if(isPossibleUpdateBookStatus(applyType, id)){    // 대여할 수 있다면
                repository.updateBookStatus(id, RENT.getName());
                return AVAILABLE.getName();
            }else{
                return repository.findBookById(id).getStatus();
            }
        }else if(applyType.equals(APPLYRETURN.name())) {
            if(isPossibleUpdateBookStatus(applyType, id)){    // 반납할 수 있다면
                repository.updateBookStatus(id, READY.getName());

                // 5분 후에 대여 가능 상태로 변경합니다.
                myScheduler.scheduleTask(() -> {
                    repository.updateBookStatus(id, AVAILABLE.getName());
                }, 5, TimeUnit.MINUTES);
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

    private Boolean isPossibleUpdateBookStatus(String applyType, long id) {
        if(applyType.equals(APPLYRENT.name())){
            // 대여가능일 때만 대여가능
            if(repository.findBookById(id).getStatus().equals(AVAILABLE.getName())){
                return true;
            }
            return false;
        }else if(applyType.equals(APPLYRETURN.name())) {
            // 대여가능일 때만 반납 불가
            if(repository.findBookById(id).getStatus().equals(AVAILABLE.getName())){
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

    public long getNumCreatedBooks() {
        return repository.getNumCreatedBooks();
    }
}
