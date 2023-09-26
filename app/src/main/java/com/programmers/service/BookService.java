package com.programmers.service;

import com.programmers.common.Messages;
import com.programmers.domain.Book;
import com.programmers.repository.BookRepository;

import java.util.Optional;
import java.util.Scanner;

public class BookService {
    private static BookRepository bookRepository;

    private BookService() {
    }

    private static class Holder {
        private static final BookService INSTANCE = new BookService();
    }

    public static BookService getInstance() {
        Optional.ofNullable(bookRepository).orElseThrow(() -> new IllegalArgumentException());
        return BookService.Holder.INSTANCE;
    }

    public static void setBookRepository(BookRepository _bookRepository) {
        bookRepository = _bookRepository;
    }

    public void registerBook() {
        System.out.println(Messages.MOVE_TO_BOOK_REGISTER);
        bookRepository.addBook(new Book());
    }

    public void getAllBooks() {
        System.out.println(Messages.BOOK_LIST_START);
        bookRepository.getAllBooks().stream().forEach(book -> toString());
        System.out.println(Messages.BOOK_LIST_FINISH);
    }

    public void searchBookByTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(Messages.MOVE_TO_BOOK_SEARCH);
        System.out.print(Messages.BOOK_TITLE_PROMPT);
        bookRepository.findBookByTitle(scanner.nextLine()).toString();
    }

    public void rentBook() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println(Messages.MOVE_TO_BOOK_RENT);
//        System.out.println(Messages.BOOK_RENT_PROMPT);
//        bookRepository.updateBookState(scanner.nextInt(), BookState.RENTED);
    }

    public void returnBook() {
        System.out.println(this);


    }

    public void lostBook() {
        System.out.println(this);


    }

    public void deleteBook() {
        System.out.println(this);


    }
}
