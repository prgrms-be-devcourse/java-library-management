package org.example.packet.requestPacket;

public class RequestForSearch extends RequestPacket {
    public final String bookName;

    public RequestForSearch(String method, String name) {
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
