package org.example.packet.responsePacket;

import org.example.packet.MethodType;

public class ResponseSuccessWithNoData extends ResponsePacket {
    public ResponseSuccessWithNoData(MethodType method) {
        super(method);
    }

    @Override
    public String toString() {
        return "ResponseSuccessWithNoData{}";
    }
}
