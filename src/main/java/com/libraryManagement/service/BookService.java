package com.libraryManagement.service;

import com.libraryManagement.domain.Book;
import com.libraryManagement.util.BookResponseDTO;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.util.MyScheduler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.libraryManagement.domain.Book.BookStatus.*;

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

    public void rentBook(long id) {
        if(repository.findBookById(id).isPossibleRent())
            repository.updateBookStatus(id, RENT.getName());
    }

    public void returnBook(long id) {
        if(repository.findBookById(id).isPossibleReturn())
            repository.updateBookStatus(id, ORGANIZING.getName());

        // 5분 후에 대여 가능 상태로 변경합니다.
        myScheduler.scheduleTask(() -> {
            repository.updateBookStatus(id, AVAILABLE.getName());
        }, 5, TimeUnit.MINUTES);
    }

    public void lostBook(long id) {
        if(repository.findBookById(id).isPossibleLost())
            repository.updateBookStatus(id, LOST.getName());
    }

    public void deleteBook(long id) {
        if(repository.findBookById(id).isPossibleDelete())
            repository.updateBookStatus(id, DELETE.getName());
    }

    public long getNumCreatedBooks() {
        return repository.getNumCreatedBooks();
    }
}
