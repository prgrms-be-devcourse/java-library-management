package java_library_management.config;

import java_library_management.repository.Mode;
import java_library_management.domain.Book;

import java.util.Map;
import java.util.function.BiConsumer;

public class CallbackConfig {

    private ModeConfig modeConfig;
    private BiConsumer<Map<Integer, Book>, String> loadCallback;
    private BiConsumer<Map<Integer, Book>, String> updateCallback;

    public void setModeConfig(ModeConfig modeConfig) {
        this.modeConfig = modeConfig;
    }

    public void setCallback(BiConsumer<Map<Integer, Book>, String> loadCallback, BiConsumer<Map<Integer, Book>, String> updateCallback) {
        this.loadCallback = loadCallback;
        this.updateCallback = updateCallback;
    }
    public void injectCallback(ModeConfig modeConfig, int modeId) {

        setModeConfig(modeConfig);
        if (modeId == 1) setCallback(modeConfig.getMode()::load, modeConfig.getMode()::update);
        else setCallback(null, null);
    }

    public BiConsumer<Map<Integer, Book>, String> getLoadCallback() {
        return this.loadCallback;
    }

    public BiConsumer<Map<Integer, Book>, String> getUpdateCallback() {
        return this.updateCallback;
    }
}
