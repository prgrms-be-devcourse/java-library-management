package library.book.infra.console.output;

import java.io.PrintStream;

public class ConsoleOutputHandler implements OutputHandler {

	private static final PrintStream out = System.out;

	public void showSelectMode() {
		StringBuilder message = new StringBuilder();

		message.append("Q. 모드를 선택해주세요.\n");
		message.append("1. 일반 모드\n");
		message.append("2. 테스트 모드\n");

		printMessage(message);
	}

	public void showSystemMessage(final String message) {
		out.println(message);
	}

	public void showSelectFunction() {
		StringBuilder message = new StringBuilder();

		message.append("Q. 사용할 기능을 선택해주세요.\n");
		message.append("1. 도서 등록\n");
		message.append("2. 전체 도서 목록 조회\n");
		message.append("3. 제목으로 도서 검색\n");
		message.append("4. 도서 대여\n");
		message.append("5. 도서 반납\n");
		message.append("6. 도서 분실\n");
		message.append("7. 도서 삭제\n");

		printMessage(message);
	}

	private void printMessage(StringBuilder message) {
		out.println(message);
	}

	public void showInputPrefix() {
		out.print("> ");
	}

	public void showHorizontalLine() {
		out.println("\n------------------------------\n");
	}
}
