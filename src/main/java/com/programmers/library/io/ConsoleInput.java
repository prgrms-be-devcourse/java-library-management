package com.programmers.library.io;

import java.util.List;

import com.programmers.library.entity.Book;

public class ConsoleInput implements Input{
	@Override
	public int inputMode() {
		return 0;
	}

	@Override
	public int inputMenu() {
		return 0;
	}

	@Override
	public int inputBookId() {
		return 0;
	}

	@Override
	public String inputBookName() {
		return null;
	}

	@Override
	public String inputBookAuthor() {
		return null;
	}

	@Override
	public String inputBookPages() {
		return null;
	}

	@Override
	public String inputBookTitle() {
		return null;
	}
}
