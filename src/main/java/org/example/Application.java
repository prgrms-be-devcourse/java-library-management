package org.example;

import org.example.client.Client;
import org.example.client.io.IO;
import org.example.packet.Request;
import org.example.server.Server;

public class Application {
    public static void main(String[] args) {
        IO io = new IO();
        try {
            Client.setIo(io);
            Server.requestMode(Client.scanMode());
            while (true) {
                Request request = Client.scanMenu();
                String response = Server.requestMethod(request);
                Client.printResponse(response);
            }
        } catch (RuntimeException e) {
            io.print("\n>> 두 번 이상의 유효하지 않은 값 입력으로 시스템을 종료합니다.\n");
        }
    }
}