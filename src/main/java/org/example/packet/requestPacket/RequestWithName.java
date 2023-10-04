package org.example.packet.requestPacket;

import org.example.packet.BookDto;
import org.example.packet.MethodType;

public class RequestWithName extends RequestPacket {
    private final String name;

    public RequestWithName(MethodType method, String name) {
        super(method);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "RequestWithName{" +
                "name='" + name + '\'' +
                '}';
    }
}
