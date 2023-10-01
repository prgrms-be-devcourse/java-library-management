package exception;

public class ThreadInterruptException extends RuntimeException {
    public ThreadInterruptException() {
    }

    public ThreadInterruptException(String message) {
        super(message);
    }
}
