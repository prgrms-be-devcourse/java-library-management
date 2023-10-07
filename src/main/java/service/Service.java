package service;

import domain.BackGround;
import domain.Book;
import repository.Repository;

import java.util.List;

public class Service{

    private final Repository repository;

    private final Long sleepTime = 300000L;

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void addBook(Book book){
        repository.addBook(book);
    }

    public List<Book> getAll() {
        return repository.getAll();
    }

    public Book getBook(Long bookNumber){

        return repository.getBook(bookNumber)
                .orElseThrow(() -> new RuntimeException("[System] 존재하지 않는 도서번호 입니다."));
    }

    public List<Book> searchName(String keyword){
        return repository.searchBook(keyword);
    }

    public void deleteBook(int bookNumber){
        Book book = getBook((long)bookNumber);
        repository.deleteBook(book);
    }

    public void rentalBook(int bookNumber){
        Book book = getBook((long)bookNumber);
        book.rentalBook();
    }

    public void returnBook(int bookNumber){
        Book book = getBook((long)bookNumber);
        book.organizeBook();
        BackGround backGroundTimer = new BackGround(book, sleepTime);
        backGroundTimer.start();
    }

    public void lostBook(int bookNumber){
        Book book = getBook((long)bookNumber);
        book.lostBook();
    }
}
