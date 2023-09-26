package java_library_management;

import java.util.Arrays;

public enum BookState {

    AVAILABLE("대여 가능"),
    LOAN("대여중"),
    ARRANGEMENT("도서 정리중"),
    LOST("분실됨");

    private String state;

    BookState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public static BookState valueOfState(String state) {
        return Arrays.stream(values())
                .filter(value -> value.state.equals(state))
                .findAny()
                .orElse(null);
    }
}
