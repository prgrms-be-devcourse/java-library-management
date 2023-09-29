package com.programmers.library.enums;

import static com.programmers.library.constants.MessageConstants.*;

import java.util.Arrays;

public enum Mode {
	NORMAL_MODE(1),
	TEST_MODE(2);

	private final int modeNumber;

	Mode(int modeNumber) {
		this.modeNumber = modeNumber;
	}

	public static Mode of(String modeInput) {
		try {
			int parsedModeNumber = Integer.parseInt(modeInput);
			return Arrays.stream(values())
				.filter(mode -> mode.modeNumber == parsedModeNumber)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(INVALID_MODE));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_MODE);
		}
	}
}
