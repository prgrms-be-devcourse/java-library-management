package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public class ConsoleOutput implements Output{

	@Override
	public void printBookList(List<Book> bookList) {
		for (Book book : bookList) {
			System.out.println();
			System.out.println(book.toString());
			System.out.println("------------------------------");
		}
	}

	@Override
	public void printResultMessage(String message) {
		System.out.println();
		System.out.println("[System] " + message);
		System.out.println();
	}

	@Override
	public void printHeader(String message) {
		System.out.println();
		System.out.println("[System] " + message);
	}

	@Override
	public void printFooter(String message) {
		System.out.println();
		System.out.println("[System] " + message);
		System.out.println();
	}
}
