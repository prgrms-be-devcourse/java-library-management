package com.programmers.library;

import com.programmers.library.controller.Controller;
import com.programmers.library.repository.FileRepository;
import com.programmers.library.repository.TestRepository;
import com.programmers.library.service.Service;

public enum Mode {
    NORMAL(1, "일반"),
    TEST(2, "테스트");

    private final int id;
    private final String name;
    private final Controller controller;

    Mode(int id, String name) {
        this.id = id;
        this.name = name;
        this.controller = id == 2 ? new Controller(new Service(new TestRepository())) : new Controller(new Service(new FileRepository()));
    }

    public void run() {
        System.out.println("[System] " + name + " 모드로 애플리케이션을 실행합니다.");
        controller.run();
    }

    public static void showModes() {
        System.out.println("Q. 모드를 선택해주세요.");
        for (Mode mode : Mode.values()) {
            System.out.println((mode.id + ". " + mode.name));
        }
        System.out.print("> ");
    }

    public static Mode selectMode(int id) {
        if (id == 2) {
            return Mode.TEST;
        }
        return Mode.NORMAL;
    }
}
