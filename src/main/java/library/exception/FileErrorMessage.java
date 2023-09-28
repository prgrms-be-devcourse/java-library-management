package library.exception;

public enum FileErrorMessage {

    FILE_PATH_IS_NULL_OR_EMPTY("파일 경로는 null이거나 비어있을 수 없습니다."),
    CSV_FIELD_COUNT_MISMATCH("필드의 개수가 맞지 않습니다."),
    INVALID_NUMBER_FORMAT("파일 내 숫자 형식이 잘못되었습니다."),
    INVALID_DATE_TIME_FORMAT("파일 내 날짜 형식이 잘못되었습니다."),
    IO_EXCEPTION("파일 입출력 중 오류가 발생했습니다.");

    private final String message;

    FileErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
