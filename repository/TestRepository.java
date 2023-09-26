package repository;

import entity.Book;

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
        return null;
    }

    @Override
    public Book findByTitle(String title) {
        return null;
    }

    @Override
    public Book findById(Long id) {
        return null;
    }

    @Override
    public void delete(Book book) {

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
