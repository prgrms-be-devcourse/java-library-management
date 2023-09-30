package controller;

import constant.Function;
import constant.Guide;
import constant.Mode;
import constant.Question;
import io.Input;
import io.Output;
import repository.FileRepository;
import repository.MemoryRepository;
import service.BookService;


public class BookController {
    private BookService bookService;
    private final Input input;
    private final Output output;
    private final String MODE = "MODE";
    private final String FUNCTION = "FUNCTION";

    public BookController(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public boolean chooseMode() {
        output.printSelection(MODE);
        try {
            int inputMode = input.inputNumber();
            switch (Mode.chosenMode(inputMode)) {
                case NORMAL_MODE -> {
                    output.printGuide(Guide.START_NORMAL_MODE);
                    this.bookService = new BookService(new FileRepository("book.csv"));
                }
                case TEST_MODE -> {
                    output.printGuide(Guide.START_TEST_MODE);
                    this.bookService = new BookService(new MemoryRepository());
                }
            }
            runApplication();
        } catch (Exception e) {
            output.printException(e.getMessage());
            return false;
        }
        return true;
    }

    public void runApplication() {
        int functionIdx = -1;
        while (functionIdx != 0) {
            output.printSelection(FUNCTION);
            {
                try {
                    functionIdx = input.inputNumber();
                    switch (Function.getFunctionByIdx(functionIdx)) {
                        case SYSTEM_END -> output.printGuide(Guide.SYSTEM_END);
                        case SAVE -> saveBook();
                        case FIND_ALL -> findAllBook();
                        case FIND_BY_TITLE -> findBooksByTitle();
                        case BORROW -> borrowBook();
                        case RETURN -> returnBook();
                        case LOST -> lostBook();
                        case DELETE -> deleteBook();
                    }
                } catch (Exception e) {
                    output.printException(e.getMessage());
                }
            }
        }
        System.exit(0);
    }

    public void saveBook() {
        output.printGuide(Guide.REGISTER_START);

        output.printQuestion(Question.REGISTER_TITLE);
        String title = input.inputString();

        output.printQuestion(Question.REGISTER_AUTHOR);
        String author = input.inputString();

        output.printQuestion(Question.REGISTER_PAGE_NUM);
        int pageNum = input.inputNumber();

        bookService.saveBook(title, author, pageNum);
        output.printGuide(Guide.REGISTER_END);
    }

    public void findAllBook() {
        output.printGuide(Guide.FIND_ALL_START);
        output.printBooks(bookService.findAllBook());
        output.printGuide(Guide.FIND_ALL_END);
    }

    public void findBooksByTitle() {
        output.printGuide(Guide.FIND_BY_TITLE_START);
        output.printQuestion(Question.FIND_BY_TITLE);
        String title = input.inputString();
        output.printBooks(bookService.findBooksByTitle(title));
        output.printGuide(Guide.FIND_BY_TITLE_END);
    }

    public void borrowBook() {
        output.printGuide(Guide.BORROW_START);
        output.printQuestion(Question.BORROW_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.borrowBookByBookNo(bookNo);
        output.printGuide(Guide.BORROW_COMPLETE);
    }

    public void returnBook() {
        output.printGuide(Guide.RETURN_START);
        output.printQuestion(Question.RETURN_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.returnBookByBookNo(bookNo, 300000);
        output.printGuide(Guide.RETURN_COMPLETE);
    }

    public void lostBook() {
        output.printGuide(Guide.LOST_START);
        output.printQuestion(Question.LOST_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.lostBookByBookNo(bookNo);
        output.printGuide(Guide.LOST_COMPLETE);
    }

    public void deleteBook() {
        output.printGuide(Guide.DELETE_START);
        output.printQuestion(Question.DELETE_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.deleteBookByBookNo(bookNo);
        output.printGuide(Guide.DELETE_COMPLETE);
    }
}
