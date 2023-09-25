package library.book.infra.console.input;

import static library.book.exception.ErrorCode.*;

import java.util.InputMismatchException;
import java.util.Scanner;

import library.book.exception.BookException;

public class ConsoleInputHandler implements InputHandler{

	public int inputNumber() {
		try {
			Scanner scanner = new Scanner(System.in);
			return scanner.nextInt();
		} catch (InputMismatchException e) {
			throw BookException.of(ONLY_NUMBER);
		}
	}

	public String inputString() {
		Scanner scanner = new Scanner(System.in);
		return scanner.next();
	}
}
