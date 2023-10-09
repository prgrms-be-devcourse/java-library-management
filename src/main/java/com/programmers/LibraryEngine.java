package com.programmers;

import com.programmers.controller.NumberMappingHandler;
import com.programmers.exception.ErrorChecking;
import com.programmers.exception.ErrorCode;
import com.programmers.exception.LibraryException;
import com.programmers.front.BookConsole;

public class LibraryEngine {

    private static LibraryConfiguration setting;

    public static LibraryConfiguration getSetting() {
        return setting;
    }

    public void run() {
        setting = setting();
        if(setting != null) init(setting);
    }

    public void init(LibraryConfiguration configuration) {
        NumberMappingHandler numberMappingHandler = new NumberMappingHandler(configuration.bookController);
        boolean keep = true;
        while(keep){
            keep = numberMappingHandler.numberMapping(BookConsole.chooseFunction());
        }
    }


    public LibraryConfiguration setting() {
        try {
            String mode = BookConsole.chooseMode();
            if (!ErrorChecking.checkNumber(mode)) throw new LibraryException(ErrorCode.NOT_NUMBER);
            if (!ErrorChecking.modeChecking(Integer.parseInt(mode)))
                throw new LibraryException(ErrorCode.NUMBER_OVER_BOUNDARY);
            int checkedMode = Integer.parseInt(mode);
            return new LibraryConfiguration(checkedMode);
        } catch (LibraryException e){
            System.out.println(e.getErrorCode().getMessage());
            run();
            return null;
        }
    }

}
