package org.example.packet.responsePacket;

public class ResponseFailWithMessage extends ResponsePacket {

    public final String FAIL_MESSAGE;

    public ResponseFailWithMessage(String method, String failMessage) {
        super(method);
        this.FAIL_MESSAGE = failMessage;
    }

    @Override
    public String toString() {
        return "ResponseFailWithMessage{" +
                "failMessage='" + FAIL_MESSAGE + '\'' +
                '}';
    }
}
