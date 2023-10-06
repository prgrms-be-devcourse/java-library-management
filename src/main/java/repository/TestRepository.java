package repository;

import domain.Book;

import java.util.ArrayList;
import java.util.List;
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
    public Book getBook(Long bookNumber) {
        return bookRepository.stream()
                .filter(book -> book.equalsId(bookNumber))
                .findAny()
                .orElseThrow(() -> new RuntimeException("[System] 존재하지 않는 도서번호 입니다."));
    }

    @Override
    public void deleteBook(Long bookNumber){
        Book book = getBook(bookNumber);
        bookRepository.remove(book);
    }

    @Override
    public void rentalBook(Long bookNumber) {
        Book book = getBook(bookNumber);
        book.rentalBook();
    }

    @Override
    public void organizeBook(Long bookNumber) {
        Book book = getBook(bookNumber);
        book.organizeBook();
        returnBook(bookNumber);
    }

    @Override
    public void returnBook(Long bookNumber) {
        Book book = getBook(bookNumber);
        book.returnBook();
    }

    @Override
    public void lostBook(Long bookNumber) {
        Book book = getBook(bookNumber);
        book.lostBook();
    }
}
