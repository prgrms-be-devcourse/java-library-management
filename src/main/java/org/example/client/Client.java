package org.example.client;

import org.example.client.console.MenuConsole;
import org.example.client.console.ModeConsole;
import org.example.connect.Request;
import org.example.type.ModeType;

public class Client {
    private Client() {

    }

    public static ModeType getMode() {
        return ModeConsole.scanType();
    }

    public static Request getMenu() {
        return MenuConsole.scanTypeAndInfo();
    }
}
