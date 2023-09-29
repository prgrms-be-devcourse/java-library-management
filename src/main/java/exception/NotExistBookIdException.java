package exception;

public class NotExistBookIdException extends RuntimeException{
    public NotExistBookIdException(){
        super("존재하지 않는 도서번호 입니다.");
    }
}
