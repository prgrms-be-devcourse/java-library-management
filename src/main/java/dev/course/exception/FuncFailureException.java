package dev.course.exception;

/**
 * 추상화를 적용시켜, 하나의 예외 인터페이스를 만들고 도서 기능에 대해 실패 예외 구현체를 만들려고 하였으나,
 * 예외를 발생시켜 메시지를 출력한다는 역할이 너무 작기 때문에 과하다고 판단해 하나의 예외 클래스만을 두었음
 */

public class FuncFailureException extends RuntimeException {
    public FuncFailureException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
