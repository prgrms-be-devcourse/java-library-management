package org.example.client;

import org.example.client.console.MethodConsole;
import org.example.client.console.ModeConsole;
import org.example.client.console.ResponsePrinter;
import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.responsePacket.ResponsePacket;

public class Client {

    public String scanMode() {
        return new ModeConsole().scanType();
    }

    public RequestPacket scanMethod() {
        return new MethodConsole().scanTypeAndInfo();
    }

    public void printResponse(ResponsePacket responsePacket) {
        new ResponsePrinter().printResponse(responsePacket);
    }
}
