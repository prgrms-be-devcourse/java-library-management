package org.example.packet;

public class Request {
    public String method;
    public RequestData requestData = new RequestData();

    public Request(String method) {
        this.method = method;
    }
}
