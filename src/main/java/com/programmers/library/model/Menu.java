package com.programmers.library.model;

import static com.programmers.library.constants.MessageConstants.*;

import java.util.Arrays;

public enum Menu {
	ADD_BOOK(1),
	GET_ALL_BOOKS(2),
	FIND_BOOKS_BY_TITLE(3),
	BORROW_BOOK(4),
	RETURN_BOOK(5),
	LOST_BOOK(6),
	DELETE_BOOK(7);

	private final int menuNumber;

	Menu(int menuNumber) {
		this.menuNumber = menuNumber;
	}

	public static Menu of(String menuNumber) {
		try {
			int parsedMenuNumber = Integer.parseInt(menuNumber);
			return Arrays.stream(values())
				.filter(menu -> menu.menuNumber == parsedMenuNumber)
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException(INVALID_MENU));
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(INVALID_MENU);
		}
	}

}
