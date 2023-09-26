package com.programmers.library.model;

import java.util.Arrays;

public enum Mode {
	NORMAL_MODE(1),
	TEST_MODE(2);

	private static final String INVALID_MODE = "모드는 1~2 사이의 숫자만 입력 가능합니다.";
	private final int modeNumber;

	Mode(int modeNumber) {
		this.modeNumber = modeNumber;
	}

	public static Mode of(String modeInput) {
		return Arrays.stream(values())
			.filter(mode -> mode.modeNumber == Integer.parseInt(modeInput))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(INVALID_MODE));
	}
}
