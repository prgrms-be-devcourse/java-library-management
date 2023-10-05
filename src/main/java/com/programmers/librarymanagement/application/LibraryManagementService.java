package com.programmers.librarymanagement.application;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.domain.ReturnResult;
import com.programmers.librarymanagement.domain.Status;
import com.programmers.librarymanagement.exception.BookNotFoundException;
import com.programmers.librarymanagement.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;

public class LibraryManagementService {

    private static final int BOOK_ARRANGE_TIME = 5;

    private final BookRepository bookRepository;

    public LibraryManagementService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, String author, int page) {

        Book book = new Book(bookRepository.createId(), title, author, page, Status.CAN_RENT, LocalDateTime.now());
        bookRepository.addBook(book);
    }

    public List<Book> getAllBooks() {

        updateAllStatus();

        return bookRepository.findAll();
    }

    public List<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Status rentBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        Status result = book.getStatus();

        // 도서가 대여 가능할 경우, 대여중으로 상태 변경
        switch (result) {
            case CAN_RENT -> {
                book.updateStatusToAlreadyRent();
                bookRepository.updateBook(book);
            }

            case ARRANGE -> {
                if (book.getReturnDateTime().plusMinutes(BOOK_ARRANGE_TIME).isBefore(LocalDateTime.now())) {
                    book.updateStatusToAlreadyRent();
                    bookRepository.updateBook(book);

                    result = Status.CAN_RENT;
                }
            }
        }

        return result;
    }

    public ReturnResult returnBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        ReturnResult result = ReturnResult.SUCCESS_RETURN;

        // 도서가 반납 가능할 경우, 정리중으로 상태 변경
        switch (book.getStatus()) {
            case CAN_RENT -> result = ReturnResult.ALREADY_RETURN;

            case ALREADY_RENT, LOST -> {
                book.updateStatusToArrange();
                book.updateReturnDateTime();
                bookRepository.updateBook(book);
            }

            case ARRANGE -> {

                // 도서 반납 후 5분이 지났다면 대여 가능
                if (book.getReturnDateTime().plusMinutes(BOOK_ARRANGE_TIME).isBefore(LocalDateTime.now())) {
                    result = ReturnResult.ALREADY_RETURN;
                } else {
                    result = ReturnResult.ARRANGE;
                }
            }
        }

        return result;
    }

    public Boolean lostBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        boolean result = true;

        // 도서가 분실 처리 가능할 경우, 분실됨으로 상태 변경
        switch (book.getStatus()) {
            case CAN_RENT, ALREADY_RENT, ARRANGE -> {
                book.updateStatusToLost();
                bookRepository.updateBook(book);
            }

            case LOST -> result = false;
        }

        return result;
    }

    public void deleteBook(Long id) {

        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
        bookRepository.deleteBook(book);
    }

    private void updateAllStatus() {

        List<Book> bookList = bookRepository.findAll();
        for (Book book : bookList) {

            // 도서 반납 후 5분이 지났다면 대여 가능으로 상태 변경
            if ((book.getStatus() == Status.ARRANGE) && (book.getReturnDateTime().plusMinutes(BOOK_ARRANGE_TIME).isBefore(LocalDateTime.now()))) {

                book.updateStatusToCanRent();
                bookRepository.updateBook(book);
            }
        }
    }
}
