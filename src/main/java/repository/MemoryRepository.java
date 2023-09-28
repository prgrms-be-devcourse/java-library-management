package repository;

import domain.Books;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemoryRepository implements Repository{
    private static final List<Books> bookList = new ArrayList<>();

    @Override
    public void addBook(Books book) {
        bookList.add(book);
    }

    @Override
    public void deleteBook(Books book) {
        bookList.remove(book);
    }

    @Override
    public Optional<Books> findById(Long bookNo) {
        return bookList.stream().filter(book -> book.getBookNo().equals(bookNo)).findAny();
    }

    @Override
    public List<Books> findByTitle(String title) {
        return bookList.stream().filter(book -> book.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public List<Books> bookList() {
        return this.bookList;
    }
}
