package org.example.client.console;

import org.example.client.io.Out;
import org.example.packet.responsePacket.BookResponseDto;
import org.example.packet.responsePacket.ResponseFail;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.packet.responsePacket.ResponseSuccessWithData;
import org.example.packet.responsePacket.ResponseSuccessWithNoData;

import java.util.LinkedList;

public class MethodResponder {

    private final Out out;

    public MethodResponder(Out out) {
        this.out = out;
    }

    public void printResponse(ResponsePacket responsePacket) {
        MethodType methodType = MethodType.valueOf(responsePacket.method);
        if (responsePacket instanceof ResponseSuccessWithData) {
            LinkedList<BookResponseDto> bookDtos = ((ResponseSuccessWithData) responsePacket).books;
            if (bookDtos.isEmpty()) {
                out.print(System.lineSeparator() + "[System] 존재하는 도서가 없습니다." + System.lineSeparator());
            } else {
                out.println(booksToString(bookDtos));
                out.println(methodType.successMessage);
            }
            return;
        }
        if (responsePacket instanceof ResponseSuccessWithNoData) {
            out.println(methodType.successMessage);
            return;
        }
        if (responsePacket instanceof ResponseFail) {
            String failMessage = ((ResponseFail) responsePacket).failMessage;
            out.println(failMessage);
        }
    }

    private String booksToString(LinkedList<BookResponseDto> bookDtos) {
        StringBuffer sb = new StringBuffer();
        bookDtos.forEach(bookDto -> sb.append(System.lineSeparator()).append("도서번호 : ").append(bookDto.id).append(System.lineSeparator()).append("제목 : ").append(bookDto.name).append(System.lineSeparator()).append("작가 이름 : ").append(bookDto.author).append(System.lineSeparator()).append("페이지 수 : ").append(bookDto.pages).append(" 페이지").append(System.lineSeparator()).append("상태 : ").append(bookDto.status).append(System.lineSeparator()).append(System.lineSeparator()).append("------------------------------").append(System.lineSeparator()));
        return sb.toString();
    }
}
