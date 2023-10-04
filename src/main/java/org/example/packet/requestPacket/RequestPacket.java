package org.example.packet.requestPacket;

import org.example.packet.MethodType;

public abstract class RequestPacket {
    protected MethodType method;

    public RequestPacket(MethodType method) {
        this.method = method;
    }

    public MethodType getMethod() {
        return method;
    }
}