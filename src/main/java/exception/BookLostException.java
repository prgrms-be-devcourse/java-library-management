package exception;

public class BookLostException extends RuntimeException{
    public BookLostException() {
        super("[System] 분실된 도서입니다.\n");
    }
}
