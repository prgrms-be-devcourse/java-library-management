package com.programmers.library.io;

import java.util.List;
import java.util.Scanner;

public class ConsoleInput implements Input{
	private static final Scanner scanner = new Scanner(System.in);
	@Override
	public int inputMode() {
		return scanner.nextInt();
	}

	@Override
	public int inputMenu() {
		return scanner.nextInt();
	}

	@Override
	public int inputBookId() {
		return scanner.nextInt();
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
	public String inputBookTitle() {
		scanner.nextLine();
		return promptInput("Q. 등록할 도서 제목을 입력하세요.");
	}
}
