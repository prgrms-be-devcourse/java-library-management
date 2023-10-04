package org.example.packet.requestPacket;


import org.example.packet.MethodType;

public class RequestWithId extends RequestPacket {
    private final int id;

    public RequestWithId(MethodType method, int id) {
        super(method);
        this.id = id;
    }
    public int getId() {
        return id;
    }
}
