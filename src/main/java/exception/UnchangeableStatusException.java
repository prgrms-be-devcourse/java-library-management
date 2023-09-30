package exception;

public class UnchangeableStatusException extends RuntimeException{
    public UnchangeableStatusException(String message){
        super(message);
    }
}
