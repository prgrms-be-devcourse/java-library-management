package constant;

import java.util.Arrays;

public enum Function {
    SYSTEM_END(0),
    SAVE(1),
    FIND_ALL(2),
    FIND_BY_TITLE(3),
    BORROW(4),
    RETURN(5),
    LOST(6),
    DELETE(7);

    private final int number;

    Function(int number) {
        this.number = number;
    }

    public static Function getFunctionByIdx(int functionNumber) {
        return Arrays.stream(Function.values())
                .filter(function -> function.number == functionNumber)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ExceptionMsg.WRONG_SELECTION.getMessage()));
    }
}
