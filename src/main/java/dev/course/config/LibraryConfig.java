package dev.course.config;

import dev.course.manager.ConsoleManager;
import lombok.Builder;

public class LibraryConfig {

    private final ModeConfig modeConfig; // 어떤 Mode 구현체를 사용할 것인가
    private final CallbackConfig callbackConfig; // 어떤 콜백함수를 전달할 것인가
    private final ConsoleManager consoleManager;

    @Builder
    public LibraryConfig(ModeConfig modeConfig, CallbackConfig callbackConfig, ConsoleManager consoleManager) {
        this.modeConfig = modeConfig;
        this.callbackConfig = callbackConfig;
        this.consoleManager = consoleManager;
    }

    public CallbackConfig getCallbackConfig() {
        return this.callbackConfig;
    }

    public ConsoleManager getConsoleManager() {
        return this.consoleManager;
    }

    public ModeConfig getModeConfig() {
        return this.modeConfig;
    }
}
