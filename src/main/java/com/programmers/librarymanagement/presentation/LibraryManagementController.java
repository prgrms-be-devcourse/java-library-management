package com.programmers.librarymanagement.presentation;

import com.programmers.librarymanagement.domain.Book;
import com.programmers.librarymanagement.dto.BookRequestDto;
import com.programmers.librarymanagement.exception.*;
import com.programmers.librarymanagement.utils.ConsoleIo;
import com.programmers.librarymanagement.repository.NormalBookRepository;
import com.programmers.librarymanagement.repository.TestBookRepository;
import com.programmers.librarymanagement.application.LibraryManagementService;

import java.io.IOException;
import java.util.List;

public class LibraryManagementController {

    private final ConsoleIo consoleIo;
    private LibraryManagementService libraryManagementService;

    public LibraryManagementController(ConsoleIo consoleIo) {
        this.consoleIo = consoleIo;
    }

    // 모드 선택
    public void start() {

        consoleIo.printSelectMode();

        try {
            switch (consoleIo.getInputInt()) {
                case 1 -> {
                    consoleIo.printNormalMode();
                    libraryManagementService = new LibraryManagementService(new NormalBookRepository());
                    run();
                }

                case 2 -> {
                    consoleIo.printTestMode();
                    libraryManagementService = new LibraryManagementService(new TestBookRepository());
                    run();
                }

                default -> {
                    consoleIo.printWrongSelect();
                    this.start();
                }
            }
        } catch (IOException e) {
            consoleIo.printIOException();
            this.start();
        }
    }

    // 모드 진입 후 선택 화면
    private void run() {

        consoleIo.printSelectFun();

        try {
            switch (consoleIo.getInputInt()) {
                case 1 -> addBook();

                case 2 -> getAllBooks();

                case 3 -> getBookByTitle();

                case 4 -> rentBook();

                case 5 -> returnBook();

                case 6 -> lostBook();

                case 7 -> deleteBook();

                default -> consoleIo.printWrongSelect();
            }
        } catch (IOException e) {
            consoleIo.printIOException();
        }

        run();
    }

    private void addBook() throws IOException {

        BookRequestDto bookRequestDto = consoleIo.addBookRequest();

        libraryManagementService.addBook(bookRequestDto.getTitle(), bookRequestDto.getAuthor(), bookRequestDto.getPage());

        consoleIo.addBookResponse();
    }

    private void getAllBooks() {

        consoleIo.getAllBooksRequest();

        List<Book> bookList = libraryManagementService.getAllBooks();

        consoleIo.getAllBooksResponse(bookList);
    }

    private void getBookByTitle() throws IOException {

        String title = consoleIo.getBookByTitleRequest();

        List<Book> bookList = libraryManagementService.getBookByTitle(title);

        consoleIo.getBookByTitleResponse(bookList);
    }

    private void rentBook() throws IOException {

        Long id = consoleIo.rentBookRequest();

        try {
            libraryManagementService.rentBook(id);

        } catch (BookNotFoundException e) {
            consoleIo.printBookNotFoundException();
            return;

        } catch (BookAlreadyRentException e) {
            consoleIo.printBookAlreadyRentException();
            return;

        } catch (BookArrangeException e) {
            consoleIo.printBookArrangeException();
            return;

        } catch (BookLostException e) {
            consoleIo.printBookLostException();
            return;
        }

        consoleIo.rentBookResponse();
    }

    private void returnBook() throws IOException {

        Long id = consoleIo.returnBookRequest();

        try {
            libraryManagementService.returnBook(id);

        } catch (BookNotFoundException e) {
            consoleIo.printBookNotFoundException();
            return;

        } catch (BookAlreadyReturnException e) {
            consoleIo.printBookAlreadyReturnException();
            return;

        } catch (BookArrangeException e) {
            consoleIo.printBookArrangeException();
            return;
        }

        consoleIo.returnBookResponse();
    }

    private void lostBook() throws IOException {

        Long id = consoleIo.lostBookRequest();

        try {
            libraryManagementService.lostBook(id);

        } catch (BookNotFoundException e) {
            consoleIo.printBookNotFoundException();
            return;

        } catch (BookAlreadyLostException e) {
            consoleIo.printBookAlreadyLostException();
            return;
        }

        consoleIo.lostBookResponse();
    }

    private void deleteBook() throws IOException {

        Long id = consoleIo.deleteBookRequest();

        try {
            libraryManagementService.deleteBook(id);

        } catch (BookNotFoundException e) {
            consoleIo.printBookNotFoundException();
            return;
        }

        consoleIo.deleteBookResponse();
    }
}
