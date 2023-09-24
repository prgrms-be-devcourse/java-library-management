package library.book.infra.console.output;

public interface OutputHandler {

	void showSelectMode();

	void showSystemMessage(final String message);

	void showSelectFunction();

	void showInputPrefix();
}
