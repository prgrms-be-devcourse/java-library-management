package library.view;

import library.view.console.ConsoleIOHandler;

public class ConsoleEngine implements Runnable {

    private final ConsoleIOHandler consoleIOHandler;
    boolean isRunning = true;

    public ConsoleEngine(ConsoleIOHandler consoleIOHandler) {
        this.consoleIOHandler = consoleIOHandler;
    }

    @Override
    public void run() {
        while (isRunning) {
            progress();
        }
    }

    private void progress() {
        try {
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            isRunning = false;
            System.out.println(e.getMessage());
        }
    }
}
