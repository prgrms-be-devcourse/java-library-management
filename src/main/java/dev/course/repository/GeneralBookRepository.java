package dev.course.repository;

import dev.course.domain.Book;
import dev.course.domain.BookState;
import dev.course.exception.FuncFailureException;
import dev.course.manager.JSONFileManager;

import java.util.*;
import java.util.stream.Collectors;

public class GeneralBookRepository implements BookRepository {

    private final String filePath;
    private final Map<Long, Book> storage;
    private final JSONFileManager fileManager;

    public GeneralBookRepository(JSONFileManager fileManager, String filePath) {
        this.fileManager = fileManager;
        this.filePath = filePath;
        this.storage = load();
    }

    private Map<Long, Book> load() {

        Map<Long, Book> bookList = new TreeMap<>();

        if (fileManager != null) {
            fileManager.readFile(filePath, (book) ->
                    bookList.put(book.getBookId(), book));
        }

        return bookList;
    }

    @Override
    public Long createBookId() {

        Long bookId = 1L;

        List<Book> bookList = getAll();
        for (Book book : bookList) {
            if (book.getBookId().equals(bookId)) bookId++;
        }
        return bookId;
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return Optional.ofNullable(storage.get(bookId));
    }

    @Override
    public void add(Book obj) {
        storage.put(obj.getBookId(), obj);
        update();
    }

    @Override
    public void delete(Long bookId) {

        if (storage.remove(bookId) == null) {
            throw new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n");
        }
        update();
    }

    @Override
    public void update(Book obj) {

        storage.put(obj.getBookId(), obj);
        update();
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Book> findByTitle(String title) {
        return storage.values().stream().filter(elem ->
                elem.getTitle().contains(title))
                .collect(Collectors.toList());
    }


    // map 에 저장된 내용을 filePath 에 있는 JSON 파일에 업데이트함
    public void update() {
        if (fileManager != null) {
            fileManager.writeFile(storage, filePath);
        }
    }

    @Override
    public int getSize() {
        return this.storage.size();
    }
}
