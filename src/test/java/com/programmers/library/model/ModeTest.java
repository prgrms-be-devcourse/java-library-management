package com.programmers.library.model;

import org.junit.jupiter.api.Test;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class ModeTest {

	@Test
	public void testValidModeNumber() {
		assertEquals(Mode.NORMAL_MODE, Mode.of("1"));
		assertEquals(Mode.TEST_MODE, Mode.of("2"));
	}

	@Test
	public void testInvalidModeNumber() {
		assertThrows(IllegalArgumentException.class, () -> Mode.of("100"));
		assertThrows(IllegalArgumentException.class, () -> Mode.of("abc"));
	}

	@Test
	public void testExceptionMessage() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Mode.of("100"));
		assertTrue(exception.getMessage().contains(INVALID_MODE));
	}
}
