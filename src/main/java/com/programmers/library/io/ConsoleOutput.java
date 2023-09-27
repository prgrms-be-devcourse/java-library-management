package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public class ConsoleOutput implements Output{

	@Override
	public void printSystemMessage(String message) {
		System.out.println("[System] " + message);
	}

	@Override
	public void printWithLineBreak(String message) {
		System.out.println(message);
	}

	@Override
	public void printWithoutLineBreak(String message) {
		System.out.print(message);
	}

}
