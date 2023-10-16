package com.libraryManagement;

import com.libraryManagement.io.ModeMenu;
import com.libraryManagement.repository.FileRepository;
import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.util.AppConfig;
import javax.swing.DefaultDesktopManager;

public class LibraryManagementApplication {
	private static final AppConfig appConfig = new AppConfig();

	public static void main(String[] args) throws Exception {

		ModeMenu modeMenu = new ModeMenu();
		modeMenu.displayModeMenu();

		Repository repository = null;
		if(modeMenu.getSelectMode().equals("일반 모드")){
			repository = new FileRepository();
		}else if(modeMenu.getSelectMode().equals("테스트 모드")) {
			repository = new MemoryRepository();
		}

		appConfig.init(repository);

		appConfig.getBookController().startBookMenu();
	}
}
