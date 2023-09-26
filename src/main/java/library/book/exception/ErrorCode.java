package library.book.exception;

public enum ErrorCode {

	ONLY_NUMBER("페이지는 숫자만 입력해주세요."),
	FILE_READ_FAIL("File Read 오류입니다."),
	FILE_WRITE_FAIL("File Write 오류입니다."),
	INVALID_BOOK_INFO_FORMAT("도서 정보를 입력 할 때 '|||' 는 입력할 수 없습니다."),

	ALREADY_RENTED("이미 대여중인 도서입니다."),
	NOW_LOST("분실된 도서입니다."),
	NOW_CLEANING("정리중인 도서입니다. 잠시후 다시 시도해주세요."),
	NOT_FOUND("존재하지 않는 도서입니다."),
	INVALID_CLEANING_END_TIME("종료시간이 설정돼있지 않습니다."),
	ALREADY_AVAILABLE_RENT("원래 대여가 가능한 도서입니다."),
	;

	private final String message;

	ErrorCode(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
