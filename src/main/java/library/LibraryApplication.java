package library;

import library.book.manager.LibraryContextLoader;
import library.book.presentation.BookController;

public class LibraryApplication {

	public static void main(String[] args) {

		BookController controller = LibraryContextLoader.assembleBookController();

		while (true) {
			controller.run();
		}
	}
}
