package exception;

public class BookOrganizingException extends RuntimeException{

    public BookOrganizingException() {
        super("[System] 정리중인 도서입니다.\n");
    }
}
