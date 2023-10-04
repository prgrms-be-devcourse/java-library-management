package com.programmers.config.enums;

import com.programmers.config.factory.ModeAbstractFactory;
import com.programmers.config.factory.NormalModeFactory;
import com.programmers.config.factory.TestModeFactory;
import com.programmers.exception.checked.InvalidModeNumberException;

import java.util.stream.Stream;

public enum Mode {
    NORMAL("1", NormalModeFactory.getInstance()),
    TEST("2", TestModeFactory.getInstance());

    Mode(String modeNumber, ModeAbstractFactory repositoryFactory) {
        this.modeNumber = modeNumber;
        this.repositoryFactory = repositoryFactory;
    }

    private final String modeNumber;
    private final ModeAbstractFactory repositoryFactory;

    public static ModeAbstractFactory getModeFactory(String inputModeNumber)
        throws InvalidModeNumberException {
        return Stream.of(Mode.values())
            .filter(mode -> mode.modeNumber.equals(inputModeNumber))
            .findAny()
            .orElseThrow(() -> new InvalidModeNumberException())
            .repositoryFactory;
    }
}
