package com.programmers.library.io;

public class ConsoleOutput implements Output {

	@Override
	public void printSystemMessage(String message) {
		System.out.printf("%n[System] %s%n%n", message);
	}

	@Override
	public void printWithLineBreak(String message) {
		System.out.println(message);
	}

}
