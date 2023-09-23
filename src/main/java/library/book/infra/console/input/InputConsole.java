package library.book.infra.console.input;

import static library.book.exception.ErrorCode.*;

import java.util.InputMismatchException;
import java.util.Scanner;

import library.book.exception.LibraryException;

public class InputConsole {

	public int inputNumber() {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.print("> ");
			return scanner.nextInt();
		} catch (InputMismatchException e) {
			throw LibraryException.of(ONLY_NUMBER);
		}
	}
}
