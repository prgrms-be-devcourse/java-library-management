package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.packet.Request;

public class Client {
    private Client() {

    }

    public static String getMode() {
        return ModeConsole.scanType();
    }

    public static Request getMenu() {
        return MethodConsole.scanTypeAndInfo();
    }
}
