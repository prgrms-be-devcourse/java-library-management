package com.programmers.library;

import com.programmers.library.controller.ModeConfigController;
import com.programmers.library.io.ConsoleInput;
import com.programmers.library.io.ConsoleOutput;
import com.programmers.library.io.Input;
import com.programmers.library.io.Output;

public class Application {
	public static void main(String[] args) {
		Input input = new ConsoleInput();
		Output output = new ConsoleOutput();
		new ModeConfigController(input, output).run();
	}
}