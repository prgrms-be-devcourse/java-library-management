package com.programmers.library.io;

import java.util.Scanner;

import com.programmers.library.model.Menu;
import com.programmers.library.model.Mode;
import com.programmers.library.model.request.AddBookRequest;
import com.programmers.library.model.request.BorrowBookRequest;
import com.programmers.library.model.request.DeleteBookRequest;
import com.programmers.library.model.request.LostBookRequest;
import com.programmers.library.model.request.ReturnBookRequest;
import com.programmers.library.model.request.SearchBookRequest;

public class Console implements Input, Output{
	private final Scanner scanner = new Scanner(System.in);
	private static final String MENU_PROMPT = "Q. 사용할 기능을 선택해주세요.\n1. 도서 등록\n2. 전체 도서 목록 조회\n3. 제목으로 도서 검색\n4. 도서 대여\n5. 도서 반납\n6. 도서 분실\n7. 도서 삭제\n\n> ";
	@Override
	public Mode inputMode() {
		return null;
	}

	@Override
	public Menu inputMenu() {
		System.out.print(MENU_PROMPT);
		return Menu.of(scanner.nextLine());
	}

	@Override
	public AddBookRequest inputAddBookRequest() {
		return null;
	}

	@Override
	public BorrowBookRequest inputBorrowBookRequest() {
		return null;
	}

	@Override
	public DeleteBookRequest inputDeleteBookRequest() {
		return null;
	}

	@Override
	public LostBookRequest inputLostBookRequest() {
		return null;
	}

	@Override
	public ReturnBookRequest inputReturnBookRequest() {
		return null;
	}

	@Override
	public SearchBookRequest inputSearchBookRequest() {
		return null;
	}

	@Override
	public void printMessage(String message) {
		System.out.println(message);
	}
}
