package org.example.packet.requestPacket;

public class RequestWithName extends RequestPacket {
    public final String NAME;

    public RequestWithName(String method, String name) {
        super(method);
        this.NAME = name;
    }

    @Override
    public String toString() {
        return "RequestWithName{" +
                "name='" + NAME + '\'' +
                '}';
    }
}
