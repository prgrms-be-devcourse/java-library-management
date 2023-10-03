package org.example;

import org.example.client.Client;
import org.example.packet.Request;
import org.example.server.Server;

public class Application {
    public static void main(String[] args) {
        Client client = new Client();
        Server server = new Server();
        try {
            server.setServer(client.scanMode());
            while (true) {
                Request request = client.scanMenu();
                String response = server.requestMethod(request);
                client.printResponse(response);
            }
        } catch (RuntimeException e) {
            server.saveData();
            System.err.print("\n>> 시스템 에러 발생! 프로그램을 종료합니다.\n");
            System.err.println(e);
        }
    }
}