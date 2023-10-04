package com.programmers.library.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import com.programmers.library.enums.Mode;

public class ModeTest {

	@Test
	@DisplayName("모드 번호를 입력하면 모드를 반환합니다")
	public void testValidModeNumber() {
		assertEquals(Mode.NORMAL_MODE, Mode.of("1"));
		assertEquals(Mode.TEST_MODE, Mode.of("2"));
	}

	@Test
	@DisplayName("모드 번호가 1, 2가 아니면 예외가 발생합니다")
	public void testInvalidModeNumber() {
		assertThrows(IllegalArgumentException.class, () -> Mode.of("100"));
		assertThrows(IllegalArgumentException.class, () -> Mode.of("abc"));
	}

	@Test
	@DisplayName("모드 번호 예외가 발생할 때 메시지를 확인합니다")
	public void testExceptionMessage() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Mode.of("100"));
		assertTrue(exception.getMessage().contains(INVALID_MODE));
	}
}
