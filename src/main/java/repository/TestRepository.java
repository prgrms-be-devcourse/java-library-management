package repository;

import domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestRepository implements Repository{
    private static final List<Book> bookList = new ArrayList<>();
    // Map

    private static Long bookNoSeq = 1L;

    private void addBookNoSeq(){
        bookNoSeq++;
    }

    @Override
    public void addBook(Book book) {
        book.setId(bookNoSeq);
        addBookNoSeq();
        bookList.add(book);
    }

    @Override
    public void deleteBook(Book book) {
        bookList.remove(book);
    }

    @Override
    public Optional<Book> findById(Long bookNo) {
        return bookList.stream().filter(book -> book.getId().equals(bookNo)).findAny();
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookList.stream().filter(book -> book.getTitle().equals(title)).collect(Collectors.toList());
    }

    @Override
    public List<Book> bookList() {
        return this.bookList;
    }

    @Override
    public void clear(){
        bookList().clear();
    }
}
