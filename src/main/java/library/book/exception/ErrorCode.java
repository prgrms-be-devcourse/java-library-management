package library.book.exception;

public enum ErrorCode {

	ONLY_NUMBER("페이지는 숫자만 입력해주세요."),
	FILE_READ_FAIL("File Read 오류입니다."),
	FILE_WRITE_FAIL("File Write 오류입니다."),
	TYPE_MISS_MATCH("주입된 타입이 틀립니다."),
	;

	private final String message;

	ErrorCode(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
