package org.example;

import org.example.client.Client;
import org.example.packet.Request;
import org.example.server.Server;

// 애플리케이션 동작의 메인이 되는 곳
public class Application {
    public static void main(String[] args) {
        try {
            Server.setServer(Client.scanMode()); // 1. 모드를 설정하고
            while (true) { // 2. 지속적으로 Reqeust, String(응답) 객체를 이용하여 데이터를 교환(HTTP 통신 비스무리하게 구현)
                Request request = Client.scanMenu();
                String response = Server.requestMethod(request);
                Client.printResponse(response);
            }
        } catch (RuntimeException e) {
            Server.saveData();
            System.err.print("\n>> 시스템 에러 발생! 프로그램을 종료합니다.\n");
        }
    }
}