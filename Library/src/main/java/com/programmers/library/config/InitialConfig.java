package com.programmers.library.config;

import com.programmers.library.business.LibraryExecutor;
import com.programmers.library.business.Menu;
import com.programmers.library.exception.ErrorCode;
import com.programmers.library.exception.ExceptionHandler;
import com.programmers.library.file.FileHandler;
import com.programmers.library.file.FileUtil;
import com.programmers.library.repository.FileRepository;
import com.programmers.library.repository.TestRepository;
import com.programmers.library.service.LibraryService;
import com.programmers.library.view.console.ConsoleInput;
import com.programmers.library.view.console.ConsoleOutput;

public class InitialConfig {

    private static final int NORMAL_MODE = 1;
    private static final int TEST_MODE = 2;

    public static LibraryExecutor configLibrary() {
        ConsoleOutput output = new ConsoleOutput();
        ConsoleInput input = new ConsoleInput();

        LibraryService libraryService;

        int mode = selectMode(output,input);

        switch (mode) {
            case NORMAL_MODE->{
                libraryService = new LibraryService(new FileRepository(new FileHandler(new FileUtil())));
            }
            case TEST_MODE ->{
                libraryService = new LibraryService(new TestRepository());
            }
            default ->{
                throw ExceptionHandler.err(ErrorCode.INVALID_MODE_EXCEPTION);
            }
        }
        Menu menu = new Menu(libraryService, output, input);

        return new LibraryExecutor(menu);
    }

    private static int selectMode(ConsoleOutput output, ConsoleInput input) {
        output.showMode();
        return Math.toIntExact(input.selectNumber());
    }
}
