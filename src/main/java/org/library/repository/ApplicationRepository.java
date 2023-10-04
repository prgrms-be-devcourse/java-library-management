package org.library.repository;

import org.library.entity.Book;
import org.library.error.NotExistError;
import org.library.utils.JsonManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationRepository implements Repository{
    private static final Map<Long, Book> bookMap = new ConcurrentHashMap<>();
    private final JsonManager jsonManager;

    public ApplicationRepository() {
        jsonManager = new JsonManager();
        List<Book> fileBooks = jsonManager.read();
        fileBooks.forEach(this::add);
    }

    @Override
    public Long generatedId() {
        Long max = bookMap.keySet().stream().max(Comparator.naturalOrder()).orElse(0L);
        return max + 1;
    }

    @Override
    public void save(Book book) {
        if(exists(book)){
            edit(book);
        } else{
            add(book);
        }
        flush();
    }

    @Override
    public List<Book> findAll() {
        processAvailable();
        return bookMap.values().stream().sorted(Comparator.comparingLong(Book::getId)).toList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        processAvailable();
        return bookMap.values().stream().filter(b -> b.getTitle().contains(title)).sorted(Comparator.comparingLong(Book::getId)).toList();
    }

    @Override
    public Book findById(Long id) {
        processAvailable();
        return bookMap.values().stream().filter(b -> b.getId().equals(id))
                .findAny().orElseThrow();
    }

    @Override
    public void delete(Book book) {
        if(!bookMap.containsKey(book.getId())){
            throw new NotExistError();
        }
        bookMap.remove(book.getId());
        flush();

    }

    @Override
    public void processAvailable() {
        bookMap.values().forEach(Book::processAvailable);
    }

    public boolean exists(Book book){
        return bookMap.values().stream().anyMatch(b -> b.getId().equals(book.getId()));
    }

    public void edit(Book targetBook){
        for(Long id : bookMap.keySet()){
            if(targetBook.getId().equals(id)){
                Book book = findById(id);
                book = targetBook;
                break;
            }
        }
    }
    
    public void add(Book targetBook){
        bookMap.put(targetBook.getId(), targetBook);
    }

    @Override
    public void flush(){
        jsonManager.write(findAll());
    }
}
