package java_library_management.repository.mock;

import java_library_management.domain.Book;
import java_library_management.domain.BookState;
import java_library_management.exception.FuncFailureException;
import java_library_management.repository.Mode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class MockMode implements Mode {

    private final List<Book> searched = new ArrayList<>();

    @Override
    public int getBookId(Map<Integer, Book> map) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Book findById(Map<Integer, Book> map, int book_id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Map<Integer, Book> map, Book obj) {
        map.put(obj.getBook_id(), obj);
    }

    @Override
    public Book register(Map<Integer, Book> map, String title, String author, int page_num) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void borrow(Map<Integer, Book> map, int book_id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void lost(Map<Integer, Book> map, int book_id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(Map<Integer, Book> map, int book_id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void returns(Map<Integer, Book> map, int book_id, String filePath, long delay, BiConsumer<Map<Integer, Book>, String> biConsumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void callback(Map<Integer, Book> map, Book book, String filePath, long delay, BookState state, BiConsumer<Map<Integer, Book>, String> biConsumer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void get(Book obj) {
        this.searched.add(obj);
    }

    @Override
    public void getAll(Map<Integer, Book> map) {
        throw new UnsupportedOperationException();
    }

    public List<Book> getSearched() {
        return this.searched;
    }

    @Override
    public void findByTitle(Map<Integer, Book> map, String title) {
        Iterator<Book> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Book elem = iterator.next();
            if (elem.getTitle().contains(title)) {
                get(elem);
            }
        }
    }

    @Override
    public void load(Map<Integer, Book> map, String filePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(Map<Integer, Book> map, String filePath) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void printStartMsg() {
        throw new UnsupportedOperationException();
    }
}
