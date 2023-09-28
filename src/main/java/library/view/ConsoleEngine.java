package library.view;

public class ConsoleEngine implements Runnable {

    boolean isRunning = true;

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
