package org.example;

import org.example.client.Client;
import org.example.packet.Request;
import org.example.server.Server;

public class Application {
    public static void main(String[] args) {
        Server.requestMode(Client.scanMode()); // 입력값 클라이언트에서 확인, 서버는 Data 관련 예외 던진다.
        while (true) {
            Request request = Client.scanMenu();
            String response = Server.requestMethod(request); // Response Class도 생각중
            Client.printResponse(response); // 입력값 클라이언트에서 확인, 서버는 Data 관련 예외 던진다.
        }
    }
}