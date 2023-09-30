package org.library.repository;

import org.library.entity.Book;
import org.library.error.NotExistError;
import org.library.utils.FileManager;

import java.util.*;

public class ApplicationRepository implements Repository{
    private static final Map<Long, Book> bookMap = new HashMap<>();
    private final FileManager fileManager;

    public ApplicationRepository() {
        fileManager = new FileManager();
        List<Book> fileBooks = fileManager.read();
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
        saveFile();
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
        if(!bookMap.containsKey(book.getId())){
            throw new NotExistError();
        }
        bookMap.remove(book.getId());
        saveFile();

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
    public void saveFile(){
        fileManager.write(findAll());
    }
}
