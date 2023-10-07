package com.programmers;

import com.programmers.app.Librarian;
import com.programmers.app.config.InitialConfig;

public class LibrarianApplication {
    public static void main(String[] args) {

        InitialConfig initialConfig = new InitialConfig();
        Librarian librarian = new Librarian(initialConfig);

        librarian.work();
    }
}
