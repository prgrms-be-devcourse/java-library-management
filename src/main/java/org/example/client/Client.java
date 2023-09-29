package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.client.io.IO;
import org.example.packet.Request;

public class Client {
    private static final IO io = new IO();

    private Client() {

    }

    public static String scanMode() {
        return ModeConsole.scanType(io);
    }

    public static Request scanMenu() {
        return MethodConsole.scanTypeAndInfo(io);
    }

    public static void printResponse(String response) {
        io.print(response);
    }
}
