package service;

import domain.Book;
import exception.AlreadySavedBookException;
import exception.NotExistBookIdException;
import exception.UnchangeableStatusException;
import manager.console.OutputManager;
import repository.BookRepository;

import java.util.List;

public class BookService {
    private final BookRepository repository;
    private final OutputManager outputManager;

    public BookService(BookRepository repository, OutputManager outputManager) {
        this.repository = repository;
        this.outputManager = outputManager;
    }

    // [1] 도서 저장
    public void saveBook(String title, String author, Integer page) {
        Book newBook = new Book(title, author, page);
        for (Book book: repository.findByTitle(newBook.getTitle())) {
            if (book.equals(newBook)){
                throw new AlreadySavedBookException();
            }
        }
        newBook.allocateId(repository.createId());
        repository.register(newBook);
    }

    // [2] 도서 목록 조회
    public void showBookList() {
        List<Book> books = repository.getBooks();
        printBooks(books);
    }

    // [3] 도서 검색
    public void findBookByTitle(String title) {
        List<Book> books = repository.findByTitle(title);
        printBooks(books);
    }

    // [4] 도서 대여
    public void borrowBook(Integer id) {
        Book book = repository.findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()) {
            case BORROWED -> throw new UnchangeableStatusException("이미 대여 중인 도서입니다.");
            case LOST -> throw new UnchangeableStatusException("분실된 도서입니다.");
            case CLEANING -> {
                if (book.isStillCleaning())
                    throw new UnchangeableStatusException("정리 중인 도서입니다.");
                else book.cleaningToAvailable();
            }
        }
        repository.borrow(book);
    }

    // [5] 도서 반납
    public void returnBook(Integer id) {
        Book book = repository.findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()) {
            case AVAILABLE -> throw new UnchangeableStatusException("원래 대여가 가능한 도서입니다.");
            case CLEANING -> {
                if (book.isStillCleaning())
                    throw new UnchangeableStatusException("이미 반납되어 정리 중인 도서입니다.");
                else book.cleaningToAvailable();
            }
        }
        repository.returnBook(book);
    }

    // [6] 분실 처리
    public void reportLostBook(Integer id) {
        Book book = repository.findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()) {
            case AVAILABLE, CLEANING -> throw new UnchangeableStatusException("도서관에서 보관중으로, 분실처리 대상 도서가 아닙니다.");
            case LOST -> throw new UnchangeableStatusException("이미 분실처리된 도서입니다.");
        }
        repository.report(book);
    }

    // [7] 도서 삭제
    public void removeBook(Integer id) {
        Book book = repository.findById(id).orElseThrow(NotExistBookIdException::new);
        repository.remove(book);
    }

    private void printBooks(List<Book> books) {
        for (Book book : books) {
            if (book.isCleaning()) {
                if (!book.isStillCleaning()) book.cleaningToAvailable();
            }
            outputManager.printBookInfo(book);
        }
    }
}
