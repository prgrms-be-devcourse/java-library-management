package com.programmers.library.config;

import com.programmers.library.business.LibraryExecutor;
import com.programmers.library.business.Menu;
import com.programmers.library.service.LibraryService;
import com.programmers.library.view.console.ConsoleInput;
import com.programmers.library.view.console.ConsoleOutput;

public class InitialConfig {

    public static LibraryExecutor configLibrary() {
        ConsoleOutput output = new ConsoleOutput();
        ConsoleInput input = new ConsoleInput();


        LibraryServiceFactory libraryServiceFactory = new LibraryServiceFactory();
        LibraryService libraryService = libraryServiceFactory.createLibraryService(selectMode(output,input));

        Menu menu = new Menu(libraryService, output, input);

        return new LibraryExecutor(menu);
    }

    private static int selectMode(ConsoleOutput output, ConsoleInput input) {
        output.showMode();
        return Math.toIntExact(input.selectNumber());
    }
}
