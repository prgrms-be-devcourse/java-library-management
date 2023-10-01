package repository;

import domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestRepository implements BookRepository {
    private List<Book> bookList = new ArrayList<>();
    @Override
    public void register(Book book) {
        bookList.add(book);
    }

    @Override
    public List<Book> getBookList() {
        return bookList;
    }

    @Override
    public List<Book> findByTitle(String title) {
        return bookList.stream().filter(book -> book.getTitle().contains(title)).collect(Collectors.toList());
    }

    @Override
    public void borrow(Book book) {
        book.borrow();
    }

    @Override
    public void returnBook(Book book) {
        book.doReturn();
    }

    @Override
    public void report(Book book) {
        book.report();
    }

    @Override
    public void remove(Book book) {
        bookList.remove(book);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        for (Book book: bookList){
            if (Objects.equals(id, book.getId())){
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    @Override
    public Integer createId() {
        if (bookList.isEmpty()) return 1;
        return bookList.get(bookList.size()-1).getId()+1;
    }
}
