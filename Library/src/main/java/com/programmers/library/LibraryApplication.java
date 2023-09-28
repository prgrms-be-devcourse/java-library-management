package com.programmers.library;

import com.programmers.library.business.LibraryExecutor;
import com.programmers.library.config.InitialConfig;

public class LibraryApplication {
    public static void main(String[] args) {
        LibraryExecutor libraryExecutor = InitialConfig.configLibrary();
        libraryExecutor.run();
    }
}