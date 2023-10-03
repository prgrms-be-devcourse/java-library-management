package exception;

import java.util.zip.CheckedInputStream;

public class NotAppropriateScope extends Exception {
    public NotAppropriateScope() {
    }

    public NotAppropriateScope(String message) {
        super(message);
    }
}
