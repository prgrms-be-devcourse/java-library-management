package com.programmers.library.controller;

import static com.programmers.library.constants.MessageConstants.*;

import com.programmers.library.enums.Mode;
import com.programmers.library.exception.InvalidModeException;
import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.repository.FileBookRepository;
import com.programmers.library.repository.MemoryBookRepository;
import com.programmers.library.repository.BookRepository;
import com.programmers.library.service.LibarayManagerService;
import com.programmers.library.service.LibraryManagerServiceImpl;

public class ModeConfigController implements Runnable {

	private final static String FILE_PATH = "src/main/resources/data.json";
	private final Input input;
	private final Output output;

	public ModeConfigController(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Mode mode = input.inputMode();
				BookRepository bookRepository = initializeRepository(mode, output);
				LibarayManagerService service = new LibraryManagerServiceImpl(bookRepository);
				new MenuController(input, output, service).run();
			} catch (Exception e) {
				output.printSystemMessage(e.getMessage());
			}
		}
	}

	private BookRepository initializeRepository(Mode mode, Output output) {
		switch (mode) {
			case NORMAL_MODE:
				output.printSystemMessage(START_NORMAL_MODE);
				return new FileBookRepository(FILE_PATH);
			case TEST_MODE:
				output.printSystemMessage(START_TEST_MODE);
				return new MemoryBookRepository();
			default:
				throw new InvalidModeException();
		}
	}

}
