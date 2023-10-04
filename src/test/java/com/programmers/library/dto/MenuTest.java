package com.programmers.library.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

import com.programmers.library.enums.Menu;

public class MenuTest {

	@Test
	@DisplayName("메뉴 번호를 입력하면 해당 메뉴를 반환합니다")
	public void testValidMenuNumber() {
		assertEquals(Menu.ADD_BOOK, Menu.of("1"));
		assertEquals(Menu.GET_ALL_BOOKS, Menu.of("2"));
		assertEquals(Menu.FIND_BOOKS_BY_TITLE, Menu.of("3"));
		assertEquals(Menu.BORROW_BOOK, Menu.of("4"));
		assertEquals(Menu.RETURN_BOOK, Menu.of("5"));
		assertEquals(Menu.LOST_BOOK, Menu.of("6"));
		assertEquals(Menu.DELETE_BOOK, Menu.of("7"));
	}

	@Test
	@DisplayName("메뉴 번호가 1~7이 아니면 예외가 발생합니다")
	public void testInvalidMenuNumber() {
		assertThrows(IllegalArgumentException.class, () -> Menu.of("100"));
		assertThrows(IllegalArgumentException.class, () -> Menu.of("abc"));
	}

	@Test
	@DisplayName("메뉴 번호 예외가 발생할 때 메시지를 확인합니다")
	public void testExceptionMessage() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Menu.of("100"));
		assertTrue(exception.getMessage().contains(INVALID_MENU));
	}
}