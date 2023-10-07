package org.example;

import org.example.client.Client;
import org.example.client.io.ConsoleIn;
import org.example.client.io.ConsoleOut;
import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.server.Server;

public class Application {
    public static void main(String[] args) {
        Client client = new Client(ConsoleOut.getInstance(), ConsoleIn.getInstance());
        Server server = new Server();
        try {
            server.setMode(client.scanMode());
            while (true) {
                RequestPacket requestPacket = client.scanMethod();
                ResponsePacket responsePacket = server.requestMethod(requestPacket);
                client.printResponse(responsePacket);
            }
        } catch (RuntimeException e) {
            System.err.print(System.lineSeparator() + ">> 시스템 에러 발생! 프로그램을 종료합니다." + System.lineSeparator());
            System.err.println(e);
        }
    }
}