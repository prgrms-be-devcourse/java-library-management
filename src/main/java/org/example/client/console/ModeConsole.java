package org.example.client.console;

import org.example.client.type.ClientMode;

// 모드를 입력 받아서 enum과 매핑해주는 역할
// 모드를 입력받고 Controller에게 타입을 전달하고, 받은 String 결과를 출력
// "모드"만 아는 입출력 클래스
public class ModeConsole implements Console {
    public static ClientMode clientMode;

    private ModeConsole() {
    }

    public static String scanType() {
        System.out.print(ClientMode.MODE_CONSOLE);
        clientMode = ClientMode.valueOfNumber(Integer.parseInt(scanner.nextLine())); //숫자 & 범위 체크
        System.out.print(clientMode.getAlert());
        return clientMode.name();
    }
}
