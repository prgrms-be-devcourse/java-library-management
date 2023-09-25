package com.programmers.library.model;

import java.util.Arrays;

public enum Menu {
	ADD_BOOK(1),
	LIST_BOOK(2),
	SEARCH_BOOK(3),
	BORROW_BOOK(4),
	RETURN_BOOK(5),
	LOST_BOOK(6),
	DELETE_BOOK(7);

	private static final String INVALID_MENU = "메뉴는 1~7 사이의 숫자만 입력 가능합니다.";
	private final int menuNumber;

	Menu(int menuNumber) {
		this.menuNumber = menuNumber;
	}

	public static Menu of(String menuNumber) {
		return Arrays.stream(values())
			.filter(menu -> menu.menuNumber == Integer.parseInt(menuNumber)) //TODO : 숫자 형식이 아닐 경우에는?
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException(INVALID_MENU));
	}

}
