package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.example.client.io.ConsoleOut;
import org.example.client.io.Out;
import org.example.packet.BookDto;
import org.example.packet.requestPacket.*;

public class MethodRequester { // 입력을 받는 역할 + In
    private final Out OUT = ConsoleOut.getInstance();
    private final Validator VALIDATOR = new Validator(); // final이 정말 상수일까!!!!!! static final!!

    public RequestPacket scanTypeAndInfo() {
        MethodType methodType;
        try {
            methodType = scanType();
        } catch (ValidateException e) {
            OUT.println(e.getMessage());
            methodType = scanType();
        }
        OUT.println(methodType.START_MESSAGE);
        try {
            return switch (methodType) {
                case REGISTER -> scanBookInfo(methodType);
                case READ_ALL -> new RequestWithNoData(methodType.name());
                case SEARCH_BY_NAME -> scanBookName(methodType);
                default -> scanBookId(methodType);
            };
        } catch (ValidateException e) {
            OUT.println(e.getMessage());
            return switch (methodType) {
                case REGISTER -> scanBookInfo(methodType);
                case READ_ALL -> new RequestWithNoData(methodType.name());
                case SEARCH_BY_NAME -> scanBookName(methodType);
                default -> scanBookId(methodType);
            };
        }
    }

    private MethodType scanType() {
        OUT.print(MethodType.BASIC_QUESTION);
        int methodNumber = VALIDATOR.scanAndValidateSelection(MethodType.values().length);
        return MethodType.valueOfNumber(methodNumber);
    }

    private RequestWithBook scanBookInfo(MethodType methodType) {
        OUT.print(methodType.QUESTIONS.get(0));
        String name = VALIDATOR.scanAndValidateString();
        OUT.print(methodType.QUESTIONS.get(1));
        String author = VALIDATOR.scanAndValidateString();
        OUT.print(methodType.QUESTIONS.get(2));
        int pages = VALIDATOR.scanAndValidateNumber();
        return new RequestWithBook(methodType.name(), new BookDto(name, author, pages));
    }

    private RequestWithName scanBookName(MethodType methodType) { // 어떤 요청을 위한 것인지? -> 객체의 행위에 대해서 명시
        OUT.print(methodType.QUESTIONS.get(0));
        String name = VALIDATOR.scanAndValidateString();
        return new RequestWithName(methodType.name(), name);
    }

    private RequestWithId scanBookId(MethodType methodType) {
        OUT.print(methodType.QUESTIONS.get(0));
        int id = VALIDATOR.scanAndValidateNumber();
        return new RequestWithId(methodType.name(), id);
    }
}
