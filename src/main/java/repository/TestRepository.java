package repository;

import domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestRepository implements Repository{

    private final List<Book> bookRepository;

    public TestRepository(){
        bookRepository = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) {
        int size = bookRepository.size();
        Long id = size>0?bookRepository.get(size-1).getId()+1:0;
        book.setId(id);
        bookRepository.add(book);
    }

    @Override
    public List<Book> getAll() {
        return new ArrayList<>(bookRepository);
    }

    @Override
    public List<Book> searchBook(String name) {
        return bookRepository.stream()
                .filter(book -> book.containsName(name))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> getBook(Long bookNumber) {
        return bookRepository.stream()
                .filter(book -> book.equalsId(bookNumber))
                .findFirst();
    }

    @Override
    public void deleteBook(Book book){
        bookRepository.remove(book);
    }

    @Override
    public void rentalBook(Book book) {
        book.rentalBook();
    }

    @Override
    public void organizeBook(Book book) {
        book.organizeBook();
        returnBook(book);
    }

    @Override
    public void returnBook(Book book) {
        book.returnBook();
    }

    @Override
    public void lostBook(Book book) {
        book.lostBook();
    }
}
