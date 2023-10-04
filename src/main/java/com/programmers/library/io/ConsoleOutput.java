package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public class ConsoleOutput implements Output {

	@Override
	public void printSystemMessage(String message) {
		System.out.printf("%n[System] %s%n%n", message);
	}

	@Override
	public void printListBook(List<Book> books) {
		books.forEach(book -> {
			System.out.println(book);
			System.out.println("------------------------------");
		});
	}

	// @Override
	// public void printWithLineBreak(String message) {
	// 	System.out.println(message);
	// }

}
