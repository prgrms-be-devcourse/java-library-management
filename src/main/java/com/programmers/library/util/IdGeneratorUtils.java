package com.programmers.library.util;

public class IdGeneratorUtils {
	private static Long id = 0L;

	private IdGeneratorUtils() {
	}

	public static void initialize(Long initId) {
		id = initId;
	}

	public static Long generateId() {
		return ++id;
	}
}
