package java_library_management.config;

import java_library_management.repository.General;
import java_library_management.repository.Mode;
import java_library_management.repository.Tests;
import lombok.Builder;

public class ModeConfig {

    private Mode mode;

    /**
     * 구현하면서 가장 애먹었던 부분,,,
     * 입력값 modeId에 따라 적절한 구현체를 전달함
     * setter 를 제거할 수 있는 방안을 생각해내지 못했음,, -> 생성자로 대체함
     */

    /**
     * 1. 생성자에 modeId 가 들어가면 -> 테스트에서 인스턴스 생성할 때 곤란함
     * 2. 메서드에 modeId 가 들어가면 -> setter 메서드를 사용해야 함
     */

    public ModeConfig(int modeId) {
        if (modeId == 1) {
            this.mode = getGeneral();
        } else {
            this.mode = getTest();
        }
    }

    // 테스트 코드에서 Override 해서 Mode 구현체를 주입함
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
