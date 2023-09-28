package org.example.client.type;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ClientMode {
    COMMON(1, "1. 일반 모드\n", "\n[System] 일반 모드로 애플리케이션을 실행합니다.\n\n"),
    TEST(2, "2. 테스트 모드\n", "\n[System] 테스트 모드로 애플리케이션을 실행합니다.\n\n");

    private static final Map<Integer, ClientMode> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(ClientMode::getNum, Function.identity()));

    public static ClientMode valueOfNumber(int num) {
        return BY_NUMBER.get(num);
    }

    public static final String MODE_CONSOLE = "\nQ. 모드를 선택해주세요.\n"
            + String.join("", Stream.of(values()).map(type -> type.name).toArray(String[]::new)) + "\n> ";

    private final int num;
    private final String name;
    public final String alert;

    ClientMode(int num, String name, String alert) {
        this.num = num;
        this.name = name;
        this.alert = alert;
    }

    public int getNum() {
        return num;
    }
}