package com.programmers.app.instances;

import com.programmers.app.book.controller.BookController;
import com.programmers.app.book.controller.BookControllerImpl;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.repository.TestBookRepository;
import com.programmers.app.book.service.BookService;
import com.programmers.app.book.service.TestBookService;
import com.programmers.app.menu.MenuExecuter;
import com.programmers.app.menu.MenuExecuterImpl;
import com.programmers.app.menu.MenuSelector;
import com.programmers.app.menu.MenuSelectorImpl;
import com.programmers.app.mode.Mode;
import com.programmers.app.timer.TestTimerManager;
import com.programmers.app.timer.TimerManger;

public class SelectiveInstances {

    private final GeneralInstances generalInstances;
    private final MenuSelector menuSelector;
    private final MenuExecuter menuExecuter;

    public SelectiveInstances(GeneralInstances generalInstances, Mode mode) {
        this.generalInstances = generalInstances;
        this.menuSelector = generateMenuSelector();
        this.menuExecuter = generateMenuExecuter(mode);
    }

    private MenuSelector generateMenuSelector() {
        return new MenuSelectorImpl(generalInstances.getCommunicationAgent());
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

    private BookService generateBookSevice(Mode mode) {
        return new TestBookService(generateBookRepository(mode), generateTimerManager(mode));
    }

    private BookController generateBookController(Mode mode) {
        return new BookControllerImpl(generateBookSevice(mode), generalInstances.getCommunicationAgent());
    }

    private MenuExecuter generateMenuExecuter(Mode mode) {
        return new MenuExecuterImpl(generateBookController(mode));
    }

    public MenuExecuter getMenuExecuter() {
        return this.menuExecuter;
    }
}
