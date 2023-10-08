package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.example.client.io.In;
import org.example.client.io.Out;
import org.example.packet.BookRegisterDto;
import org.example.packet.requestPacket.*;

public class MethodRequester {
    private final Out out;
    private final In in;
    private final Validator validator = new Validator();

    public MethodRequester(Out out, In in) {
        this.out = out;
        this.in = in;
    }

    public RequestPacket scanMethodTypeAndInfo() {
        MethodType methodType;
        try {
            methodType = scanMethodType();
        } catch (ValidateException e) {
            out.println(e.getMessage());
            methodType = scanMethodType();
        }
        out.println(methodType.startMessage);
        try {
            return switch (methodType) {
                case REGISTER -> scanForRegister(methodType);
                case READ_ALL -> new RequestForReadAll(methodType.name());
                case SEARCH_BY_NAME -> scanForSearchName(methodType);
                default -> scanForFindById(methodType);
            };
        } catch (ValidateException e) {
            out.println(e.getMessage());
            return switch (methodType) {
                case REGISTER -> scanForRegister(methodType);
                case READ_ALL -> new RequestForReadAll(methodType.name());
                case SEARCH_BY_NAME -> scanForSearchName(methodType);
                default -> scanForFindById(methodType);
            };
        }
    }

    private MethodType scanMethodType() {
        out.print(MethodType.BASIC_QUESTION);
        int methodNumber = validator.scanAndValidateSelectionNumber(MethodType.values().length, in.scanLine());
        return MethodType.valueOfNumber(methodNumber);
    }

    private RequestForRegister scanForRegister(MethodType methodType) {
        out.print(methodType.questions.get(0));
        String name = validator.scanAndValidateNameAndAuthor(in.scanLine());
        out.print(methodType.questions.get(1));
        String author = validator.scanAndValidateNameAndAuthor(in.scanLine());
        out.print(methodType.questions.get(2));
        int pages = validator.scanAndValidateIdAndPageNumber(in.scanLine());
        return new RequestForRegister(methodType.name(), new BookRegisterDto(name, author, pages));
    }

    private RequestForSearch scanForSearchName(MethodType methodType) {
        out.print(methodType.questions.get(0));
        String name = validator.scanAndValidateNameAndAuthor(in.scanLine());
        return new RequestForSearch(methodType.name(), name);
    }

    private RequestForChange scanForFindById(MethodType methodType) {
        out.print(methodType.questions.get(0));
        int id = validator.scanAndValidateIdAndPageNumber(in.scanLine());
        return new RequestForChange(methodType.name(), id);
    }
}
