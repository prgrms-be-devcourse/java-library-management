package com.programmers.library.controller;

import static com.programmers.library.constants.MessageConstants.*;

import com.programmers.library.exception.InvalidModeException;
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

public class ModeController implements Runnable {

	@Override
	public void run() {
		Input input = new ConsoleInput();
		Output output = new ConsoleOutput();
		while(true) {
			try {
				Mode mode = input.inputMode();
				Repository repository = initializeRepository(mode, output);
				LibarayManagerService service = new LibraryManagerServiceImpl(repository);
				new MenuController(input, output, service).run();
			} catch (Exception e) {
				output.printSystemMessage(e.getMessage());
			}
		}
	}

	private Repository initializeRepository(Mode mode, Output output) {
		switch (mode) {
			case NORMAL_MODE:
				output.printSystemMessage(START_NORMAL_MODE);
				return new FileRepository();
			case TEST_MODE:
				output.printSystemMessage(START_TEST_MODE);
				return new MemoryRepository();
			default:
				throw new InvalidModeException();
		}
	}

}
