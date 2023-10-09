package com.programmers.librarymanagement;

import com.programmers.librarymanagement.presentation.LibraryManagementController;
import com.programmers.librarymanagement.utils.ConsoleIo;

public class LibraryManagementApplication {

	public static void main(String[] args) {

		LibraryManagementController libraryManagementController = new LibraryManagementController(new ConsoleIo());

		libraryManagementController.start();
	}

}
