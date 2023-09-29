package com.programmers.library.model;

import org.junit.jupiter.api.Test;

import static com.programmers.library.constants.MessageConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class MenuTest {

	@Test
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
	public void testInvalidMenuNumber() {
		assertThrows(IllegalArgumentException.class, () -> Menu.of("100"));
		assertThrows(IllegalArgumentException.class, () -> Menu.of("abc"));
	}

	@Test
	public void testExceptionMessage() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> Menu.of("100"));
		assertTrue(exception.getMessage().contains(INVALID_MENU));
	}
}