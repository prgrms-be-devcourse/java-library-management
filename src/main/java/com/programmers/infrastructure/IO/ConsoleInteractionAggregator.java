package com.programmers.infrastructure.IO;

import com.programmers.domain.dto.RegisterBookReq;
import com.programmers.util.Messages;
import java.util.List;

public class ConsoleInteractionAggregator {

    private final Console console;

    public ConsoleInteractionAggregator(Console console) {
        this.console = console;
    }

    public String collectModeInput() {
        return collectInput(Messages.SELECT_MODE.getMessage());
    }

    public String collectMenuInput() {
        return collectInput(Messages.SELECT_MENU.getMessage());
    }

    public RegisterBookReq collectBookInfoInput() {
        console.displayMessage(Messages.BOOK_REGISTER_TITLE.getMessage());
        String title = console.collectUserInput();
        console.displayMessage(Messages.BOOK_REGISTER_AUTHOR.getMessage());
        String author = console.collectUserInput();
        console.displayMessage(Messages.BOOK_REGISTER_PAGES.getMessage());
        int pages = console.collectUserIntegerInput();
        return RegisterBookReq.from(title, author, pages);
    }

    public String collectSearchInput() {
        return collectInput(Messages.BOOK_SEARCH_TITLE.getMessage());
    }

    public Long collectDeleteInput() {
        return collectLongInput(Messages.BOOK_DELETE_ID.getMessage());
    }

    public Long collectRentInput() {
        return collectLongInput(Messages.BOOK_RENT_ID.getMessage());
    }

    public Long collectReturnInput() {
        return collectLongInput(Messages.BOOK_RETURN_ID.getMessage());
    }

    public Long collectLostInput() {
        return collectLongInput(Messages.BOOK_LOST_ID.getMessage());
    }

    public String collectExitInput() {
        return collectInput(Messages.EXIT_PROMPT.getMessage());
    }

    private String collectInput(String question) {
        console.displayMessage(question);
        return console.collectUserInput();
    }

    private Long collectLongInput(String question) {
        console.displayMessage(question);
        return console.collectUserLongInput();
    }

    public <T> void displayListInfo(List<T> ListItems) {
        ListItems.forEach(item -> displayMessage(item.toString()));
    }

    public <T> void displaySingleInfo(T item) {
        displayMessage(item.toString());
    }

    public void displayMessage(String message) {
        console.displayMessage(message);
    }
}
