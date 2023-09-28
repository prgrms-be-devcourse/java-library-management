package org.example;


import org.example.client.console.MenuConsole;
import org.example.client.console.ModeConsole;
import org.example.client.connect.Request;
import org.example.client.connect.RequestData;
import org.example.server.ServerApplication;

public class Main {
    static ModeConsole modeConsole = new ModeConsole();
    static MenuConsole menuConsole = new MenuConsole();
    static ServerApplication server = ServerApplication.getInstance();
    static String response;

    public static void main(String[] args) {
        server.setModeType(modeConsole.scanType());
        while (true) {
            Request request = new Request(menuConsole.scanType(), new RequestData(menuConsole.scanBookInfo())); // 수정 필요, 함수 실행 순서가 정해져 있음. 캡슐화 해버리자.
            response = server.request(request); // 이름 수정 필요
            System.out.println(response);
        }
    }
}