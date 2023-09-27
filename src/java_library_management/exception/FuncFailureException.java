package java_library_management.exception;

public class FuncFailureException extends RuntimeException {
    public FuncFailureException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
