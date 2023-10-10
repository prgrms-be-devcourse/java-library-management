package domain;

import java.util.Arrays;
import java.util.NoSuchElementException;

public enum Status {
    POSSIBLE("대여 가능"),
    IMPOSSIBLE("대여중"),
    ORGANIZE("도서 정리중"),
    LOST("분실됨");

    private final String message;

    Status(String message) {
        this.message = message;
    }

    public static Status of(String data){
        return Arrays.stream(values())
                .filter(message -> message.isEquals(data))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("도서 상태 에러"));
    }

    private boolean isEquals(String data){
        return this.message.equals(data);
    }

    public String getMessage(){
        return message;
    }
}
