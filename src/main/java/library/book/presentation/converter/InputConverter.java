package library.book.presentation.converter;

public class InputConverter {

	private static final String[] numberStrings
		= {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};

	public String convertNumberToString(final int number) {
		return numberStrings[number];
	}
}
