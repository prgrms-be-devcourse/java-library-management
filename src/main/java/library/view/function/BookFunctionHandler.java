package library.view.function;

import library.controller.BookController;
import library.dto.BookFindResponse;
import library.dto.BookSaveRequest;
import library.repository.BookRepository;
import library.service.BookService;
import library.view.console.ConsoleIOHandler;

import java.util.List;

import static library.view.constant.InputMessage.*;

public class BookFunctionHandler {

    private final BookController bookController;
    private final ConsoleIOHandler consoleIOHandler;

    public BookFunctionHandler(BookRepository bookRepository, ConsoleIOHandler consoleIOHandler) {
        this.bookController = new BookController(new BookService(bookRepository));
        this.consoleIOHandler = consoleIOHandler;
    }

    public void addBook() {
        BookSaveRequest bookSaveRequest = consoleIOHandler.inputBookInfo();
        bookController.addBook(bookSaveRequest);
    }

    public void searchAll() {
        List<BookFindResponse> allBookList = bookController.findAllBooks();

        consoleIOHandler.printList(allBookList);
    }

    public void searchByTitle() {
        String searchTitle = consoleIOHandler.inputStringWithMessage(SEARCH_BY_TITLE);
        List<BookFindResponse> searchBookList = bookController.findBookLisContainTitle(searchTitle);

        consoleIOHandler.printList(searchBookList);
    }

    public void rentBook() {
        long rentBookNumber = consoleIOHandler.inputLongWithMessage(RENT_BOOK);

        bookController.rentBook(rentBookNumber);
    }

    public void returnBook() {
        long returnBookNumber = consoleIOHandler.inputLongWithMessage(RETURN_BOOK);

        bookController.returnBook(returnBookNumber);
    }

    public void lostBook() {
        long lostBookNumber = consoleIOHandler.inputLongWithMessage(LOST_BOOK);

        bookController.lostBook(lostBookNumber);
    }

    public void deleteBook() {
        long deleteBookNumber = consoleIOHandler.inputLongWithMessage(DELETE_BOOK);

        bookController.deleteBook(deleteBookNumber);
    }
}

