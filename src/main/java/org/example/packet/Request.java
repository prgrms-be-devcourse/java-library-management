package org.example.packet;

// Client와 Server의 의존성을 약하게 하는 객체
// Client와 Server는 이 객체를 통해 정보 교환
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
