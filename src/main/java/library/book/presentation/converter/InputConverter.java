package library.book.presentation.converter;

import static library.book.exception.ErrorCode.*;

import library.book.application.dto.request.RegisterBookRequest;
import library.book.exception.LibraryException;

public class InputConverter {

	private static final String[] numberStrings
		= {"ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE"};

	public String convertNumberToString(final int number) {
		return numberStrings[number];
	}

	public RegisterBookRequest convertStringToRegisterRequest(
		final String bookInfo
	) {
		String[] splitBookInfo = bookInfo.split("\\|\\|\\|");
		validateBookInfoFormat(splitBookInfo.length);

		return new RegisterBookRequest(
			splitBookInfo[0],
			splitBookInfo[1],
			Integer.parseInt(splitBookInfo[2])
		);
	}

	private void validateBookInfoFormat(int size) {
		if (size != 3) {
			throw LibraryException.of(INVALID_BOOK_INFO_FORMAT);
		}
	}
}
