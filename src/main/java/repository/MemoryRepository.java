package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MemoryRepository implements Repository {
    private final List<Book> books = new ArrayList<>();

    @Override
    public void saveBook(Book inputBook) {
        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            if (Objects.equals(book.getBookNo(), inputBook.getBookNo())) {
                books.set(i, inputBook);
                return;
            }
        }
        books.add(inputBook);
    }

    @Override
    public List<Book> findAllBook() {
        return books;
    }

    @Override
    public List<Book> findBookByTitle(String searchTitle) {
        return books.stream()
                .filter(book -> book.isTitleContaining(searchTitle))
                .toList();
    }

    @Override
    public void deleteBook(Long bookNo) {
        this.books.removeIf(book -> book.isBookNo(bookNo));
    }

    @Override
    public Long createBookNo() {
        Optional<Long> maxBookNo = books.stream()
                .map(Book::getBookNo)
                .max(Long::compareTo);
        return maxBookNo.map(aLong -> aLong + 1).orElse(1L);
    }

    @Override
    public Optional<Book> findBookByBookNo(Long bookNo) {
        return books.stream()
                .filter(book -> book.isBookNo(bookNo))
                .findFirst();
    }
}
