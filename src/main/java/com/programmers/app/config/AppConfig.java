package com.programmers.app.config;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.programmers.app.book.controller.BookController;
import com.programmers.app.book.controller.BookControllerImpl;
import com.programmers.app.book.domain.Book;
import com.programmers.app.book.repository.BookRepository;
import com.programmers.app.book.repository.NormalBookRepository;
import com.programmers.app.book.repository.TestBookRepository;
import com.programmers.app.book.service.BookService;
import com.programmers.app.file.BookFileManager;
import com.programmers.app.file.FileManager;
import com.programmers.app.menu.MenuExecutor;
import com.programmers.app.menu.MenuSelector;
import com.programmers.app.mode.Mode;

public class AppConfig {

    private final InitialConfig initialConfig;
    private final MenuSelector menuSelector;
    private final MenuExecutor menuExecutor;

    private static final String FILE_PATH = "src/main/resources/books.json";

    public AppConfig(InitialConfig initialConfig, Mode mode) {
        this.initialConfig = initialConfig;
        mode.printSelected(initialConfig.getCommunicationAgent());
        this.menuSelector = generateMenuSelector();
        this.menuExecutor = generateMenuExecutor(mode);
    }

    private MenuSelector generateMenuSelector() {
        return new MenuSelector(initialConfig.getCommunicationAgent());
    }

    public MenuSelector getMenuSelector() {
        return menuSelector;
    }

    private FileManager<HashMap<Integer, Book>, List<Book>> generateBookFileManager() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();
        return new BookFileManager(FILE_PATH, gson);
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

    private BookService generateBookService(Mode mode) {
        try {
            return new BookService(generateBookRepository(mode));
        } catch (Exception e) {
            e.printStackTrace();
            this.initialConfig.getCommunicationAgent().printConfigError();
        }

        return new BookService(new TestBookRepository());
    }

    private BookController generateBookController(Mode mode) {
        return new BookControllerImpl(generateBookService(mode), initialConfig.getCommunicationAgent());
    }

    private MenuExecutor generateMenuExecutor(Mode mode) {
        return new MenuExecutor(generateBookController(mode));
    }

    public MenuExecutor getMenuExecutor() {
        return this.menuExecutor;
    }
}
