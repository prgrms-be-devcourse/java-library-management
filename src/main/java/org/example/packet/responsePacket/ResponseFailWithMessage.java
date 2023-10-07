package org.example.packet.responsePacket;

public class ResponseFailWithMessage extends ResponsePacket {

    public final String failMessage;

    public ResponseFailWithMessage(String method, String failMessage) {
        super(method);
        this.failMessage = failMessage;
    }

    @Override
    public String toString() {
        return "ResponseFailWithMessage{" +
                "failMessage='" + failMessage + '\'' +
                '}';
    }
}
