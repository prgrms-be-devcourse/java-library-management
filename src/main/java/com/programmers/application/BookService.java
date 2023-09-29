package com.programmers.application;

import com.programmers.config.enums.ExitCommand;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.checked.InvalidExitCommandException;
import com.programmers.presentation.UserInteraction;
import com.programmers.util.Messages;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;
    private final UserInteraction userInteraction;

    public String exitApp() {
        try{
            userInteraction.displayMessage(Messages.Exit_PROMPT.getMessage());
            String exitInput = userInteraction.collectUserInput();
            ExitCommand.promptForExit(exitInput);
        }catch (InvalidExitCommandException e){
            userInteraction.displayMessage(e.getMessage());
            exitApp();
        }
        return Messages.Return_MENU.getMessage();
    }
}
