package org.example.client.console;

import org.example.client.ValidateException;
import org.example.client.Validator;
import org.example.client.io.ConsoleIO;
import org.example.client.io.IO;
import org.example.packet.BookDto;
import org.example.packet.requestPacket.*;

public class MethodRequester {
    private final IO IO = ConsoleIO.getInstance();
    private final Validator VALIDATOR = new Validator();

    public RequestPacket scanTypeAndInfo() {
        MethodType methodType;
        try {
            methodType = scanType();
        } catch (ValidateException e) {
            IO.println(e.getMessage());
            methodType = scanType();
        }
        IO.println(methodType.START_MESSAGE);
        try {
            return switch (methodType) {
                case REGISTER -> scanBookInfo(methodType);
                case READ_ALL -> new RequestWithNoData(methodType.name());
                case SEARCH_BY_NAME -> scanBookName(methodType);
                default -> scanBookId(methodType);
            };
        } catch (ValidateException e) {
            IO.println(e.getMessage());
            return switch (methodType) {
                case REGISTER -> scanBookInfo(methodType);
                case READ_ALL -> new RequestWithNoData(methodType.name());
                case SEARCH_BY_NAME -> scanBookName(methodType);
                default -> scanBookId(methodType);
            };
        }
    }

    private MethodType scanType() {
        IO.print(MethodType.BASIC_QUESTION);
        int methodNumber = VALIDATOR.validateSelectNum(MethodType.values().length, IO.scanLine());
        return MethodType.valueOfNumber(methodNumber);
    }

    private RequestWithBook scanBookInfo(MethodType methodType) {
        IO.print(methodType.QUESTIONS.get(0));
        String name = VALIDATOR.validateNameAndAuthor(IO.scanLine());
        IO.print(methodType.QUESTIONS.get(1));
        String author = VALIDATOR.validateNameAndAuthor(IO.scanLine());
        IO.print(methodType.QUESTIONS.get(2));
        int pages = VALIDATOR.validateIdAndPages(IO.scanLine());
        return new RequestWithBook(methodType.name(), new BookDto(name, author, pages));
    }

    private RequestWithName scanBookName(MethodType methodType) {
        IO.print(methodType.QUESTIONS.get(0));
        String name = VALIDATOR.validateNameAndAuthor(IO.scanLine());
        return new RequestWithName(methodType.name(), name);
    }

    private RequestWithId scanBookId(MethodType methodType) {
        IO.print(methodType.QUESTIONS.get(0));
        int id = VALIDATOR.validateIdAndPages(IO.scanLine());
        return new RequestWithId(methodType.name(), id);
    }
}
