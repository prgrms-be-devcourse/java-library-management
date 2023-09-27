package repository;

import domain.Book;

import java.util.ArrayList;
import java.util.List;

public class TestRepository implements Repository{

    private List<Book> bookRepository;

    public TestRepository(){
        bookRepository = new ArrayList<>();
    }

    @Override
    public void addBook(Book book) {
        bookRepository.add(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository;
    }

    @Override
    public Book searchBook(String name) {
        return bookRepository.stream()
                .filter(book->book.getName().contains(name))
                .findAny()
                .orElseThrow(() -> new RuntimeException("[System] 존재하지 않습니다"));
    }

    @Override
    public Book getBook(Long bookNumber) {
        return bookRepository.stream()
                .filter(b -> b.getId().equals(bookNumber))
                .findAny()
                .orElseThrow(() -> new RuntimeException("[System] 존재하지 않는 도서번호 입니다."));
    }

    @Override
    public void deleteBook(Long bookNumber){
        Book book = getBook(bookNumber);
        bookRepository.remove(book);
    }
}
