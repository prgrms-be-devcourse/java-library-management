package org.example.client.console;

import org.example.client.io.IO;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* Q. 모드와 기타 정보들을 매핑하고 싶어서 enum으로 정보를 저장했는데,
    enum안 함수에서 입출력을 담당하는 io를 어떻게 공유하면 좋을지 잘 생각이 나지 않아
    scanType() 메서드에서 파라미터 값으로 IO 정보를 전하게 되었습니다.
    아래와 같은 경우에 enum을 적극활용하는 것이 좋을지, 더 좋은 방법이 있을지 궁금합니다!
    MethodConsole 클래스도 구조가 ModeConsole와 동일 합니다. */

// "모드"만 아는 클래스
// io를 전달 받아 사용자로부터 모드를 입력 받고 enum과 매핑
// 매핑 후 사용자가 선택한 모드 타입을 반환하는 역할
public class ModeConsole {
    public enum ModeType {
        COMMON(1, "1. 일반 모드\n", "\n[System] 일반 모드로 애플리케이션을 실행합니다.\n\n"),
        TEST(2, "2. 테스트 모드\n", "\n[System] 테스트 모드로 애플리케이션을 실행합니다.\n\n");

        private static final Map<Integer, ModeType> BY_NUMBER =
                Stream.of(values()).collect(Collectors.toMap(ModeType::getNum, Function.identity()));

        public static ModeType valueOfNumber(int num) {
            return BY_NUMBER.get(num);
        }

        public static final String MODE_CONSOLE = "\nQ. 모드를 선택해주세요.\n"
                + String.join("", Stream.of(values()).map(type -> type.name).toArray(String[]::new)) + "\n> ";

        private final int num;
        private final String name;
        public final String alert;

        ModeType(int num, String name, String alert) {
            this.num = num;
            this.name = name;
            this.alert = alert;
        }

        public int getNum() {
            return num;
        }
    }

    private ModeConsole() {
    }

    public static String scanType(IO io) {
        io.print(ModeType.MODE_CONSOLE);
        int selectNum = Validator.validateSelectNum(ModeType.values().length, io.scanLine()); //숫자 & 범위 체크
        ModeType modeType = ModeType.valueOfNumber(selectNum);
        io.print(modeType.alert);
        return modeType.name();
    }
}
