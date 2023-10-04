package org.library.repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.library.entity.Book;
import org.library.exception.NotExistException;
import org.library.utils.JsonManager;

public class ApplicationRepository implements Repository {

    private static final Map<Long, Book> bookMap = new ConcurrentHashMap<>();
    private final JsonManager jsonManager;

    public ApplicationRepository(String path) {
        jsonManager = new JsonManager(path);
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
        if (exists(book)) {
            edit(book);
        } else {
            add(book);
        }
        flush();
    }

    @Override
    public List<Book> findAll() {
        return bookMap.values().stream().sorted(Comparator.comparingLong(Book::getId)).toList();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookMap.values().stream().filter(b -> b.getTitle().contains(title))
            .sorted(Comparator.comparingLong(Book::getId))
            .toList();
    }

    @Override
    public Book findById(Long id) {
        return bookMap.values().stream().filter(b -> b.getId().equals(id))
            .findAny()
            .orElseThrow();
    }

    @Override
    public void delete(Book book) {
        if (!bookMap.containsKey(book.getId())) {
            throw new NotExistException();
        }
        bookMap.remove(book.getId());
        flush();

    }

    public boolean exists(Book book) {
        return bookMap.values().stream().anyMatch(b -> b.getId().equals(book.getId()));
    }

    public void edit(Book targetBook) {
        for (Long id : bookMap.keySet()) {
            if (targetBook.getId().equals(id)) {
                Book book = findById(id);
                book = targetBook;
                break;
            }
        }
    }

    public void add(Book targetBook) {
        bookMap.put(targetBook.getId(), targetBook);
    }

    @Override
    public void flush() {
        jsonManager.write(findAll());
    }
}
