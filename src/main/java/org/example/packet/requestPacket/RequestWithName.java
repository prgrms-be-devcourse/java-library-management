package org.example.packet.requestPacket;

public class RequestWithName extends RequestPacket {
    public final String bookName;

    public RequestWithName(String method, String name) {
        super(method);
        this.bookName = name;
    }

    @Override
    public String toString() {
        return "RequestWithName{" +
                "name='" + bookName + '\'' +
                '}';
    }
}
