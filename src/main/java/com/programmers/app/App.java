package com.programmers.app;

import com.programmers.app.instances.GeneralInstances;
import com.programmers.app.instances.SelectiveInstances;
import com.programmers.app.menu.Menu;
import com.programmers.app.mode.Mode;

public class App {

    private final GeneralInstances generalInstances;
    private final Mode mode;
    private final SelectiveInstances selectiveInstances;

    public App(GeneralInstances generalInstances) {
        this.generalInstances = generalInstances;
        mode = generalInstances.getModeSelector().select();
        selectiveInstances = new SelectiveInstances(generalInstances, mode);
    }

    public void run() {
        Menu menu;
        while (true) {
            menu = selectiveInstances.getMenuSelector().select();
            selectiveInstances.getMenuExecuter().execute(menu);
        }
    }
}
