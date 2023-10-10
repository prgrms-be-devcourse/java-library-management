package com.libraryManagement;

import com.libraryManagement.util.AppConfig;

public class LibraryManagementApplication {
	private static final AppConfig appConfig = new AppConfig();

	public static void main(String[] args) throws Exception {

		appConfig.init();
	}
}