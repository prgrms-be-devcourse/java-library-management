package constant;

public enum Function {
    SYSTEM_END,
    SAVE,
    FIND_ALL,
    FIND_BY_TITLE,
    BORROW,
    RETURN,
    LOST,
    DELETE;

    public static Function getFunctionByIdx(int idx) {
        Function[] functions = Function.values();
        if (idx >= 0 && idx < functions.length) {
            return functions[idx];
        } else {
            throw new IllegalArgumentException(ExceptionMsg.WRONG_SELECTION.getMessage());
        }
    }
}
