package controller;

import io.Input;
import io.Output;
import repository.Repository;
import service.BookService;

public class BookController {

    private final BookService bookService;
    public BookController(Repository repository) {
        this.bookService = new BookService(repository);
    }

    public void runApplication() {

        Output.printFunctionOptions();
        int function = Input.inputNumber();

        // 도서 등록
        switch (function) {
            case 1 :
                bookService.saveBookToCsv();
                break;
        }
    }
}
