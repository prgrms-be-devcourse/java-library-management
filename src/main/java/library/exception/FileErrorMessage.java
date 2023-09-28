package library.exception;

public enum FileErrorMessage {

    IO_EXCEPTION("파일 처리 중 오류가 발생했습니다.");

    private final String message;

    FileErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
