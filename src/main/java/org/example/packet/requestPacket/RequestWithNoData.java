package org.example.packet.requestPacket;

public class RequestWithNoData extends RequestPacket {
    public RequestWithNoData(String method) {
        super(method);
    }

    @Override
    public String toString() {
        return "RequestWithNoData{}";
    }
}
