package org.example.packet.responsePacket;

public abstract class ResponsePacket {
    public final String METHOD;

    public ResponsePacket(String method) {
        this.METHOD = method;
    }
}
