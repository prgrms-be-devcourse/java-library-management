package org.example.client.console;

import org.example.type.ModeType;

// 모드를 입력 받아서 enum과 매핑해주는 역할
// 모드를 입력받고 Controller에게 타입을 전달하고, 받은 String 결과를 출력
// "모드"만 아는 입출력 클래스
public class ModeConsole implements Console {

    public ModeType modeType;

    public ModeType scanType() {
        show();
        modeType = ModeType.valueOfNumber(scanner.nextInt()); // 매핑
        alert();
        return modeType;
    }

    @Override
    public void show() {
        System.out.print("Q. 모드를 선택해주세요.\n\n> ");
    }

    private void alert() {
        System.out.println(modeType.getModeName());
    }
}
