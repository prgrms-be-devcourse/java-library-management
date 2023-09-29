package com.programmers.app;

import com.programmers.app.config.InitialConfig;
import com.programmers.app.config.AppConfig;
import com.programmers.app.menu.Menu;
import com.programmers.app.mode.Mode;

public class App {

    private final Mode mode;
    private final AppConfig appConfig;

    public App(InitialConfig initialConfig) {
        mode = initialConfig.getModeSelector().select();
        appConfig = new AppConfig(initialConfig, mode);
    }

    public void run() {
        Menu menu;
        while (true) {
            menu = appConfig.getMenuSelector().select();
            appConfig.getMenuExecuter().execute(menu);
        }
    }
}
