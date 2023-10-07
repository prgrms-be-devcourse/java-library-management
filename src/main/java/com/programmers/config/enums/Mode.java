package com.programmers.config.enums;

import com.programmers.config.factory.ModeAbstractFactory;
import com.programmers.config.factory.NormalModeFactory;
import com.programmers.config.factory.TestModeFactory;
import com.programmers.exception.checked.InvalidModeNumberException;
import java.util.stream.Stream;

public enum Mode {
    NORMAL("1", NormalModeFactory.getInstance()),
    TEST("2", TestModeFactory.getInstance());

    Mode(String modeNumber, ModeAbstractFactory modeFactory) {
        this.modeNumber = modeNumber;
        this.modeFactory = modeFactory;
    }

    private final String modeNumber;
    private final ModeAbstractFactory modeFactory;

    public static ModeAbstractFactory getModeFactory(String inputModeNumber)
        throws InvalidModeNumberException {
        return Stream.of(Mode.values())
            .filter(mode -> mode.modeNumber.equals(inputModeNumber))
            .findAny()
            .orElseThrow(InvalidModeNumberException::new)
            .modeFactory;
    }
}
