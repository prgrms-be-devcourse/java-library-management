package org.example.client.console;

import org.example.client.io.IO;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModeConsole {
    private ModeConsole() {
    }

    public static String scanType(IO io) {
        io.print(ModeType.MODE_CONSOLE);
        int selectNum = Validator.validateSelectNum(ModeType.values().length, io.scanLine());
        ModeType modeType = ModeType.valueOfNumber(selectNum);
        io.println(modeType.alert);
        return modeType.name();
    }

    public enum ModeType {
        COMMON(1, "1. 일반 모드", System.lineSeparator() + "[System] 일반 모드로 애플리케이션을 실행합니다." + System.lineSeparator()),
        TEST(2, "2. 테스트 모드", System.lineSeparator() + "[System] 테스트 모드로 애플리케이션을 실행합니다." + System.lineSeparator());

        public static final String MODE_CONSOLE = System.lineSeparator() + "Q. 모드를 선택해주세요." + System.lineSeparator()
                + String.join("", Stream.of(values()).map(type -> type.name + System.lineSeparator()).toArray(String[]::new)) + System.lineSeparator() + "> ";
        private static final Map<Integer, ModeType> BY_NUMBER =
                Stream.of(values()).collect(Collectors.toMap(ModeType::getNum, Function.identity()));
        public final String alert;
        private final int num;
        private final String name;

        ModeType(int num, String name, String alert) {
            this.num = num;
            this.name = name;
            this.alert = alert;
        }

        public static ModeType valueOfNumber(int num) {
            return BY_NUMBER.get(num);
        }

        public int getNum() {
            return num;
        }
    }
}
