package com.programmers.library.entity;

public class BookFixture {

	public static Book createSampleBook() {
		return new Book(1L, "Sample Title", "Sample Author", 150L);
	}

	public static Book createJavaBook() {
		return new Book(1L, "Java", "Joshua Bloch", 416L);
	}

	public static Book createPythonBook() {
		return new Book(2L, "Python", "Guido van Rossum", 160L);
	}

	public static Book createCustomBook(Long id, String title, String author, Long pages) {
		return new Book(id, title, author, pages);
	}

}
