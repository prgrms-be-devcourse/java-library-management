package org.example.packet.responsePacket;

import org.example.packet.BookDto;
import org.example.packet.MethodType;

import java.util.LinkedList;

public class ResponseFailWithMessage extends ResponsePacket {

    private String failMessage;

    public ResponseFailWithMessage(MethodType method, String failMessage) {
        super(method);
        this.failMessage = failMessage;
    }

    public String getFailMessage() {
        return failMessage;
    }
}
