package library;

import library.view.ConsoleEngine;
import library.view.console.ConsoleIOHandler;
import library.view.console.in.ScannerInput;
import library.view.console.out.PrintStreamOutput;

public class LibraryApplication {

    public static void main(String[] args) {
        new ConsoleEngine(
                new ConsoleIOHandler(new ScannerInput(), new PrintStreamOutput()))
                .run();
    }
}
