package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.example.client.io.ConsoleIO;
import org.example.client.io.IO;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModeConsole {

    private final IO IO = ConsoleIO.getInstance();

    public String scanType() {
        try {
            IO.print(Type.BASIC_QUESTION);
            int selectNum = Validator.validateSelectNum(Type.values().length, IO.scanLine());
            Type type = Type.valueOfNumber(selectNum);
            IO.println(type.START_MESSAGE);
            return type.name();
        } catch (ValidateException e) {
            IO.println(e.getMessage());
            IO.print(Type.BASIC_QUESTION);
            int selectNum = Validator.validateSelectNum(Type.values().length, IO.scanLine());
            Type type = Type.valueOfNumber(selectNum);
            IO.println(type.START_MESSAGE);
            return type.name();
        }
    }

    private enum Type {
        COMMON(1, "일반 모드", System.lineSeparator() + "[System] 일반 모드로 애플리케이션을 실행합니다." + System.lineSeparator()),
        TEST(2, "테스트 모드", System.lineSeparator() + "[System] 테스트 모드로 애플리케이션을 실행합니다." + System.lineSeparator());

        private static final String BASIC_QUESTION = "Q. 모드를 선택해주세요." + System.lineSeparator()
                + String.join(System.lineSeparator(), Stream.of(values()).map(type -> type.NUMBER + ". " + type.NAME).toArray(String[]::new)) + System.lineSeparator() + "> ";
        private static final Map<Integer, Type> BY_NUMBER =
                Stream.of(values()).collect(Collectors.toMap(Type::getNum, Function.identity()));
        private final String START_MESSAGE;
        private final int NUMBER;
        private final String NAME;

        Type(int num, String name, String startMessage) {
            this.NUMBER = num;
            this.NAME = name;
            this.START_MESSAGE = startMessage;
        }

        private static Type valueOfNumber(int num) {
            return BY_NUMBER.get(num);
        }

        private int getNum() {
            return NUMBER;
        }
    }
}
