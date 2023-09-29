package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.repository.Repository;

import java.util.Calendar;
import java.util.Date;

/*
 * 이때 도서가 반납되면 도서의 상태는 '도서 정리중' 상태로 바뀌어야합니다.
 * 그리고 '도서 정리중' 상태에서 5분이 지난 도서는 '대여 가능'으로 바뀌어야합니다.
 * (대여된 후 5분이 지난 도서를 누군가 대여하려고 했을 때 대여 처리가 되어야한다는 겁니다)
 */
public class BookService implements Service {
    private final Repository repository; // 외부에서 repository 의존성 주입

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void register(String name, String author, int pages) {
        Book book = new Book(name, author, pages);
        repository.create(book);
    }

    public String readAll() {
        return repository.readAll();
    }

    public String searchByName(String bookName) {
        return repository.searchByName(bookName);
    }

    public void borrow(int bookId) {
        Book book = repository.getById(bookId);
        if (BookState.valueOf(book.state).equals(BookState.BORROWED) || BookState.valueOf(book.state).equals(BookState.LOADING) || BookState.valueOf(book.state).equals(BookState.LOST))
            throw BookState.valueOf(book.state).throwStatusException();
        book.state = BookState.BORROWED.name(); // save
    }

    public void restore(int bookId) {
        Book book = repository.getById(bookId);
        if (BookState.valueOf(book.state).equals(BookState.CAN_BORROW) || BookState.valueOf(book.state).equals(BookState.LOADING))
            throw BookState.valueOf(book.state).throwStatusException();
        book.state = BookState.LOADING.name();
        Date curr = new Date();
        Book.calendar.setTime(curr);
        Book.calendar.add(Calendar.MINUTE, 5);
        book.endLoadTime = Book.format.format(new Date(Book.calendar.getTimeInMillis()));
    } // save

    public void lost(int bookId) {
        Book book = repository.getById(bookId);
        if (BookState.valueOf(book.state).equals(BookState.LOADING) || BookState.valueOf(book.state).equals(BookState.LOST))
            throw BookState.valueOf(book.state).throwStatusException();
        book.state = BookState.LOST.name(); // save
    }

    public void delete(int bookId) {
        Book book = repository.getById(bookId);
        repository.delete(bookId);
    }
}
