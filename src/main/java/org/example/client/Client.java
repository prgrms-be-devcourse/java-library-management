package org.example.client;

import org.example.client.console.MethodRequester;
import org.example.client.console.ModeRequester;
import org.example.client.console.MethodResponder;
import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.responsePacket.ResponsePacket;

public class Client {

    public String scanMode() {
        return new ModeRequester().scanType();
    }

    public RequestPacket scanMethod() {
        return new MethodRequester().scanTypeAndInfo();
    } // 계속 인스턴스 생성!! 언제 생성하고 언제 유지할까! (상태)

    public void printResponse(ResponsePacket responsePacket) {
        new MethodResponder().printResponse(responsePacket);
    }
}
