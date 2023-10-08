package org.example.packet.requestPacket;


public class RequestForChange extends RequestPacket {
    public final int bookId;

    public RequestForChange(String method, int id) {
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
