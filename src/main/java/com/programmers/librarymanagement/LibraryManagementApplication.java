package com.programmers.librarymanagement;

import com.programmers.librarymanagement.application.LibraryManagementSystem;
import com.programmers.librarymanagement.io.ConsoleIO;

public class LibraryManagementApplication {

	public static void main(String[] args) {

		LibraryManagementSystem libraryManagementSystem = new LibraryManagementSystem(new ConsoleIO());
		libraryManagementSystem.start();
	}

}
