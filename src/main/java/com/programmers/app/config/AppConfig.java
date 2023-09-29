package com.programmers.app.config;

import com.programmers.app.book.controller.BookController;
import com.programmers.app.book.controller.BookControllerImpl;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.repository.TestBookRepository;
import com.programmers.app.book.service.BookService;
import com.programmers.app.book.service.TestBookService;
import com.programmers.app.menu.MenuExecuter;
import com.programmers.app.menu.MenuSelector;
import com.programmers.app.mode.Mode;
import com.programmers.app.timer.TestTimerManager;
import com.programmers.app.timer.TimerManger;

public class AppConfig {

    private final InitialConfig initialConfig;
    private final MenuSelector menuSelector;
    private final MenuExecuter menuExecuter;

    public AppConfig(InitialConfig initialConfig, Mode mode) {
        this.initialConfig = initialConfig;
        this.menuSelector = generateMenuSelector();
        this.menuExecuter = generateMenuExecuter(mode);
    }

    private MenuSelector generateMenuSelector() {
        return new MenuSelector(initialConfig.getCommunicationAgent());
    }

    public MenuSelector getMenuSelector() {
        return menuSelector;
    }

    private BookRepository generateBookRepository(Mode mode) {
        if (mode != Mode.TEST) System.out.println("Temoporarily testing");
        return new TestBookRepository();
    }

    private TimerManger generateTimerManager(Mode mode) {
        if (mode != Mode.TEST) System.out.println("Temporarily testing");
        return new TestTimerManager();
    }

    private BookService generateBookService(Mode mode) {
        return new TestBookService(generateBookRepository(mode), generateTimerManager(mode));
    }

    private BookController generateBookController(Mode mode) {
        return new BookControllerImpl(generateBookService(mode), initialConfig.getCommunicationAgent());
    }

    private MenuExecuter generateMenuExecuter(Mode mode) {
        return new MenuExecuter(generateBookController(mode));
    }

    public MenuExecuter getMenuExecuter() {
        return this.menuExecuter;
    }
}
