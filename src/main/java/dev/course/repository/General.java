package dev.course.repository;

/**
 * 일반 모드 구현체
 */

public class General implements Mode {

    @Override
    public void printStartMsg() {
        System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
    }
}
