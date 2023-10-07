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
    public void rentBook(long id) throws InterruptedException {
        if(isPossibleRentBook(id))
            repository.updateBookStatus(id, RENT.getName());
    }

    public void returnBook(long id) throws IOException {
        if(isPossibleReturnBook(id))
            repository.updateBookStatus(id, RENT.getName());

        // 5분 후에 대여 가능 상태로 변경합니다.
        myScheduler.scheduleTask(() -> {
            repository.updateBookStatus(id, AVAILABLE.getName());
        }, 5, TimeUnit.MINUTES);
    }

    public void lostBook(long id) throws IOException {
        if(isPossibleLostBook(id))
            repository.updateBookStatus(id, RENT.getName());
    }

    public void deleteBook(long id) throws IOException {
        if(isPossibleDeleteBook(id))
            repository.updateBookStatus(id, RENT.getName());
    }

    private Boolean isPossibleRentBook(long id) {
        // 대여가능일 때만 대여가능
        if(repository.findBookById(id).getStatus().equals(AVAILABLE.getName())){
            return true;
        }
        return false;
    }
    private Boolean isPossibleReturnBook(long id) {
        // 대여가능일 때만 반납 불가
        if(repository.findBookById(id).getStatus().equals(AVAILABLE.getName())){
            return false;
        }
        return true;
    }
    private Boolean isPossibleLostBook(long id) {
        // 분실됨일 때만 분실처리 불가
        if(repository.findBookById(id).getStatus().equals(LOST.getName())){
            return false;
        }
        return true;
    }
    private Boolean isPossibleDeleteBook(long id) {
        // 존재하지 않는 도서일 때만 삭제처리 불가
        if(repository.findBookById(id).getStatus().equals(DELETE.getName())){
            return false;
        }
        return true;
    }

    public long getNumCreatedBooks() {
        return repository.getNumCreatedBooks();
    }
}
