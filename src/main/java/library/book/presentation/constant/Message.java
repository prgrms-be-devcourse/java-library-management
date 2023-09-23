package library.book.presentation.constant;

public enum Message {

	ENTRY_REGISTER_MENU("[System] 도서 등록 메뉴로 넘어갑니다.\n"),
	INPUT_BOOK_NAME("Q. 등록할 도서 제목을 입력하세요.\n"),
	INPUT_AUTHOR_NAME("\n\nQ. 작가 이름을 입력하세요.\n"),
	INPUT_PAGES("\n\nQ. 페이지 수를 입력하세요.\n"),
	COMPLETE_REGISTER("\n\n[System] 도서 등록이 완료되었습니다.\n"),
	;

	private final String value;

	Message(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
