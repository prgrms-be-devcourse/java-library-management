package library.book.infra.console.output;

import java.io.PrintStream;

import library.book.presentation.io.OutputHandler;

public class ConsoleOutputHandler implements OutputHandler {

	private static final PrintStream out = System.out;
	private static final String NEW_LINE = System.lineSeparator();

	public void showSelectMode() {
		StringBuilder message = new StringBuilder();

		message.append("Q. 모드를 선택해주세요.").append(NEW_LINE);
		message.append("1. 일반 모드").append(NEW_LINE);
		message.append("2. 테스트 모드").append(NEW_LINE);

		printMessage(message);
	}

	public void showSystemMessage(final String message) {
		out.println(message);
	}

	public void showSelectFunction() {
		StringBuilder message = new StringBuilder();

		message.append(NEW_LINE);
		message.append("Q. 사용할 기능을 선택해주세요.").append(NEW_LINE);
		message.append("1. 도서 등록").append(NEW_LINE);
		message.append("2. 전체 도서 목록 조회").append(NEW_LINE);
		message.append("3. 제목으로 도서 검색").append(NEW_LINE);
		message.append("4. 도서 대여").append(NEW_LINE);
		message.append("5. 도서 반납").append(NEW_LINE);
		message.append("6. 도서 분실").append(NEW_LINE);
		message.append("7. 도서 삭제").append(NEW_LINE);

		printMessage(message);
	}

	private void printMessage(StringBuilder message) {
		out.println(message);
	}

	public void showInputPrefix() {
		out.print("> ");
	}

	public void showHorizontalLine() {
		out.println(NEW_LINE + "------------------------------" + NEW_LINE);
	}
}
