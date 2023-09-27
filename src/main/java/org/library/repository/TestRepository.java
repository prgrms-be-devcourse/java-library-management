package org.library.repository;

import org.library.entity.Book;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestRepository implements Repository{
    private static final Map<Long, Book> bookMap = new HashMap<>();

    @Override
    public Long generatedId() {
        Long max = bookMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0L);
        return max + 1;
    }

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
        return bookMap.values().stream().sorted().toList();
    }

    @Override
    public Book findByTitle(String title) {
        return bookMap.values().stream().filter(b->b.getTitle().contains(title))
                .findAny().orElseThrow();
    }

    @Override
    public Book findById(Long id) {
        return bookMap.values().stream().filter(b -> b.getId().equals(id))
                .findAny().orElseThrow();
    }

    @Override
    public void delete(Book book) {
        bookMap.remove(book.getId());
    }

    public boolean exists(Book book){
        return bookMap.values().stream().anyMatch(b -> b.equals(book));
    }

    public void edit(Book targetBook){
        for(Book book : bookMap.values()){
            if(book.equals(targetBook)){
                book = targetBook;
                return;
            }
        }
    }

    public void add(Book targetBook){
        bookMap.put(targetBook.getId(), targetBook);
    }
}
