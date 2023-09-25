package library.book.presentation.constant;

public enum Message {

	ENTRY_REGISTER_MENU("[System] 도서 등록 메뉴로 넘어갑니다.\n"),
	INPUT_BOOK_NAME("Q. 등록할 도서 제목을 입력하세요.\n"),
	INPUT_AUTHOR_NAME("\nQ. 작가 이름을 입력하세요.\n"),
	INPUT_PAGES("\nQ. 페이지 수를 입력하세요.\n"),
	COMPLETE_REGISTER("\n[System] 도서 등록이 완료되었습니다.\n"),

	ENTRY_SEARCH_ALL_BOOKS("[System] 전체 도서 목록입니다.\n"),
	COMPLETE_SEARCH_ALL_BOOKS("[System] 도서 목록 끝"),

	ENTRY_SEARCH_BOOKS_BY_TITLE("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n"),
	INPUT_TITLE("Q. 검색할 도서 제목 일부를 입력하세요.\n"),
	;

	private final String value;

	Message(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
