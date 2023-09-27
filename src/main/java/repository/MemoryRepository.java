package repository;

import model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MemoryRepository implements Repository {

    List<Book> bookList = new ArrayList<>();

    @Override
    public void saveBook(Book inputBook) {
        for (int i = 0; i < bookList.size(); i++) {
            Book book = bookList.get(i);
            if (Objects.equals(book.getBookNo(), inputBook.getBookNo())) {
                bookList.set(i, inputBook);
                return;
            }
        }
        bookList.add(inputBook);
    }

    @Override
    public List<Book> findAllBook() {
        return bookList;
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return bookList.stream()
                .filter(book -> book.getTitle().contains(title))
                .toList();
    }

    @Override
    public void deleteBook(Long bookNo) {
        this.bookList = bookList.stream()
                .filter(book -> book.getBookNo().equals(bookNo))
                .toList();
    }

    @Override
    public Long createBookNo() {

        Optional<Long> maxBookNo = bookList.stream()
                .map(Book::getBookNo)
                .max(Long::compareTo);
        return maxBookNo.map(aLong -> aLong + 1).orElse(1L);
    }

    @Override
    public Optional<Book> findBookByBookNo(Long bookNo) {
        return bookList.stream()
                .filter(book -> book.getBookNo().equals(bookNo))
                .findFirst();
    }
}
