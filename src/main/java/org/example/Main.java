package org.example;

import org.example.client.Client;
import org.example.server.Server;

public class Main {
    static String response;

    public static void main(String[] args) {
        Server.requestMode(Client.getMode());
        while (true) {
            response = Server.requestMethod(Client.getMenu());
            System.out.println(response);
        }
    }
}