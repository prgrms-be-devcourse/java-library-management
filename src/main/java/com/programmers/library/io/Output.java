package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public interface Output {
	void printSystemMessage(String message);
	void printWithLineBreak(String message);
	void printWithoutLineBreak(String message);
}
