package org.example.packet.requestPacket;

public class RequestForReadAll extends RequestPacket {
    public RequestForReadAll(String method) {
        super(method);
    }

    @Override
    public String toString() {
        return "RequestWithNoData{}";
    }
}
