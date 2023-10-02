package com.libraryManagement;

import com.libraryManagement.controller.BookController;
import com.libraryManagement.repository.FileRepository;
import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.service.BookService;
import com.libraryManagement.util.GlobalVariables;
import com.libraryManagement.view.BookMenu;
import com.libraryManagement.view.BookView;
import com.libraryManagement.view.ModeMenu;

import java.io.IOException;

import static com.libraryManagement.util.GlobalVariables.isSelectMenu;
import static com.libraryManagement.util.GlobalVariables.mode;

public class LibraryManagementApplication {

	public static void main(String[] args) throws IOException {

		while(true)
		{
			if(!isSelectMenu)
			{
				ModeMenu modeMenu = new ModeMenu();
				modeMenu.displayModeMenu();
				isSelectMenu = true;
			}
			else {
				Repository repository = null;

				if(mode.equals("일반 모드")){
					repository = new FileRepository();
				}else if(mode.equals("테스트 모드")) {
					repository = new MemoryRepository();
				}

				GlobalVariables globalVariables = new GlobalVariables(repository);

				BookMenu bookMenu = new BookMenu(new BookController(new BookService(repository), new BookView()));
				bookMenu.displayBookMenu();
			}
		}
	}
}