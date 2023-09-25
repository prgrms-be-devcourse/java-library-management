package com.programmers.library.controller;

import com.programmers.library.io.Input;
import com.programmers.library.io.Output;
import com.programmers.library.service.LibarayManagerService;

public class LibraryManagerController implements Runnable {

	private final Input input;
	private final Output output;
	private final LibarayManagerService service;

	public LibraryManagerController(Input input, Output output, LibarayManagerService service) {
		this.input = input;
		this.output = output;
		this.service = service;
	}

	@Override
	public void run() {

	}


}
