package com.programmers.library.io;

import java.util.List;
import java.util.Scanner;

public class ConsoleInput implements Input{
	private static final Scanner scanner = new Scanner(System.in);
	private static final String MODE_MESSAGE = "Q. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n";
	private static final String MENU_MESSAGE = "Q. 사용할 기능을 선택해주세요.\n1. 도서 등록\n2. 전체 도서 목록 조회\n3. 제목으로 도서 검색\n4. 도서 대여\n5. 도서 반납\n6. 도서 분실\n7. 도서 삭제";

	@Override
	public int inputMode() {
		System.out.println(MODE_MESSAGE);
		System.out.println();
		System.out.print("> ");
		return scanner.nextInt();
	}

	@Override
	public int inputMenu() {
		System.out.println(MENU_MESSAGE);
		System.out.println();
		System.out.print("> ");
		return scanner.nextInt();
	}

	@Override
	public long inputBookId() {
		scanner.nextLine();
		String result = promptInput("Q. 대여할 도서번호를 입력하세요");
		return Long.parseLong(result);
	}

	private String promptInput(String question) {
		System.out.println();
		System.out.println(question);
		System.out.println();
		System.out.print("> ");
		return scanner.nextLine();
	}

	@Override
	public String inputBookAuthor() {
		return promptInput("Q. 작가 이름을 입력하세요.");
	}

	@Override
	public long inputBookPages() {
		String result = promptInput("Q. 페이지 수를 입력하세요.");
		try {
			return Long.parseLong(result);
		} catch (NumberFormatException e) {
			System.out.println("[Error] 올바른 숫자를 입력해주세요.");
			return inputBookPages();
		}
	}

	@Override
	public String inputBookTitleSearch() {
		scanner.nextLine();
		return promptInput("Q. 검색할 도서 제목 일부를 입력하세요.");
	}

	@Override
	public long inputBookIdReturn() {
		scanner.nextLine();
		String result = promptInput("Q. 반납할 도서번호를 입력하세요");
		return Long.parseLong(result);
	}

	@Override
	public long inputBookIdLost() {
		scanner.nextLine();
		String result = promptInput("Q. 분실 처리할 도서번호를 입력하세요");
		return Long.parseLong(result);
	}

	@Override
	public long inputBookIdDelete() {
		scanner.nextLine();
		String result = promptInput("Q. 삭제 처리할 도서번호를 입력하세요");
		return Long.parseLong(result);
	}

	@Override
	public String inputBookTitle() {
		scanner.nextLine();
		return promptInput("Q. 등록할 도서 제목을 입력하세요.");
	}
}
