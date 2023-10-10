package org.library.utils;

import java.util.Arrays;
import java.util.List;
import org.library.domain.Func;
import org.library.domain.Message;
import org.library.dto.BookDto;
import org.library.entity.Book;

public class ConsoleManager {

    private ConsoleInputManager consoleInputManager;

    public ConsoleManager(ConsoleInputManager consoleInputManager) {
        this.consoleInputManager = consoleInputManager;
    }

    public BookDto register() {
        System.out.println(Message.START_REGISTER.getMessage());
        System.out.println(Message.INPUT_REGISTER_TITLE.getMessage());
        String title = consoleInputManager.inputString();
        System.out.println(Message.INPUT_REGISTER_AUTHOR.getMessage());
        String author = consoleInputManager.inputString();
        System.out.println(Message.INPUT_REGISTER_PAGE.getMessage());
        int page = consoleInputManager.inputInt();
        return new BookDto(title, author, page);
    }

    public void showAll(List<Book> bookList) {
        System.out.println(Message.START_FIND_ALL.getMessage());
        printBookList(bookList);
        System.out.println(Message.END_FIND_ALL.getMessage());
    }

    public String inputTitle() {
        System.out.println(Message.START_FIND_BY_TITLE.getMessage());
        System.out.println(Message.INPUT_FIND_TITLE.getMessage());
        return consoleInputManager.inputString();
    }

    public void printBookList(List<Book> bookList) {
        bookList.forEach(book -> System.out.println(book.toString()));
    }

    public long inputId() {
        return consoleInputManager.inputLong();
    }

    public Long rent() {
        System.out.println(Message.START_RENT.getMessage());
        System.out.println(Message.INPUT_RENT_ID.getMessage());
        return inputId();
    }

    public Long returns() {
        System.out.println(Message.START_RETURNS.getMessage());
        System.out.println(Message.INPUT_RETURNS_ID.getMessage());
        return inputId();
    }

    public Long reportLost() {
        System.out.println(Message.START_REPORT_LOST.getMessage());
        System.out.println(Message.INPUT_REPORT_LOST_ID.getMessage());
        return inputId();
    }

    public Long delete() {
        System.out.println(Message.START_DELETE.getMessage());
        System.out.println(Message.INPUT_DELETE_ID.getMessage());
        return inputId();
    }

    public int inputFunctionNum() {
        System.out.println(Message.INPUT_USE_FUNCTION.getMessage());
        Arrays.stream(Func.values()).forEach(func -> System.out.println(func.toString()));
        return consoleInputManager.inputInt();
    }

    public void printResult(String result) {
        System.out.println(result);
    }
}