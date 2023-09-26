package org.library.repository;

import org.library.entity.Book;

import java.util.ArrayList;
import java.util.List;

public class TestRepository implements Repository{
    private static final List<Book> bookList = new ArrayList<>();

    @Override
    public void save(Book book) {
        if(exists(book)){ // 수정
            edit(book);
        }else{ // 추가
            add(book);
        }
    }

    @Override
    public List<Book> findAll() {
        return bookList;
    }

    @Override
    public Book findByTitle(String title) {
        return bookList.stream().filter(b -> b.getTitle().contains(title))
                .findAny().orElseThrow();
    }

    @Override
    public Book findById(Long id) {
        return bookList.stream().filter(b -> b.getId().equals(id))
                .findAny().orElseThrow();
    }

    @Override
    public void delete(Book book) {
        bookList.remove(book);
    }

    public boolean exists(Book book){
        return bookList.stream().anyMatch(b -> b.equals(book));
    }

    public void edit(Book targetBook){
        for(Book book : bookList){
            if(book.equals(targetBook)){
                book = targetBook;
                return;
            }
        }
    }

    public void add(Book targetBook){
        bookList.add(targetBook);
    }
}
