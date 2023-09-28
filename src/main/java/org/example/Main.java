package org.example;

import org.example.client.Client;
import org.example.server.ServerApplication;

public class Main {
    static String response;

    public static void main(String[] args) {
        ServerApplication.setModeType(Client.getMode());
        while (true) {
            response = ServerApplication.request(Client.getMenu());
            System.out.println(response);
        }
    }
}