package library.view.function;

import library.controller.BookController;
import library.dto.BookFindResponse;
import library.dto.BookSaveRequest;
import library.repository.BookRepository;
import library.service.BookService;
import library.view.console.ConsoleIOHandler;

import java.util.List;

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
}

