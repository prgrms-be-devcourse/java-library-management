package org.example.packet.requestPacket;

public abstract class RequestPacket {
    public final String methodName;

    public RequestPacket(String method) {
        this.methodName = method;
    }
}