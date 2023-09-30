package com.programmers.app.config;

import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programmers.app.book.controller.BookController;
import com.programmers.app.book.controller.BookControllerImpl;
import com.programmers.app.book.domain.Book;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.repository.NormalBookRepository;
import com.programmers.app.book.repository.TestBookRepository;
import com.programmers.app.book.service.BookService;
import com.programmers.app.book.service.BookServiceImpl;
import com.programmers.app.file.BookFileManager;
import com.programmers.app.file.FileManager;
import com.programmers.app.file.TimerFileManager;
import com.programmers.app.menu.MenuExecuter;
import com.programmers.app.menu.MenuSelector;
import com.programmers.app.mode.Mode;
import com.programmers.app.timer.NormalTimerManager;
import com.programmers.app.timer.TestTimerManager;
import com.programmers.app.timer.Timer;
import com.programmers.app.timer.TimerManger;

public class AppConfig {

    private final InitialConfig initialConfig;
    private final MenuSelector menuSelector;
    private final MenuExecuter menuExecuter;

    public static final int MINUTES_FOR_BOOK_ARRANGEMENT = 5;

    public AppConfig(InitialConfig initialConfig, Mode mode) {
        this.initialConfig = initialConfig;
        mode.printSelected(initialConfig.getCommunicationAgent());
        this.menuSelector = generateMenuSelector();
        this.menuExecuter = generateMenuExecuter(mode);
    }

    private MenuSelector generateMenuSelector() {
        return new MenuSelector(initialConfig.getCommunicationAgent());
    }

    public MenuSelector getMenuSelector() {
        return menuSelector;
    }

    private FileManager<Map<Integer, Book>, List<Book>> generateBookFileManager() {
        String booksFilePath = "src/main/resources/books.json";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return new BookFileManager(booksFilePath, gson);
    }

    private FileManager<Queue<Timer>, Queue<Timer>> generateTimerFileManager() {
        String timerFilePath = "src/main/resources/timers.json";
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        return new TimerFileManager(timerFilePath, gson);
    }

    private BookRepository generateBookRepository(Mode mode)  {
        if (mode.equals(Mode.NORMAL)) {
            try {
                return new NormalBookRepository(generateBookFileManager());
            } catch (Exception e) {
                e.printStackTrace();
                this.initialConfig.getCommunicationAgent().printConfigError();
            }
        }
        return new TestBookRepository();
    }

    private TimerManger generateTimerManager(Mode mode) {
        if (mode.equals(Mode.NORMAL)) {
            try {
                return new NormalTimerManager(generateTimerFileManager());
            } catch (Exception e) {
                e.printStackTrace();
                this.initialConfig.getCommunicationAgent().printConfigError();
            }
        }

        return new TestTimerManager();
    }

    private BookService generateBookService(Mode mode) {
        try {
            return new BookServiceImpl(generateBookRepository(mode), generateTimerManager(mode));
        } catch (Exception e) {
            e.printStackTrace();
            this.initialConfig.getCommunicationAgent().printConfigError();
        }

        return new BookServiceImpl(new TestBookRepository(), new TestTimerManager());
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
