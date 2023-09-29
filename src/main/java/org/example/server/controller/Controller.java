package org.example.server.controller;

import org.example.packet.Request;

public interface Controller {
    String mapController(Request request);
}