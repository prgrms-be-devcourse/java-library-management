package com.programmers.library.service;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatus;
import com.programmers.library.dto.CreateBookRequestDto;
import com.programmers.library.repository.Repository;

import java.util.List;

public class Service {

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void createBook(CreateBookRequestDto request) {
        repository.save(new Book(
                repository.generateId(),
                request.getName(),
                request.getAuthor(),
                request.getPageCount(),
                BookStatus.AVAILABLE
        ));
    }

    //TODO 정리됐는지 체크
    public List<Book> getBooks() {
        return repository.findAll();
    }

    //TODO 정리됐는지 체크
    public List<Book> getBooksByName(String name) {
        return repository.findAllByName(name);
    }

    private Book getBook(int id) {
        return repository.findOneById(id)
                .orElseThrow(() -> new IllegalArgumentException("[System] 존재하지 않는 도서번호 입니다."));
    }

    public void borrowBook(int id) {
        Book book = getBook(id);
        book.borrowed();
        repository.update(id, book);
    }

    public void returnBook(int id) {
        Book book = getBook(id);
        book.returned();
        repository.update(id, book);
    }

    public void reportLostBook(int id) {
        Book book = getBook(id);
        book.lost();
        repository.update(id, book);
    }

    public void deleteBook(int id) {
        getBook(id);
        repository.delete(id);
    }

}
