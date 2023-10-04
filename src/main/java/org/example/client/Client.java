package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.responsePacket.ResponsePacket;

public class Client {
    private final ModeConsole modeConsole = new ModeConsole();
    private final MethodConsole methodConsole = new MethodConsole();

    public String scanMode() {
        return modeConsole.scanType();
    }

    public RequestPacket scanMethod() {
        return methodConsole.scanTypeAndInfo();
    }

    public void printResponse(ResponsePacket responsePacket) {
        methodConsole.printResponse(responsePacket);
    }
}
