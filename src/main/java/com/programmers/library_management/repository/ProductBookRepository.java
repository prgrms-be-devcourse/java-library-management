package com.programmers.library_management.repository;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.utils.CsvFileManager;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductBookRepository implements BookRepository {
    private final Map<Integer, Book> bookMemory;
    private final CsvFileManager csvFileManager;

    public ProductBookRepository() {
        csvFileManager = new CsvFileManager();
        this.bookMemory = csvFileManager.loadMemoryFromCsv();
    }

    @Override
    public void save(Book book) {
        bookMemory.put(book.getId(), book);
        csvFileManager.saveMemoryToCsv(bookMemory);
    }

    @Override
    public Optional<Book> findByBookNumber(int bookNumber) {
        return Optional.ofNullable(bookMemory.get(bookNumber));
    }

    @Override
    public List<Book> findByTitle(String searchText) {
        return bookMemory.values()
                .stream()
                .filter(book -> book.getTitle().contains(searchText))
                .toList();
    }

    @Override
    public List<Book> findAll() {
        return bookMemory.values()
                .stream()
                .toList();
    }

    @Override
    public void delete(Book book) {
        bookMemory.remove(book.getId());
        csvFileManager.saveMemoryToCsv(bookMemory);
    }

    @Override
    public void updateAllBookStatus() {
        for (Book book : bookMemory.values()) {
            if (book.isOrganized()) {
                book.available();
            }
        }
        csvFileManager.saveMemoryToCsv(bookMemory);
    }

    @Override
    public int generateBookNumber() {
        int max = bookMemory.keySet().stream().max(Integer::compareTo).orElse(0);
        for (int i = 1; i <= max; i++) {
            if (!bookMemory.containsKey(i)) {
                return i;
            }
        }
        return max + 1;
    }
}
