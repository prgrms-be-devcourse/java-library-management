package com.programmers.librarymanagement.application;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.domain.Status;
import com.programmers.librarymanagement.exception.BookNotFoundException;
import com.programmers.librarymanagement.repository.BookRepository;

import java.time.LocalDateTime;
import java.util.List;

public class LibraryManagementService {

    private final BookRepository bookRepository;

    public LibraryManagementService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(String title, String author, int page) {

        Book book = new Book(bookRepository.createId(), title, author, page, Status.CAN_RENT, LocalDateTime.now());
        bookRepository.addBook(book);
    }

    public void getAllBooks() {

        updateAllStatus();

        List<Book> bookList = bookRepository.findAll();
        printBookInfo(bookList);
    }

    public void getBookByTitle(String title) {

        List<Book> bookList = bookRepository.findByTitle(title);
        printBookInfo(bookList);
    }

    public String rentBook(Long id) {

        String result = "[System] 도서가 대여 처리 되었습니다. \n";

        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        // 도서가 대여 가능할 경우, 대여중으로 상태 변경
        switch (book.getStatus()) {
            case CAN_RENT -> {
                Book rentBook = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), Status.CANNOT_RENT, book.getReturnDateTime());
                bookRepository.updateBook(rentBook);
            }

            case CANNOT_RENT -> result = "[System] 이미 대여중인 도서입니다. \n";

            case ARRANGE -> {
                if (book.getReturnDateTime().plusMinutes(5).isAfter(LocalDateTime.now())) {
                    Book arrangeBook = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), Status.CANNOT_RENT, book.getReturnDateTime());
                    bookRepository.updateBook(arrangeBook);
                } else {
                    result = "[System] 정리 중인 도서입니다. \n";
                }
            }

            case LOST -> result = "[System] 분실된 도서입니다. \n";
        }

        return result;
    }

    public String returnBook(Long id) {

        String result = "[System] 도서가 반납 처리 되었습니다. \n";

        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        // 도서가 반납 가능할 경우, 정리중으로 상태 변경
        switch (book.getStatus()) {
            case CAN_RENT -> result = "[System] 원래 대여가 가능한 도서입니다. \n";

            case CANNOT_RENT, LOST -> {
                Book rentBook = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), Status.ARRANGE, LocalDateTime.now());
                bookRepository.updateBook(rentBook);
            }

            case ARRANGE -> {

                // 도서 반납 후 5분이 지났다면 대여 가능
                if (book.getReturnDateTime().plusMinutes(5).isBefore(LocalDateTime.now())) {
                    result = "[System] 원래 대여가 가능한 도서입니다. \n";
                } else {
                    result = "[System] 정리 중인 도서입니다. \n";
                }
            }
        }

        return result;
    }

    public String lostBook(Long id) {

        String result = "[System] 도서가 분실 처리 되었습니다. \n";

        Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);

        // 도서가 분실 처리 가능할 경우, 분실됨으로 상태 변경
        switch (book.getStatus()) {
            case CAN_RENT, CANNOT_RENT, ARRANGE -> {
                Book missBook = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), Status.LOST, book.getReturnDateTime());
                bookRepository.updateBook(missBook);
            }

            case LOST -> result = "[System] 이미 분실 처리된 도서입니다. \n";
        }

        return result;
    }

    public String deleteBook(Long id) {

        String result = "[System] 도서가 삭제 처리 되었습니다. \n";

        try {
            Book book = bookRepository.findById(id).orElseThrow(BookNotFoundException::new);
            bookRepository.deleteBook(book);
        } catch (BookNotFoundException e) {
            result = "[System] 존재하지 않는 도서번호 입니다. \n";
        }

        return result;
    }

    private void printBookInfo(List<Book> bookList) {

        for (Book book : bookList) {
            System.out.println("도서번호 : " + book.getId() + "\n"
                    + "제목 : " + book.getTitle() + "\n"
                    + "작가 이름 : " + book.getAuthor() + "\n"
                    + "페이지 수 : " + book.getPage() + " 페이지 \n"
                    + "상태 : " + book.getStatus().getValue() + "\n");

            System.out.println("------------------------------ \n");
        }
    }

    private void updateAllStatus() {

        List<Book> bookList = bookRepository.findAll();
        for (Book book : bookList) {

            // 도서 반납 후 5분이 지났다면 대여 가능으로 상태 변경
            if ((book.getStatus() == Status.ARRANGE) && (book.getReturnDateTime().plusMinutes(5).isBefore(LocalDateTime.now()))) {

                Book statusBook = new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), Status.CAN_RENT, book.getReturnDateTime());
                bookRepository.updateBook(statusBook);
            }
        }
    }
}
