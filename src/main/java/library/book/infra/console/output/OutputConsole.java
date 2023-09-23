package library.book.infra.console.output;

import java.io.PrintStream;

public class OutputConsole {

	private static final PrintStream out = System.out;

	public void showSelectMode() {
		StringBuilder message = new StringBuilder();

		message.append("Q. 모드를 선택해주세요.\n");
		message.append("1. 일반 모드\n");
		message.append("2. 테스트 모드\n");
		appendInputPrefix(message);

		out.println(message);
	}

	public void showSystemMessage(final String message) {
		out.println(message);
	}

	private void appendInputPrefix(StringBuilder message) {
		message.append("\n> ");
	}
}
