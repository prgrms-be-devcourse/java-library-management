package com.programmers.library;

import java.util.List;
import java.util.Scanner;

import com.programmers.library.entity.Book;
import com.programmers.library.io.ConsoleInput;
import com.programmers.library.io.ConsoleOutput;
import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.repository.MemoryRepository;
import com.programmers.library.repository.Repository;

public class LibraryManager implements Runnable {

	private final Repository repository = new MemoryRepository();
	private Output output = new ConsoleOutput();
	private Input input = new ConsoleInput();

	@Override
	public void run() {
		// TODO select mode
		while(true) {
			output.printMenu();
			int menu = input.inputMenu();

			switch(menu) {
				//도서 등록
				case 1:
					output.printHeader("도서 등록 메뉴로 넘어갑니다.");
					repository.save(new Book(input.inputBookTitle(), input.inputBookAuthor(), input.inputBookPages()));
					output.printFooter("도서 등록이 완료되었습니다.");
					break;
				//전체 도서 목록 조회
				case 2:
					break;
				//제목으로 도서 검색
				case 3:
					break;
				//도서 대여
				case 4:
					break;
				//도서 반납
				case 5:
					break;
				//도서 분실
				case 6:
					break;
				//도서 삭제
				case 7:
					break;
			}

		}

	}




}
