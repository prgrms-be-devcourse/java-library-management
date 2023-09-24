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
					output.printHeader("전체 도서 목록입니다.");
					List<Book> bookList = repository.findAll();
					output.printBookList(bookList);
					output.printFooter("도서 목록 끝");
					break;
				//제목으로 도서 검색
				case 3:
					output.printHeader("제목으로 도서 검색 메뉴로 넘어갑니다.");
					List<Book> bookResult = repository.findByTitleLike(input.inputBookTitleSearch());
					output.printBookList(bookResult);
					output.printFooter("검색된 도서 끝");
					break;
				//도서 대여
				case 4:
					output.printHeader("도서 대여 메뉴로 넘어갑니다.");
					long bookId = input.inputBookId();
					try {
						Book book = repository.findById(bookId).orElseThrow(() -> new IllegalArgumentException("해당 ID의 도서가 존재하지 않습니다."));
						if(book.isAvailable()) {
							book.borrow();
							repository.save(book);
							output.printResultMessage("도서가 대여 처리 되었습니다.");
						} else if(book.isBorrowed()) {
							output.printResultMessage("이미 대여중인 도서입니다.");
						} else if(book.isLost()) {
							output.printResultMessage("분실된 도서입니다.");
						} else if(book.isOrganizing()) {
							output.printResultMessage("정리 중인 도서입니다.");
						}
					} catch (IllegalArgumentException e) {
						output.printResultMessage(e.getMessage());
					}
					break;
				//도서 반납
				case 5:
					output.printHeader("도서 반납 메뉴로 넘어갑니다.");
					break;
				//도서 분실
				case 6:
					output.printHeader("도서 분실 처리 메뉴로 넘어갑니다.");
					break;
				//도서 삭제
				case 7:
					output.printHeader("도서 삭제 처리 메뉴로 넘어갑니다.");
					break;
			}

		}

	}


}
