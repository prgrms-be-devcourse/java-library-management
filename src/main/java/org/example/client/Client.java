package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.client.console.ValidateException;
import org.example.client.io.IO;
import org.example.packet.Request;

public class Client {
    private static IO io;

    private Client() {

    }

    public static void setIo(IO io) {
        Client.io = io;
    }

    public static String scanMode() {
        try {
            return ModeConsole.scanType(io);
        } catch (ValidateException e) {
            io.print(e.getMessage());
            return ModeConsole.scanType(io);
        }
    }

    public static Request scanMenu() {
        try {
            return MethodConsole.scanTypeAndInfo(io);
        } catch (ValidateException e) {
            io.print(e.getMessage());
            return MethodConsole.scanTypeAndInfo(io);
        }
    }

    public static void printResponse(String response) {
        io.print(response);
    }
}
