package java_library_management.config;

import java_library_management.manager.ConsoleManager;
import java_library_management.service.LibraryManagement;

public class AppConfig {

    public LibraryManagement getLibrary(int modeId) {
        return new LibraryManagement(getLibraryConfig(modeId));
    }

    public LibraryConfig getLibraryConfig(int modeId) {
        LibraryConfig config = new LibraryConfig(getModeConfig(), getCallbackConfig(), getConsoleManager());
        config.injectLibraryConfig(modeId);
        return config;
    }

    public ModeConfig getModeConfig() {
        return new ModeConfig();
    }

    public CallbackConfig getCallbackConfig() {
        return new CallbackConfig();
    }

    public ConsoleManager getConsoleManager() {
        return new ConsoleManager();
    }
}
