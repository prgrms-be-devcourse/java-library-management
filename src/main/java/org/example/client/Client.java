package org.example.client;

import org.example.client.console.MethodRequester;
import org.example.client.console.MethodResponder;
import org.example.client.console.ModeRequester;
import org.example.client.io.In;
import org.example.client.io.Out;
import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.responsePacket.ResponsePacket;

public class Client {
    private final Out out;
    private final In in;
    private final MethodRequester methodRequester;
    private final MethodResponder methodResponder;

    public Client(Out out, In in) {
        this.out = out;
        this.in = in;
        methodRequester = new MethodRequester(out, in);
        methodResponder = new MethodResponder(out);
    }

    public String scanMode() {
        return new ModeRequester(out, in).scanModeType();
    }

    public RequestPacket scanMethod() {
        return methodRequester.scanMethodTypeAndInfo();
    }

    public void printResponse(ResponsePacket responsePacket) {
        methodResponder.printResponse(responsePacket);
    }
}
