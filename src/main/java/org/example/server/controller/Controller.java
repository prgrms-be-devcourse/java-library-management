package org.example.server.controller;

import org.example.packet.requestPacket.RequestPacket;
import org.example.packet.responsePacket.ResponsePacket;

public interface Controller {
    ResponsePacket handleRequest (RequestPacket requestPacket);
}