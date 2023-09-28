package org.example;

import org.example.client.console.MenuConsole;
import org.example.client.console.ModeConsole;
import org.example.client.connect.Request;
import org.example.server.ServerApplication;

public class Main {
    static String response;

    public static void main(String[] args) {
        ServerApplication.setModeType(ModeConsole.scanType());
        while (true) {
            Request request = MenuConsole.scanTypeAndInfo();
            response = ServerApplication.request(request); // 이름 수정 필요
            System.out.println(response);
        }
    }
}