package exception;

public class AlreadySavedBookException extends RuntimeException{
    public AlreadySavedBookException() {
        super("이미 저장된 책입니다.");
    }
}
