package com.programmers.library.entity;

public class BookFixture {

	public static Book createSampleBook() {
		return new Book("Sample Title", "Sample Author", 150L);
	}

	public static Book createJavaBook() {
		return new Book("Java Programming", "Test Author", 111L);
	}

	public static Book createPythonBook() {
		return new Book("Python Programming", "Test Author", 111L);
	}

}
