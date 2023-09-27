package manage;

import entity.Book;
import entity.State;
import exception.EntityNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ListBookManager implements BookManager {
    private final List<Book> bookList; // 전체 노드 풀
    private final HashMap<Integer, Integer> numToIdx = new HashMap<>(); // {도서번호, idx}

    public ListBookManager() {
        bookList = new ArrayList<>();
    }

    public ListBookManager(List<Book> bookList){
        this.bookList = bookList;
        bookList.stream().forEach(book -> numToIdx.put(book.getNumber(), bookList.size()));
    }

    @Override
    public Book register(Book book) {
        numToIdx.put(book.getNumber(), bookList.size());
        bookList.add(book);
        return book;
    }

    @Override
    public List<Book> searchAll() {
        return bookList.stream().filter(book -> {
            book.isOver5Minutes();
            return !book.isDeleted();
        }).collect(Collectors.toList());
    }

    @Override
    public List<Book> search(String text) {
        return bookList.stream().filter(book -> {
            book.isOver5Minutes();
            return book.hasText(text);
        }).collect(Collectors.toList());
    }

    @Override
    public State rent(int bookNum) {
        Book book = getBook(bookNum);
        if (book.getState() == State.AVAILABLE) {
            book.setState(State.RENTED);
            return State.AVAILABLE;
        }
        return book.getState();
    }

    @Override
    public State returnBook(int bookNum) {
        Book book = getBook(bookNum);
        State initState = book.getState();

        if (book.getState() == State.RENTED){
            book.setState(State.PROCESSING);
            book.setLastReturn(System.currentTimeMillis());
        }
        return initState;
    }

    @Override
    public State lost(int bookNum) {
        Book book = getBook(bookNum);
        State initState = book.getState();

        if (book.getState() != State.LOST && book.getState() != State.DELETED)
            book.setState(State.LOST);

        return initState;
    }

    @Override
    public State delete(int bookNum) {
        Book book = getBook(bookNum);
        State initState = book.getState();

        if (book.getState() != State.DELETED)
            book.setState(State.DELETED);

        return initState;
    }

    private Book getBook(int bookNum) {
        Integer idx = Optional.ofNullable(numToIdx.get(bookNum)).orElseThrow(EntityNotFoundException::new);
        Book book = bookList.get(idx);
        if (book.isDeleted()) throw new EntityNotFoundException();
        book.isOver5Minutes();
        return book;
    }
}
