package org.example.packet;

public class Request {
    public String method;
    public RequestData requestData;

    public Request(String method) {
        this.method = method;
    }

    public Request(String method, RequestData requestData) {
        this.method = method;
        this.requestData = requestData;
    }
}
