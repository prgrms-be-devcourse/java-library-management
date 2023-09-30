package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.client.console.ValidateException;
import org.example.client.io.IO;
import org.example.packet.Request;

// 클라이언트는 싱글톤으로 생성
// 기능 1: 사용자로부터 받은 모드 번호에 따라 모드명 매핑 후 서버에 모드명 전송
// 기능 2: 사용자로부터 받은 메뉴 번호에 따라 정보 수집 후 Request 인스턴스(메뉴명 + 메뉴에 따른 필요한 데이터)에 담아 서버에 전송
// 기능 3: 서버로부터 받은 응답(String)을 출력
public class Client {
    private static final IO io = new IO();

    private Client() {

    }

    public static String scanMode() {
        try {
            return ModeConsole.scanType(io);
        } catch (ValidateException e) {
            io.println(e.getMessage());
            return ModeConsole.scanType(io);
        }
    }// 사용자로부터 받은 모드 번호에 따라 모드명 매핑 후 서버에 모드명 전송

    public static Request scanMenu() {
        try {
            return MethodConsole.scanTypeAndInfo(io);
        } catch (ValidateException e) {
            io.println(e.getMessage());
            return MethodConsole.scanTypeAndInfo(io);
        }
    }// 사용자로부터 받은 메뉴 번호에 따라 정보 수집 후 Request 인스턴스(메뉴명 + 메뉴에 따른 필요한 데이터)에 담아 서버에 전송

    public static void printResponse(String response) {
        io.println(response);
    }
}
