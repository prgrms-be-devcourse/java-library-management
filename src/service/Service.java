package service;

import domain.Book;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class Service{

    private Repository repository;

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

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
        Book book = repository.getBook((long)bookNumber);
        book.rentalBook();
    }

    public void organizeBook(int bookNumber){
        Book book = repository.getBook((long)bookNumber);
        book.organizeBook();
        BackGround backGroundTimer = new BackGround(book);
        backGroundTimer.start();
    }

    public void lostBook(int bookNumber){
        Book book = repository.getBook((long)bookNumber);
        book.lostBook();
    }

    public void deleteBook(int bookNumber){
        repository.deleteBook((long)bookNumber);
    }

    private static class BackGround extends Thread{
        private Book book;

        BackGround(Book book){
            this.book = book;
        }

        @Override
        public void run() {
            try {
                sleep(300000);
                book.returnBook();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
