package org.example.client.connect;

import org.example.server.entity.RequestBookDto;

public class RequestData {
    public RequestBookDto requestBookDto;
    public int bookId;

    public RequestData(int bookId) {
        this.bookId = bookId;
    }

    public RequestData(RequestBookDto requestBookDto) {
        this.requestBookDto = requestBookDto;
    }
}
