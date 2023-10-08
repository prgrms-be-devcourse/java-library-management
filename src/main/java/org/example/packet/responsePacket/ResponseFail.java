package org.example.packet.responsePacket;

public class ResponseFail extends ResponsePacket {

    public final String failMessage;

    public ResponseFail(String method, String failMessage) {
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
