package org.example.packet.responsePacket;

public class ResponseSuccessWithNoData extends ResponsePacket {
    public ResponseSuccessWithNoData(String method) {
        super(method);
    }

    @Override
    public String toString() {
        return "ResponseSuccessWithNoData{}";
    }
}
