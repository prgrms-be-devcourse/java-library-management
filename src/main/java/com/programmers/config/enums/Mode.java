package com.programmers.config.enums;

import com.programmers.config.factory.ModeAbstractFactory;
import com.programmers.config.factory.NormalModeFactory;
import com.programmers.config.factory.TestModeFactory;
import com.programmers.domain.repository.BookRepository;
import com.programmers.exception.checked.InvalidModeNumberException;
import com.programmers.presentation.UserInteraction;
import com.programmers.util.Messages;

import java.util.stream.Stream;

import static com.programmers.exception.ErrorCode.INVALID_MODE_NUMBER;

public enum Mode {
    NORMAL("1", NormalModeFactory.getInstance()),
    TEST("2", TestModeFactory.getInstance());

    Mode(String modeNumber, ModeAbstractFactory repositoryFactory) {
        this.modeNumber = modeNumber;
        this.repositoryFactory = repositoryFactory;
    }

    private final String modeNumber;
    private final ModeAbstractFactory repositoryFactory;

    public static BookRepository getBookRepositoryByMode(UserInteraction userInteraction) {
        try {
            userInteraction.displayMessage(Messages.SELECT_MODE.getMessage());
            String inputModeNumber = userInteraction.collectUserInput();

            return Stream.of(Mode.values())
                    .filter(mode -> mode.modeNumber.equals(inputModeNumber))
                    .findAny()
                    .orElseThrow(() -> new InvalidModeNumberException(INVALID_MODE_NUMBER))
                    .repositoryFactory.createBookRepository();
        } catch (InvalidModeNumberException e) {
            userInteraction.displayMessage(e.getErrorCode().getMessage());
            return getBookRepositoryByMode(userInteraction);
        }
    }
}
