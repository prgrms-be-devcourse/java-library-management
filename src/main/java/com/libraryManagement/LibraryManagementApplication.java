package com.libraryManagement;

import com.libraryManagement.util.AppConfig;

import java.io.IOException;

public class LibraryManagementApplication {
	private static final AppConfig appConfig = new AppConfig();

	public static void main(String[] args) throws IOException {

		appConfig.init();

	}

}