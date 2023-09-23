package library.book.presentation.converter;

public class NumberConverter {

	private static final String[] numberStrings
		= {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};

	public String convert(final int number) {
		return numberStrings[number];
	}
}
