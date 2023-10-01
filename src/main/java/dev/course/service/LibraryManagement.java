package dev.course.service;

import dev.course.config.LibraryConfig;
import dev.course.domain.Book;
import dev.course.domain.BookState;
import dev.course.exception.FuncFailureException;
import dev.course.manager.ConsoleManager;
import dev.course.repository.BookRepository;
import lombok.Builder;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LibraryManagement {

    private final BookRepository bookRepository;
    private final ConsoleManager consoleManager;

    @Builder
    public LibraryManagement(LibraryConfig libraryConfig) {
        this.bookRepository = libraryConfig.getBookRepositoryConfig().getBookRepository();
        this.consoleManager = libraryConfig.getConsoleManager();
    }


    public void add(Book book) {

        this.bookRepository.add(book);
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    public void getAll() {

        List<Book> bookList = this.bookRepository.getAll();
        bookList.forEach(this.bookRepository::printBook);
        System.out.println("[System] 도서 목록 조회가 완료되었습니다.\n");
    }

    public void findByTitle(String title) throws FuncFailureException {

        List<Book> searched = this.bookRepository.findByTitle(title);
        searched.forEach(this.bookRepository::printBook);
        System.out.println("[System] 도서 검색 조회가 완료되었습니다.\n");
    }

    public void borrow(Long bookId) throws FuncFailureException {

        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));

        if (book.getState().equals(BookState.RENTAL_AVAILABLE)) {
            Book rented = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.RENTING);
            this.bookRepository.add(rented);
        } else if (book.getState().equals(BookState.RENTING)) {
            throw new FuncFailureException("[System] 대여중인 도서로 대여가 불가합니다.\n");
        } else if (book.getState().equals(BookState.ARRANGEMENT)) {
            throw new FuncFailureException("[System] 정리중인 도서로 대여가 불가합니다.\n");
        } else {
            throw new FuncFailureException("[System] 분실된 도서로 대여가 불가합니다..\n");
        }

        System.out.println("[System] 도서 대여가 완료되었습니다.\n");
    }

    public void returns(Long bookId) throws FuncFailureException {

        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));
        if (book.getState().equals(BookState.RENTAL_AVAILABLE)) {
            throw new FuncFailureException("[System] 대여 가능한 도서로 반납이 불가합니다.\n");
        } else if (book.getState().equals(BookState.RENTING) || book.getState().equals(BookState.LOST)) {
            Book returned = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.ARRANGEMENT);
            this.bookRepository.add(returned);
            afterDelayArrangementComplete(returned, 300000);
        } else {
            throw new FuncFailureException("[System] 정리중인 도서로 반납이 불가합니다.\n");
        }

        System.out.println("[System] 도서 반납 처리가 완료되었습니다.\n");
    }

    public void lost(Long bookId) throws FuncFailureException {

        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));

        if (book.getState().equals(BookState.LOST)) {
            throw new FuncFailureException("[System] 이미 분실된 도서입니다.\n");
        } else {
            Book lost = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.LOST);
            this.bookRepository.add(lost);
        }

        System.out.println("[System] 도서 분실 처리가 완료되었습니다.\n");
    }

    public void delete(Long bookId) throws FuncFailureException {

        Book book = this.bookRepository.findById(bookId)
                .orElseThrow(() -> new FuncFailureException("[System] 해당 도서는 존재하지 않습니다.\n"));

        this.bookRepository.delete(book.getBookId());

        System.out.println("[System] 도서 삭제 처리가 완료되었습니다.\n");
    }

    public void afterDelayArrangementComplete(Book book, long delay) {

        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        service.schedule(() -> {
            Book arranged = new Book(book.getBookId(), book.getTitle(), book.getAuthor(), book.getPage_num(), BookState.RENTAL_AVAILABLE);
            this.bookRepository.add(arranged);
        }, delay, TimeUnit.MILLISECONDS);
    }

    public Book createBook(String title, String author, int pageNum) {
        return new Book(this.bookRepository.createBookId(), title, author, pageNum, BookState.RENTAL_AVAILABLE);
    }
}
