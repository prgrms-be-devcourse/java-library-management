package library.view.console.in;

import java.util.Scanner;

public class ScannerInput implements ConsoleInput {

    private final Scanner scanner = new Scanner(System.in);

    public String getInput() {
        return scanner.nextLine();
    }
}
