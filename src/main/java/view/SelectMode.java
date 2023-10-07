package view;

import domain.ModeType;
import message.ExecuteMessage;
import service.Mode;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum SelectMode {
    NORMAL_MODE(1) {
        @Override
        public Mode run() {
            System.out.println(ExecuteMessage.NORMAL_MODE.getMessage());
            return new Mode(ModeType.NORMAL_MODE);
        }
    },
    TEST_MODE(2) {
        @Override
        public Mode run() {
            System.out.println(ExecuteMessage.TEST_MODE.getMessage());
            return new Mode(ModeType.TEST_MODE);
        }
    };

    private static final Map<Integer, SelectMode> BY_STRING =
            Stream.of(values())
                    .collect(Collectors.toMap(SelectMode :: getSelectNum, e -> e));
    private final int selectNum;
    SelectMode(int selectNum) {
        this.selectNum = selectNum;
    }

    public int getSelectNum() {
        return this.selectNum;
    }

    public static SelectMode valueOfSelectNum(int selectNum) {
        return BY_STRING.get(selectNum);
    }

    public Mode run() {
        return new Mode(ModeType.NORMAL_MODE);
    }
}
