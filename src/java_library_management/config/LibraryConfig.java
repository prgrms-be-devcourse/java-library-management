package java_library_management.config;

import java_library_management.repository.Mode;
import java_library_management.manager.ConsoleManager;
import java_library_management.service.LibraryManagement;
import lombok.Builder;

/**
 * LibraryConfig 는 ModeConfig / CallbackConfig, ConsoleManager 를 아우르는 클래스
 * injectLibraryConfig 메서드를 통해서
 * 1. modeId에 맞는 Mode 구현체를 주입받음
 * 2. 해당 모드에 필요한 콜백함수에 적절한 인자를 전달해줌
 */

public class LibraryConfig {

    private ModeConfig modeConfig;
    private final CallbackConfig callbackConfig; // 어떤 콜백함수를 전달할 것인가
    private final ConsoleManager consoleManager;

    @Builder
    public LibraryConfig(ModeConfig modeConfig, CallbackConfig callbackConfig, ConsoleManager consoleManager) {
        this.modeConfig = modeConfig;
        this.callbackConfig = callbackConfig;
        this.consoleManager = consoleManager;
    }

    /**
     * 1. injectMode -> 입력값 modeId에 따라 적절한 Mode 구현체를 전달
     * 2. injectCallback -> 입력값 modeId에 따라 적절한 콜백 함수를 전달
     */

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
