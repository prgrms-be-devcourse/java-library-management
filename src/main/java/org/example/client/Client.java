package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.packet.Request;

public class Client {
    private Client() {

    }

    public static String scanMode() {
        return ModeConsole.scanType();
    }

    public static Request scanMenu() {
        return MethodConsole.scanTypeAndInfo();
    }

    public static void printResponse(String response) {
        System.out.println(response);
    }
}
