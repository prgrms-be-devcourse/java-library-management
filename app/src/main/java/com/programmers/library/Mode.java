package com.programmers.library;

import com.programmers.library.controller.Controller;
import com.programmers.library.repository.FileRepository;
import com.programmers.library.repository.TestRepository;
import com.programmers.library.service.Service;

import java.util.Arrays;

public enum Mode {
    NORMAL(1, "일반", new Controller(new Service(new FileRepository()))),
    TEST(2, "테스트", new Controller(new Service(new TestRepository())));

    private final int id;
    private final String name;
    private final Controller controller;

    Mode(int id, String name, Controller controller) {
        this.id = id;
        this.name = name;
        this.controller = controller;
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
        return Arrays.stream(values())
                .filter(mode -> mode.id == id)
                .findFirst()
                .orElse(NORMAL);
    }
}
