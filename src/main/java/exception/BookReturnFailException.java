package exception;

public class BookReturnFailException extends RuntimeException{
    public BookReturnFailException() {
        super("[System] 원래 대여가 가능한 도서입니다.\n");
    }
}
