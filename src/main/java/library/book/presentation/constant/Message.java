package library.book.presentation.constant;

public enum Message {

	ENTRY_REGISTER("[System] 도서 등록 메뉴로 넘어갑니다."),
	INPUT_BOOK_NAME("Q. 등록할 도서 제목을 입력하세요."),
	INPUT_AUTHOR_NAME("Q. 작가 이름을 입력하세요."),
	INPUT_PAGES("Q. 페이지 수를 입력하세요."),
	COMPLETE_REGISTER("[System] 도서 등록이 완료되었습니다."),

	ENTRY_SEARCH_ALL_BOOKS("[System] 전체 도서 목록입니다."),
	COMPLETE_SEARCH_ALL_BOOKS("[System] 도서 목록 끝"),

	ENTRY_SEARCH_BOOKS_BY_TITLE("[System] 검색된 도서 끝."),
	INPUT_TITLE("Q. 검색할 도서 제목 일부를 입력하세요."),

	ENTRY_RENT_BOOK("[System] 도서 대여 메뉴로 넘어갑니다."),
	INPUT_RENT_BOOK_ID("Q. 대여할 도서번호를 입력하세요."),
	COMPLETE_RENT("[System] 도서가 대여 처리 되었습니다."),

	ENTRY_RETURN_BOOK("[System] 도서 반납 메뉴로 넘어갑니다."),
	INPUT_RETURN_BOOK_ID("Q. 반납할 도서번호를 입력하세요."),
	COMPLETE_RETURN("[System] 도서가 반납 처리 되었습니다."),

	ENTRY_LOST_BOOK("[System] 도서 분실 처리 메뉴로 넘어갑니다."),
	INPUT_LOST_BOOK_ID("Q. 분실 처리할 도서번호를 입력하세요."),
	COMPLETE_LOST("[System] 도서가 분실 처리 되었습니다."),

	ENTRY_DELETE("[System] 도서 삭제 처리 메뉴로 넘어갑니다."),
	INPUT_DELETE_ID("Q. 삭제 처리할 도서번호를 입력하세요."),
	COMPLETE_DELETE("[System] 도서가 삭제 처리 되었습니다."),
	;

	private final String value;

	Message(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
