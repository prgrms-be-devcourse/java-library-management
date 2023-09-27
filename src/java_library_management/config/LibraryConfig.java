package java_library_management.config;

import java_library_management.repository.Mode;
import java_library_management.manager.ConsoleManager;
import java_library_management.service.LibraryManagement;
import lombok.Builder;

public class LibraryConfig {

    private final ModeConfig modeConfig;
    private final CallbackConfig callbackConfig;
    private final ConsoleManager consoleManager;

    @Builder
    public LibraryConfig(ModeConfig modeConfig, CallbackConfig callbackConfig, ConsoleManager consoleManager) {
        this.modeConfig = modeConfig;
        this.callbackConfig = callbackConfig;
        this.consoleManager = consoleManager;
    }

    public void injectLibraryConfig(int modeId)  {
        this.modeConfig.injectMode(modeId);
        this.callbackConfig.injectCallback(this.modeConfig, modeId);
    }

    /*
    public LibraryManagement getLibraryManagement(LibraryConfig libraryConfig) {
        return new LibraryManagement(libraryConfig);
        // return new LibraryManagement(new LibraryConfig(this.modeConfig, this.callbackConfig, this.consoleManager));
       // return new LibraryManagement(this.mode, this.callbackConfig, this.consoleManager);
    }
    */

    public ModeConfig getModeConfig() {
        return this.modeConfig;
    }

    public CallbackConfig getCallbackConfig() {
        return this.callbackConfig;
    }

    public ConsoleManager getConsoleManager() {
        return this.consoleManager;
    }
}
