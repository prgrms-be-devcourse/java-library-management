package com.programmers;

import com.programmers.app.App;
import com.programmers.app.config.InitialConfig;

public class LibrarianApplication {
    public static void main(String[] args) {

        InitialConfig initialConfig = new InitialConfig();
        App app = new App(initialConfig);

        app.run();
    }
}
