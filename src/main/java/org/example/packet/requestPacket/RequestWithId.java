package org.example.packet.requestPacket;


public class RequestWithId extends RequestPacket {
    public final int bookId;

    public RequestWithId(String method, int id) {
        super(method);
        this.bookId = id;
    }

    @Override
    public String toString() {
        return "RequestWithId{" +
                "id=" + bookId +
                '}';
    }
}
