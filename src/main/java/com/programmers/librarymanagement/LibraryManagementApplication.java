package com.programmers.librarymanagement;

import com.programmers.librarymanagement.presentation.LibraryManagementController;
import com.programmers.librarymanagement.utils.ConsoleIo;
import com.programmers.librarymanagement.utils.CsvFileIo;

public class LibraryManagementApplication {

	public static void main(String[] args) {

		LibraryManagementController libraryManagementController = new LibraryManagementController(new ConsoleIo(), new CsvFileIo());
		libraryManagementController.start();
	}

}
