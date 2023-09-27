package exception;

public class BookAlreadyLostException extends Exception{
    public BookAlreadyLostException() {
        super("[System] 이미 분실 처리된 도서입니다.\n");
    }
}
