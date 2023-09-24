package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public interface Output {
	void printBookList(List<Book> bookList);
	void printResultMessage(String message);
	void printHeader(String s);
	void printFooter(String s);
}
