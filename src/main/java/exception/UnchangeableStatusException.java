package exception;

// 도서 상태 변경 불가 예외
public class UnchangeableStatusException extends RuntimeException {
    public UnchangeableStatusException(String message) {
        super(message);
    }
}
