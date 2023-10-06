package service;

import domain.Book;
import repository.Repository;

import java.util.List;

public class Service{

    private final Repository repository;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void addBook(Book book){
        repository.addBook(book);
    }

    public List<Book> getAll() {
        return repository.getAll();
    }

    public List<Book> searchName(String keyword){
        return repository.searchBook(keyword);
    }

    public void rentalBook(int bookNumber){
        repository.rentalBook((long)bookNumber);
    }

    public void organizeBook(int bookNumber){
        repository.organizeBook((long)bookNumber);
    }

    public void lostBook(int bookNumber){
        repository.lostBook((long)bookNumber);
    }

    public void deleteBook(int bookNumber){
        repository.deleteBook((long)bookNumber);
    }
}
