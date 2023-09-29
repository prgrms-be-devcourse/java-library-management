package org.example;

import org.example.client.Client;
import org.example.packet.Request;
import org.example.server.Server;

// 애플리케이션 동작의 메인이 되는 곳
public class Application {
    public static void main(String[] args) {
        try {
            Server.setServer(Client.scanMode());
            while (true) {
                Request request = Client.scanMenu();
                String response = Server.requestMethod(request);
                Client.printResponse(response);
            }
        } catch (RuntimeException e) {
            System.err.print("\n>> 시스템 에러 발생! 프로그램을 종료합니다.\n");
        }
    }
}