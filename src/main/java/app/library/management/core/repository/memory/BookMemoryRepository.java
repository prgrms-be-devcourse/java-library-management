package app.library.management.core.repository.memory;

import app.library.management.core.domain.Book;
import app.library.management.core.repository.BookRepository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class BookMemoryRepository implements BookRepository {
    private final List<Book> bookArrayList = new CopyOnWriteArrayList<>();
    private final AtomicLong nextId = new AtomicLong(0);

    @Override
    public Book save(Book book) {
        book.setId(nextId.getAndIncrement());
        bookArrayList.add(book);
        return book;
    }

    @Override
    public List<Book> findAll() {
        return bookArrayList;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookArrayList.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(long id) {
        return bookArrayList.stream()
                .filter(book -> book.getId() == id)
                .findAny();
    }

    @Override
    public void delete(Book book) {
        bookArrayList.remove(book);
    }

    @Override
    public void update(Book book) {
        /**
         * 메모리 상에는 setter로 변환 가능하므로 따로 update가 필요 없음
         */
    }

}
