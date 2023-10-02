package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.domain.BookStatusType;
import com.programmers.library.file.FileHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository implements Repository{

    private final Map<Long, Book> map;
    private final FileHandler fileHandler;

    public FileRepository(FileHandler fileHandler) {
        this.fileHandler = fileHandler;
        this.map = initBooks();
    }
    public Map<Long, Book> initBooks(){
        return fileHandler.init();
    }

    public void updateStatus(Book book, BookStatusType bookStatus){
        book.updateBookStatus(bookStatus);
        fileHandler.updateFile(book);
    }

    @Override
    public void register(Book book) {
        map.put(book.getBookId(), book);
        fileHandler.saveToFile(book);
    }

    @Override
    public List<Book> findAllBooks(){
        return new ArrayList<>(map.values());
    }

    @Override
    public void deleteBook(Long id){
        map.remove(id);
    }

    @Override
    public List<Book> findBooksByTitle(String title){
        return map.values().stream().filter(book -> book.getTitle().contains(title)).collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findBookById(Long id) {
        return Optional.ofNullable(map.get(id));
    }


    @Override
    public Long findLastId() {
        Long maxId = map.keySet().stream().max(Long::compareTo).orElse(0L);
        return maxId;
    }
}
