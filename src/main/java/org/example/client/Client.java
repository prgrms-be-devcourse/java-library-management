package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.client.console.ValidateException;
import org.example.client.io.IO;
import org.example.packet.Request;

public class Client {
    private final IO io = new IO();

    public String scanMode() {
        try {
            return ModeConsole.scanType(io);
        } catch (ValidateException e) {
            io.println(e.getMessage());
            return ModeConsole.scanType(io);
        }
    }

    public Request scanMenu() {
        try {
            return MethodConsole.scanTypeAndInfo(io);
        } catch (ValidateException e) {
            io.println(e.getMessage());
            return MethodConsole.scanTypeAndInfo(io);
        }
    }

    public void printResponse(String response) {
        io.println(response);
    }
}
