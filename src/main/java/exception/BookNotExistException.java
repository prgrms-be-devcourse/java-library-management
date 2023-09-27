package exception;

public class BookNotExistException extends Exception{
    public BookNotExistException() {
        super("[System] 존재하지 않는 도서번호 입니다.\n");
    }
}
