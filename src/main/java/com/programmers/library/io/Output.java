package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public interface Output {
	void printMode();
	void printMenu();
	void printBookList(List<Book> bookList);
	void printBookListByName(List<Book> bookList);
	void printResultMessage(String message);
}
