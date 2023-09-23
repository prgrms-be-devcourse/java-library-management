package library.book.exception;

public enum ErrorCode {

	ONLY_NUMBER("페이지는 숫자만 입력해주세요.")
	;

	private final String message;

	ErrorCode(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
