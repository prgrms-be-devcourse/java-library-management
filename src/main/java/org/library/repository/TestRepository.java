package org.library.repository;

import org.library.entity.Book;

import java.util.*;

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
        processAvailable();
        Optional<List<Book>> books = Optional.ofNullable(bookMap.values().stream().sorted(Comparator.comparingLong(Book::getId)).toList());
        return books.orElse(new ArrayList<>());
    }

    @Override
    public List<Book> findByTitle(String title) {
        processAvailable();
        List<Book> findBooks = bookMap.values().stream().filter(b -> b.getTitle().contains(title)).sorted(Comparator.comparingLong(Book::getId)).toList();
        return Optional.ofNullable(findBooks).orElseThrow();
    }

    @Override
    public Book findById(Long id) {
        processAvailable();
        return bookMap.values().stream().filter(b -> b.getId().equals(id))
                .findAny().orElseThrow();
    }

    @Override
    public void delete(Book book) {
        bookMap.remove(book.getId());
    }

    public boolean exists(Book book){
        return bookMap.values().stream().anyMatch(b -> b.getId().equals(book.getId()));
    }

    public void edit(Book targetBook){
        for(Long id : bookMap.keySet()){
            if(targetBook.getId().equals(id)){
                Book book = bookMap.get(id);
                book = targetBook;
                break;
            }
        }
    }
    @Override
    public void processAvailable(){
        bookMap.values().forEach(Book::processAvailable);
    }

    public void add(Book targetBook){
        bookMap.put(targetBook.getId(), targetBook);
    }

    @Override
    public void saveFile() {
    }
}
