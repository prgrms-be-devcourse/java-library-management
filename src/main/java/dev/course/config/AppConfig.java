package dev.course.config;

import dev.course.manager.ConsoleManager;
import dev.course.service.LibraryManagement;

public class AppConfig {

    public LibraryManagement getLibrary(int modeId) {
        return new LibraryManagement(getLibraryConfig(modeId));
    }

    public LibraryConfig getLibraryConfig(int modeId) {
        return new LibraryConfig(getRepositoryConfig(modeId), getConsoleManager());
    }

    public BookRepositoryConfig getRepositoryConfig(int modeId) {
        return new BookRepositoryConfig(modeId);
    }

    public ConsoleManager getConsoleManager() {
        return new ConsoleManager();
    }
}
