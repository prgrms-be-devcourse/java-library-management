package controller;

import constant.Guide;
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

    public BookController(Input input, Output output) {
        this.input = input;
        this.output = output;
    }

    public boolean chooseMode() {
        output.printModeOptions();
        try {
            int mode = input.inputNumber();
            if (mode == 1) {
                output.printGuide(Guide.START_NORMAL_MODE);
                this.bookService = new BookService(new FileRepository("book.csv"));
            } else if (mode == 2) {
                output.printGuide(Guide.START_TEST_MODE);
                this.bookService = new BookService(new MemoryRepository());
            } else {
                output.printGuide(Guide.WRONG_MODE);
                return false;
            }
            runApplication();
        } catch (Exception e) {
            output.printException(e.getMessage());
        }
        return true;
    }

    public void runApplication() {
        int function = -1;
        do {
            output.printFunctionOptions();
            {
                try {
                    function = input.inputNumber();
                    switch (function) {
                        case 0 -> output.printGuide(Guide.SYSTEM_END);
                        case 1 -> saveBook();
                        case 2 -> findAllBook();
                        case 3 -> findBooksByTitle();
                        case 4 -> borrowBook();
                        case 5 -> returnBook();
                        case 6 -> lostBook();
                        case 7 -> deleteBook();
                    }
                } catch (Exception e) {
                    output.printException(e.getMessage());
                }
            }
        } while (function != 0);
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
        output.printBookList(bookService.findAllBook());
        output.printGuide(Guide.FIND_ALL_END);
    }

    public void findBooksByTitle() {
        output.printGuide(Guide.FIND_BY_TITLE_START);
        output.printQuestion(Question.FIND_BY_TITLE);
        String title = input.inputString();
        output.printBookList(bookService.findBooksByTitle(title));
        output.printGuide(Guide.FIND_BY_TITLE_END);
    }

    public void borrowBook() throws Exception {
        output.printGuide(Guide.BORROW_START);
        output.printQuestion(Question.BORROW_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.borrowBookByBookNo(bookNo);
        output.printGuide(Guide.BORROW_COMPLETE);
    }

    public void returnBook() throws Exception {
        output.printGuide(Guide.RETURN_START);
        output.printQuestion(Question.RETURN_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.returnBookByBookNo(bookNo);
        output.printGuide(Guide.RETURN_COMPLETE);
    }

    public void lostBook() throws Exception {
        output.printGuide(Guide.LOST_START);
        output.printQuestion(Question.LOST_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.lostBookByBookNo(bookNo);
        output.printGuide(Guide.LOST_COMPLETE);
    }

    public void deleteBook() throws Exception {
        output.printGuide(Guide.DELETE_START);
        output.printQuestion(Question.DELETE_BY_BOOK_NO);
        Long bookNo = input.inputLong();
        bookService.deleteBookByBookNo(bookNo);
        output.printGuide(Guide.DELETE_COMPLETE);
    }
}


