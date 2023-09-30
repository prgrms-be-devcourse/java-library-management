package org.example.server.service;

import org.example.server.entity.Book;
import org.example.server.entity.BookState;
import org.example.server.repository.Repository;

import java.util.Calendar;
import java.util.Date;

/* Q. 조건이 맞지 않아 요청이 실패할 경우, ServerException이라는 커스텀 예외 안에 메시지를 넣고 던지는 로직을 구현했습니다.
    위 방식으로하면 if문보다는 깔끔해지는 것 같지만 예외를 일부로 직접 만들어서 던지는 것이 조금은 안정적이지 않은 것 같다는 생각이 들기도 합니다.
    멘토님들은 어떻게 생각하시는지 궁금합니다! */

// 서버의 서비스 레이어를 담당하는 부분
public class BookService implements Service {
    private final Repository repository; // 외부에서 repository 의존성 주입

    public BookService(Repository repository) {
        this.repository = repository;
    }

    public void register(String name, String author, int pages) {
        Book book = new Book(name, author, pages);
        repository.create(book);
        repository.save();
    }

    public String readAll() {
        return repository.readAll();
    }

    public String searchByName(String bookName) {
        return repository.searchByName(bookName);
    }

    public void borrow(int bookId) {
        Book book = repository.getById(bookId);
        BookState bookState = BookState.valueOf(book.state);
        if (bookState.equals(BookState.BORROWED) || bookState.equals(BookState.LOADING) || bookState.equals(BookState.LOST))
            throw bookState.throwStatusException();
        book.state = BookState.BORROWED.name();
        repository.save();
    }

    public void restore(int bookId) {
        Book book = repository.getById(bookId);
        BookState bookState = BookState.valueOf(book.state);
        if (bookState.equals(BookState.CAN_BORROW) || bookState.equals(BookState.LOADING))
            throw bookState.throwStatusException();
        book.state = BookState.LOADING.name();
        // 도서 정리 완료 예정 시간 계산 후 저장
        Date curr = new Date();
        Book.calendar.setTime(curr);
        Book.calendar.add(Calendar.MINUTE, 5);
        book.endLoadTime = Book.format.format(new Date(Book.calendar.getTimeInMillis()));
        repository.save();
    }

    public void lost(int bookId) {
        Book book = repository.getById(bookId);
        BookState bookState = BookState.valueOf(book.state);
        if (bookState.equals(BookState.LOST))
            throw bookState.throwStatusException();
        if (bookState.equals(BookState.LOADING)) {
            book.endLoadTime = "";
        }
        book.state = BookState.LOST.name();
        repository.save();
    }

    public void delete(int bookId) {
        repository.getById(bookId);
        repository.delete(bookId);
        repository.save();
    }
}
