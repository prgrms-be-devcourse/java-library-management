package org.example.packet.responsePacket;

public abstract class ResponsePacket {
    public final String method;

    public ResponsePacket(String method) {
        this.method = method;
    }
}
