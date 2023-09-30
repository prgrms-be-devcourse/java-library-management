package domain;

import message.ExecuteMessage;
import service.Mode;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SelectMode {
    NORMAL_MODE("1") {
        @Override
        public Mode run() throws IOException {
            System.out.println(ExecuteMessage.NORMAL_MODE);
            return new Mode(ModeType.NORMAL_MODE);
        }
    },
    TEST_MODE("2") {
        @Override
        public Mode run() throws IOException {
            System.out.println(ExecuteMessage.TEST_MODE);
            return new Mode(ModeType.TEST_MODE);
        }
    };

    private static final Map<String, SelectMode> BY_STRING =
            Stream.of(values())
                    .collect(Collectors.toMap(SelectMode :: getSelectNum, e -> e));
    private final String selectNum;
    SelectMode(String selectNum) {
        this.selectNum = selectNum;
    }

    public String getSelectNum() {
        return this.selectNum;
    }

    public static SelectMode valueOfSelectNum(String selectNum) {
        return BY_STRING.get(selectNum);
    }

    public Mode run() throws IOException {
        return new Mode(ModeType.NORMAL_MODE);
    }
}
