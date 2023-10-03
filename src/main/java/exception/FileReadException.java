package exception;

public class FileReadException extends RuntimeException {
    public FileReadException() {
    }

    public FileReadException(String message) {
        super(message);
    }
}
