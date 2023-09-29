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
        int size = bookRepository.size();
        Long id = size>0?bookRepository.get(size-1).getId()+1:0;
        book.setId(id);
        bookRepository.add(book);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository;
    }

    @Override
    public List<Book> searchBook(String name) {
        List<Book> searchResult = new ArrayList<>();
        bookRepository.stream()
                .filter(book->book.getName().contains(name))
                .forEach(searchResult::add);
        return searchResult;
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
