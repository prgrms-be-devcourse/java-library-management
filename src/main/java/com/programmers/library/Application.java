package com.programmers.library;

import com.programmers.library.controller.LibraryManagerController;
import com.programmers.library.io.Console;
import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.repository.MemoryRepository;
import com.programmers.library.repository.Repository;
import com.programmers.library.service.LibarayManagerService;
import com.programmers.library.service.LibraryManagerServiceImpl;

public class Application {
	public static void main(String[] args) {
		Input input = new Console();
		Output output = new Console();
		Repository repository = new MemoryRepository(); //todo : select mode
		LibarayManagerService service = new LibraryManagerServiceImpl(repository);
		new LibraryManagerController(input, output, service).run();
	}
}