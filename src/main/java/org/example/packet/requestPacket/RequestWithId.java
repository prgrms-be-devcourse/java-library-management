package org.example.packet.requestPacket;


public class RequestWithId extends RequestPacket {
    public final int ID;

    public RequestWithId(String method, int id) {
        super(method);
        this.ID = id;
    }

    @Override
    public String toString() {
        return "RequestWithId{" +
                "id=" + ID +
                '}';
    }
}
