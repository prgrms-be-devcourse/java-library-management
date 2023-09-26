package library;

import library.book.manager.LibraryLoader;
import library.book.presentation.BookController;

public class LibraryApplication {

	public static void main(String[] args) {

		BookController controller = LibraryLoader.assembleBookController();

		while (true) {
			controller.run();
		}
	}
}
