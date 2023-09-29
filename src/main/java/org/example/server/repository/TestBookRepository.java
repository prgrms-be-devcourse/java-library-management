package org.example.server.repository;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.exception.BookNotFoundException;
import org.example.server.exception.EmptyLibraryException;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Optional;

public class TestBookRepository implements Repository {
    private int count;
    private LinkedHashMap<Integer, Book> data;

    public TestBookRepository() {
        loadData();
    }

    @Override
    public void loadData() {
        count = 0; // id 생성자
        data = new LinkedHashMap<>();
    }

    @Override
    public void create(Book book) {
        int bookId = count++;
        book.id = bookId;
        data.put(bookId, book);
    }

    @Override
    public String readAll() {
        if (data.isEmpty())
            throw new EmptyLibraryException();
        StringBuilder sb = new StringBuilder();
        data.values().forEach((book) -> {
            sb.append(checkLoadTime(book));
        }); // 정리 여부 수정 후 append
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public String searchByName(String bookName) {
        StringBuilder sb = new StringBuilder();
        data.values().forEach(this::checkLoadTime); // 정리 여부 수정
        data.values().stream().filter(book -> book.name.contains(bookName)).forEach(sb::append);
        if (sb.isEmpty())
            throw new EmptyLibraryException();
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public Book getById(int bookId) {
        Optional<Book> book = Optional.ofNullable(data.get(bookId));
        if (book.isEmpty())
            throw new BookNotFoundException();
        return checkLoadTime(book.get()); // 정리 여부 수정
    }

    @Override
    public void delete(int bookId) {
        data.remove(bookId);
    }

    private Book checkLoadTime(Book book) {
        if (BookState.valueOf(book.state).equals(BookState.LOADING)) {
            try {
                Date endLoadTime = Book.format.parse(book.endLoadTime);
                Date now = Book.format.parse(Book.format.format(new Date()));
                if (now.after(endLoadTime)) {
                    book.state = BookState.CAN_BORROW.name();
                    book.endLoadTime = "";
                    return book;
                } // save
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
        return book;
    } // 책 꺼내올 때마다 책이 (1. 도서 정리중/2. 반납 시간이 5분 지남)이면 초기화
}
