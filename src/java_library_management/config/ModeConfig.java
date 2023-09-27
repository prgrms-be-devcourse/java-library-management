package java_library_management.config;

import java_library_management.repository.General;
import java_library_management.repository.Mode;
import java_library_management.repository.Tests;

public class ModeConfig {

    private Mode mode;

    public void injectMode(int modeId) {

        if (modeId == 1) this.mode = getGeneral();
        else this.mode = getTest();
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return this.mode;
    }

    public Mode getGeneral() {
        return new General();
    }

    public Mode getTest() {
        return new Tests();
    }
}
