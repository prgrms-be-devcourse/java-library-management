package dev.course.config;

import dev.course.manager.ConsoleManager;
import dev.course.manager.JSONFileManager;
import lombok.Builder;

public class LibraryConfig {

    private final BookRepositoryConfig bookRepositoryConfig;
    private final ConsoleManager consoleManager;

    @Builder
    public LibraryConfig(BookRepositoryConfig bookRepositoryConfig, ConsoleManager consoleManager) {
        this.bookRepositoryConfig = bookRepositoryConfig;
        this.consoleManager = consoleManager;
    }

    public ConsoleManager getConsoleManager() {
        return this.consoleManager;
    }

    public BookRepositoryConfig getBookRepositoryConfig() {
        return this.bookRepositoryConfig;
    }
}
