package java_library_management.config;

import java_library_management.repository.Mode;
import java_library_management.domain.Book;

import java.util.Map;
import java.util.function.BiConsumer;

public class CallbackConfig {

    private ModeConfig modeConfig;
    private BiConsumer<Map<Integer, Book>, String> loadCallback;
    private BiConsumer<Map<Integer, Book>, String> updateCallback;

    /**
     * 좋은 객체지향 설계를 위한 리팩토링을 하는 과정에서 최대한 setter의 사용을 지양하려고 노력했음
     * 설계 상의 문제점일지도 모르지만, 입력값 modeId에 따라 분기를 나누고, 필요한 인자를 전달해주는 책임이 한 곳에 있어서 때문인지 (추측)
     * setter 메서드의 사용을 완전히 제거할 수 없었음
     * 더 좋은 설계 방법이 있을 것 같은데 아직 생각해내지 못한 것 같음,,ㅠ
     */

    public CallbackConfig(ModeConfig modeConfig) {
        this.modeConfig = modeConfig;
    }

    public void setCallback(BiConsumer<Map<Integer, Book>, String> loadCallback, BiConsumer<Map<Integer, Book>, String> updateCallback) {
        this.loadCallback = loadCallback;
        this.updateCallback = updateCallback;
    }

    /**
     * 인자로 전달해야 할 콜백 함수를 지정해주기 위해, Mode 인터페이스에 접근해야 함 -> modeConfig 를 통해 접근
     * modeConfig 를 통해 콜백 함수를 전달해줌
     */
    public void injectCallback(int modeId) {

        Mode mode = this.modeConfig.getMode();
        if (modeId == 1) {
            setCallback(mode::load, mode::update);
        } else {
            setCallback(null, null);
        }
    }

    public BiConsumer<Map<Integer, Book>, String> getLoadCallback() {
        return this.loadCallback;
    }

    public BiConsumer<Map<Integer, Book>, String> getUpdateCallback() {
        return this.updateCallback;
    }

    public ModeConfig getModeConfig() {
        return this.modeConfig;
    }
}
