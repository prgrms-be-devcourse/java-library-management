package java_library_management.repository;

import java_library_management.repository.Mode;

public class General implements Mode {

    @Override
    public void printStartMsg() {
        System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
    }
}
