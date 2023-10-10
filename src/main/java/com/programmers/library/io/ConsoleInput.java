package com.programmers.library.io;

import java.util.Scanner;

import com.programmers.library.dto.AddBookRequestDto;
import com.programmers.library.dto.BorrowBookRequestDto;
import com.programmers.library.dto.DeleteBookRequestDto;
import com.programmers.library.dto.FindBookRequestDto;
import com.programmers.library.dto.LostBookRequestDto;
import com.programmers.library.dto.ReturnBookRequestDto;
import com.programmers.library.enums.Menu;
import com.programmers.library.enums.Mode;

public class ConsoleInput implements Input {
	private static final String MODE_PROMPT = "Q. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n\n> ";
	private static final String MENU_PROMPT = "Q. 사용할 기능을 선택해주세요.\n1. 도서 등록\n2. 전체 도서 목록 조회\n3. 제목으로 도서 검색\n4. 도서 대여\n5. 도서 반납\n6. 도서 분실\n7. 도서 삭제\n\n> ";
	private static final String ADD_BOOK_TITLE_PROMPT = "\nQ. 등록할 도서 제목을 입력하세요.\n\n> ";
	private static final String ADD_BOOK_AUTHOR_PROMPT = "\nQ. 작가 이름을 입력하세요.\n\n> ";
	private static final String ADD_BOOK_PAGES_PROMPT = "\nQ. 페이지 수를 입력하세요.\n\n> ";
	private static final String FIND_BOOK_TITLE_PROMPT = "\nQ. 검색할 도서 제목 일부를 입력하세요.\n\n> ";
	private static final String BORROW_BOOK_ID_PROMPT = "\nQ. 대여할 도서번호를 입력하세요.\n\n> ";
	private static final String RETURN_BOOK_ID_PROMPT = "\nQ. 반납할 도서번호를 입력하세요.\n\n> ";
	private static final String LOST_BOOK_ID_PROMPT = "\nQ. 분실 처리할 도서번호를 입력하세요.\n\n> ";
	private static final String DELETE_BOOK_ID_PROMPT = "\nQ. 삭제 처리할 도서번호를 입력하세요.\n\n> ";
	private final Scanner scanner = new Scanner(System.in);

	private void printPrompt(String menuPrompt) {
		System.out.print(menuPrompt);
	}

	@Override
	public Mode inputMode() {
		printPrompt(MODE_PROMPT);
		return Mode.of(readLineWithTrimming());
	}

	private String readLineWithTrimming() {
		return scanner.nextLine().trim();
	}

	@Override
	public Menu inputMenu() {
		printPrompt(MENU_PROMPT);
		return Menu.of(readLineWithTrimming());
	}

	@Override
	public AddBookRequestDto inputAddBookRequest() {
		printPrompt(ADD_BOOK_TITLE_PROMPT);
		String title = readLineWithTrimming();
		printPrompt(ADD_BOOK_AUTHOR_PROMPT);
		String author = readLineWithTrimming();
		printPrompt(ADD_BOOK_PAGES_PROMPT);
		String pages = readLineWithTrimming();
		return new AddBookRequestDto(title, author, pages);
	}

	@Override
	public BorrowBookRequestDto inputBorrowBookRequest() {
		printPrompt(BORROW_BOOK_ID_PROMPT);
		return new BorrowBookRequestDto(readLineWithTrimming());
	}

	@Override
	public DeleteBookRequestDto inputDeleteBookRequest() {
		printPrompt(DELETE_BOOK_ID_PROMPT);
		return new DeleteBookRequestDto(readLineWithTrimming());
	}

	@Override
	public LostBookRequestDto inputLostBookRequest() {
		printPrompt(LOST_BOOK_ID_PROMPT);
		return new LostBookRequestDto(readLineWithTrimming());
	}

	@Override
	public ReturnBookRequestDto inputReturnBookRequest() {
		printPrompt(RETURN_BOOK_ID_PROMPT);
		return new ReturnBookRequestDto(readLineWithTrimming());
	}

	@Override
	public FindBookRequestDto inputFindBookRequest() {
		printPrompt(FIND_BOOK_TITLE_PROMPT);
		return new FindBookRequestDto(readLineWithTrimming());
	}
}
