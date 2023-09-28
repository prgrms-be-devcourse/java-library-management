package library.exception;

public enum InputErrorMessage {

    INVALID_INPUT("유효하지 않은 입력입니다."),
    NOT_NUMBER("숫자만 입력 받을 수 있습니다.");

    private final String message;

    InputErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
