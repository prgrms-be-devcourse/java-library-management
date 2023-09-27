package service;

import exception.*;
import model.Book;
import model.Status;
import repository.Repository;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookService {

    private final Repository repository;
    public BookService(Repository repository) {
        this.repository = repository;
    }

    Timer timer = new Timer(true);

    // 도서 등록
    public void saveBook(String title, String author, int pageNum) {
        Book book = new Book(repository.createBookNo(), title, author, pageNum, Status.AVAILABLE);
        repository.saveBook(book);
    }

    // 도서 전체 목록 조회
    public List<Book> findAllBook() {
        return repository.findAllBook();
    }

    // 특정 도서 제목으로 조회
    public List<Book>  findBooksByTitle(String title) {
        return repository.findBookByTitle(title);
    }

    // 도서 대여
    public void borrowBookByBookNo(Long bookNo) throws Exception {
        Book book = repository.findBookByBookNo(bookNo)
                .orElseThrow(BookNotExistException::new);

        switch (book.getStatus()) {
            case BORROWED -> throw new BookBorrowedException();
            case LOST -> throw new BookLostException();
            case ORGANIZING -> throw new BookOrganizingException();
        }
        repository.borrowBook(book);
    }

    // 도서 반납
    public void returnBookByBookNo(Long bookNo) throws BookNotExistException {
        Book book = repository.findBookByBookNo(bookNo)
                .orElseThrow(BookNotExistException::new);

        switch (book.getStatus()) {
            case AVAILABLE, ORGANIZING -> throw new BookReturnFailException();
        }
        repository.returnBook(book);
        scheduleTask(book);
    }

    // 도서 분실
    public void lostBookByBookNo(Long bookNo) throws BookNotExistException, BookAlreadyLostException {
        Book book = repository.findBookByBookNo(bookNo)
                .orElseThrow(BookNotExistException::new);
        if (Status.isLost(book.getStatus())) {
            throw new BookAlreadyLostException();
        }
        repository.lostBook(book);
    }

    // 도서 삭제
    public void deleteBookByBookNo(Long bookNo) throws BookNotExistException {
        Book book = repository.findBookByBookNo(bookNo)
                .orElseThrow(BookNotExistException::new);
        repository.deleteBook(bookNo);
    }

    private void scheduleTask(Book book) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                book.changeStatus(Status.AVAILABLE);
                repository.saveBook(book);
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(timerTask, 300000);
    }
}
