package org.example.packet.requestPacket;

public class RequestForRegister extends RequestPacket {
    public final BookRegisterDto bookInfo;

    public RequestForRegister(String method, BookRegisterDto bookDto) {
        super(method);
        this.bookInfo = bookDto;
    }

    @Override
    public String toString() {
        return "RequestWithBook{" +
                "bookDto=" + bookInfo +
                '}';
    }
}
