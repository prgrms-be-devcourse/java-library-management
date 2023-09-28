package org.example;

import org.example.client.Client;
import org.example.server.Server;

public class Main {
    public static void main(String[] args) {
        Server.requestMode(Client.scanMode()); // 입력값 클라이언트에서 확인, 서버는 Data 관련 예외 던진다.
        while (true) {
            Client.printResponse(Server.requestMethod(Client.scanMenu())); // 입력값 클라이언트에서 확인, 서버는 Data 관련 예외 던진다.
        }
    }
}