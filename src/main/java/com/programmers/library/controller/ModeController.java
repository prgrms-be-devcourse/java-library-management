package com.programmers.library.controller;

import static com.programmers.library.constants.MessageConstants.*;

import com.programmers.library.exception.InvalidModeException;
import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.enums.Mode;
import com.programmers.library.repository.FileRepository;
import com.programmers.library.repository.MemoryRepository;
import com.programmers.library.repository.Repository;
import com.programmers.library.service.LibarayManagerService;
import com.programmers.library.service.LibraryManagerServiceImpl;

public class ModeController implements Runnable {

	private final Input input;
	private final Output output;
	private final static String FILE_PATH = "src/main/resources/data.json";

	public ModeController(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
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
				return new FileRepository(FILE_PATH);
			case TEST_MODE:
				output.printSystemMessage(START_TEST_MODE);
				return new MemoryRepository();
			default:
				throw new InvalidModeException();
		}
	}

}
