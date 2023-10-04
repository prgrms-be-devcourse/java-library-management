package org.example.packet.responsePacket;

import org.example.packet.MethodType;

public abstract class ResponsePacket {
    protected MethodType method;

    public ResponsePacket(MethodType method) {
        this.method = method;
    }

    public MethodType getMethod() {
        return method;
    }
}
