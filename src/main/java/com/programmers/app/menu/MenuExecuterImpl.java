package com.programmers.app.menu;

import com.programmers.app.book.service.BookService;
import com.programmers.app.console.CommunicationAgent;

public class MenuExecuterImpl implements MenuExecuter {
    private final BookService bookService;
    private final CommunicationAgent communicationAgent;

    public MenuExecuterImpl(BookService bookService, CommunicationAgent communicationAgent) {
        this.bookService = bookService;
        this.communicationAgent = communicationAgent;
    }

    @Override
    public void execute(Menu menu) {
        switch (menu) {
            case REGISTER:
                bookService.register(communicationAgent.instructRegister());
            case EXIT:
                System.exit(0);
        }
    }
}
