package dev.course.repository;

import dev.course.domain.Book;

import java.util.*;

public interface BookRepository {

    Long createBookId();
    Optional<Book> findById(Long bookId);
    void add(Book obj);
    void delete(Long bookId);
    List<Book> getAll();
    List<Book> findByTitle(String title);
    int getSize();

    default void printBook(Book obj) {

        System.out.println("도서번호 : " + obj.getBookId());
        System.out.println("제목 : " + obj.getTitle());
        System.out.println("작가 이름 : " + obj.getAuthor());
        System.out.println("페이지 수 : " + obj.getPage_num() + " 페이지");
        System.out.println("상태 : " + obj.getState().getState());
        System.out.println("\n------------------------------\n");
    }
}
