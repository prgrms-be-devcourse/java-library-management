package com.programmers.library;

import com.programmers.library.controller.LibraryManagerController;
import com.programmers.library.io.ConsoleInput;
import com.programmers.library.io.ConsoleOutput;
import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.model.Mode;
import com.programmers.library.repository.FileRepository;
import com.programmers.library.repository.MemoryRepository;
import com.programmers.library.repository.Repository;
import com.programmers.library.service.LibarayManagerService;
import com.programmers.library.service.LibraryManagerServiceImpl;

public class Application {
	public static void main(String[] args) {
		Input input = new ConsoleInput();
		Output output = new ConsoleOutput();
		Mode mode = input.inputMode();
		Repository repository;
		repository = switch (mode) {
			case NORMAL_MODE -> new FileRepository();
			case TEST_MODE -> new MemoryRepository();
		};
		LibarayManagerService service = new LibraryManagerServiceImpl(repository);
		new LibraryManagerController(input, output, service).run();
	}
}