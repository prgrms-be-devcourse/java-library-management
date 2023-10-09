package com.programmers.app;

import com.programmers.app.config.InitialConfig;
import com.programmers.app.config.AppConfig;
import com.programmers.app.menu.Menu;
import com.programmers.app.mode.Mode;

public class Librarian {

    private final AppConfig appConfig;

    public Librarian(InitialConfig initialConfig) {
        Mode mode = initialConfig.getModeSelector().select();
        appConfig = new AppConfig(initialConfig, mode);
    }

    public void work() {
        boolean isRunning = true;
        while (isRunning) {
            Menu menu = appConfig.getMenuSelector().select();
            isRunning = appConfig.getMenuExecutor().execute(menu);
        }
    }
}
