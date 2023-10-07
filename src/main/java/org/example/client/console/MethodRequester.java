package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.example.client.io.In;
import org.example.client.io.Out;
import org.example.packet.BookDto;
import org.example.packet.requestPacket.*;

public class MethodRequester {
    private final Out out;
    private final In in;
    private final Validator validator = new Validator();

    public MethodRequester(Out out, In in) {
        this.out = out;
        this.in = in;
    }

    public RequestPacket scanTypeAndInfo() {
        MethodType methodType;
        try {
            methodType = scanMethodType();
        } catch (ValidateException e) {
            out.println(e.getMessage());
            methodType = scanMethodType();
        }
        out.println(methodType.START_MESSAGE);
        try {
            return switch (methodType) {
                case REGISTER -> scanForRegister(methodType);
                case READ_ALL -> new RequestWithNoData(methodType.name());
                case SEARCH_BY_NAME -> scanForSearchName(methodType);
                default -> scanForFindById(methodType);
            };
        } catch (ValidateException e) {
            out.println(e.getMessage());
            return switch (methodType) {
                case REGISTER -> scanForRegister(methodType);
                case READ_ALL -> new RequestWithNoData(methodType.name());
                case SEARCH_BY_NAME -> scanForSearchName(methodType);
                default -> scanForFindById(methodType);
            };
        }
    }

    private MethodType scanMethodType() {
        out.print(MethodType.BASIC_QUESTION);
        int methodNumber = validator.scanAndValidateSelection(MethodType.values().length, in.scanLine());
        return MethodType.valueOfNumber(methodNumber);
    }

    private RequestWithBook scanForRegister(MethodType methodType) {
        out.print(methodType.QUESTIONS.get(0));
        String name = validator.scanAndValidateString(in.scanLine());
        out.print(methodType.QUESTIONS.get(1));
        String author = validator.scanAndValidateString(in.scanLine());
        out.print(methodType.QUESTIONS.get(2));
        int pages = validator.scanAndValidateNumber(in.scanLine());
        return new RequestWithBook(methodType.name(), new BookDto(name, author, pages));
    }

    private RequestWithName scanForSearchName(MethodType methodType) {
        out.print(methodType.QUESTIONS.get(0));
        String name = validator.scanAndValidateString(in.scanLine());
        return new RequestWithName(methodType.name(), name);
    }

    private RequestWithId scanForFindById(MethodType methodType) {
        out.print(methodType.QUESTIONS.get(0));
        int id = validator.scanAndValidateNumber(in.scanLine());
        return new RequestWithId(methodType.name(), id);
    }
}
