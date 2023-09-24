package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public class ConsoleOutput implements Output{


	private static final String MODE_MESSAGE = "Q. 모드를 선택해주세요.\n1. 일반 모드\n2. 테스트 모드\n";
	private static final String MENU_MESSAGE = "Q. 사용할 기능을 선택해주세요.\n1. 도서 등록\n2. 전체 도서 목록 조회\n3. 제목으로 도서 검색\n4. 도서 대여\n5. 도서 반납\n6. 도서 분실\n7. 도서 삭제";

	@Override
	public void printMode() {
		System.out.println(MODE_MESSAGE);
	}

	@Override
	public void printMenu() {
		System.out.println(MENU_MESSAGE);
		System.out.println();
		System.out.print("> ");
	}

	@Override
	public void printBookList(List<Book> bookList) {
		for (Book book : bookList) {
			System.out.println();
			System.out.println(book.toString());
			System.out.println("------------------------------");
		}
	}

	@Override
	public void printBookListByName(List<Book> bookList) {

	}

	@Override
	public void printResultMessage(String message) {

	}

	@Override
	public void printHeader(String message) {
		System.out.println();
		System.out.println("[System] " + message);
		System.out.println();
	}

	@Override
	public void printFooter(String message) {
		System.out.println();
		System.out.println("[System] " + message);
		System.out.println();
	}
}
