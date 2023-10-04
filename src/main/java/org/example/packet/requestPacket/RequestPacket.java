package org.example.packet.requestPacket;

public abstract class RequestPacket {
    public final String METHOD;

    public RequestPacket(String method) {
        this.METHOD = method;
    }
}