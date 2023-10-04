package org.example.client.console;

import org.example.client.io.IO;
import org.example.packet.dto.BookDto;
import org.example.packet.requestPacket.*;
import org.example.packet.responsePacket.ResponseFailWithMessage;
import org.example.packet.responsePacket.ResponsePacket;
import org.example.packet.responsePacket.ResponseSuccessWithData;
import org.example.packet.responsePacket.ResponseSuccessWithNoData;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodConsole {
    private final IO io = IO.getInstance();

    public void printResponse(ResponsePacket responsePacket) {
        Type type = Type.valueOf(responsePacket.METHOD);
        if (responsePacket instanceof ResponseSuccessWithData) {
            LinkedList<BookDto> bookDtos = ((ResponseSuccessWithData) responsePacket).BOOKS;
            if (bookDtos.isEmpty()) {
                io.println(System.lineSeparator() + "[System] 존재하는 도서가 없습니다." + System.lineSeparator());
            } else {
                bookDtos.forEach(bookDto -> io.println(String.valueOf(bookDto)));
                io.println(type.SUCCESS_MESSAGE);
            }
            return;
        }
        if (responsePacket instanceof ResponseSuccessWithNoData) {
            io.println(type.SUCCESS_MESSAGE);
            return;
        }
        if (responsePacket instanceof ResponseFailWithMessage) {
            String failMessage = ((ResponseFailWithMessage) responsePacket).FAIL_MESSAGE;
            io.println(failMessage);
        }
    }

    public RequestPacket scanTypeAndInfo() {
        Type type;
        try {
            type = scanType();
        } catch (ValidateException e) {
            io.println(e.getMessage());
            type = scanType();
        }
        io.println(type.START_MESSAGE);
        try {
            return switch (type) {
                case REGISTER -> scanBookInfo(type);
                case READ_ALL -> new RequestWithNoData(type.name());
                case SEARCH_BY_NAME -> scanBookName(type);
                default -> scanBookId(type);
            };
        } catch (ValidateException e) {
            io.println(e.getMessage());
            return switch (type) {
                case REGISTER -> scanBookInfo(type);
                case READ_ALL -> new RequestWithNoData(type.name());
                case SEARCH_BY_NAME -> scanBookName(type);
                default -> scanBookId(type);
            };
        }
    }

    private Type scanType() {
        io.print(Type.BASIC_QUESTION);
        int methodNumber = Validator.validateSelectNum(Type.values().length, io.scanLine());
        return Type.valueOfNumber(methodNumber);
    }

    private RequestWithBook scanBookInfo(Type type) {
        io.print(type.QUESTIONS.get(0));
        String name = Validator.validateNameAndAuthor(io.scanLine());
        io.print(type.QUESTIONS.get(1));
        String author = Validator.validateNameAndAuthor(io.scanLine());
        io.print(type.QUESTIONS.get(2));
        int pages = Validator.validateIdAndPages(io.scanLine());
        return new RequestWithBook(type.name(), new BookDto(name, author, pages));
    }

    private RequestWithName scanBookName(Type type) {
        io.println(type.QUESTIONS.get(0));
        String name = Validator.validateNameAndAuthor(io.scanLine());
        return new RequestWithName(type.name(), name);
    }

    private RequestWithId scanBookId(Type type) {
        io.println(type.QUESTIONS.get(0));
        int id = Validator.validateIdAndPages(io.scanLine());
        return new RequestWithId(type.name(), id);
    }

    private enum Type {
        REGISTER(1, "도서 등록", System.lineSeparator() + "[System] 도서 등록 메뉴로 넘어갑니다." + System.lineSeparator(), System.lineSeparator() + "[System] 도서 등록이 완료되었습니다." + System.lineSeparator(), new LinkedList<>(Arrays.asList(
                ("Q. 등록할 도서 제목을 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> "),
                ("Q. 작가 이름을 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> "),
                ("Q. 페이지 수를 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> ")
        ))),
        READ_ALL(2, "전체 도서 목록 조회", System.lineSeparator() + "[System] 전체 도서 목록입니다." + System.lineSeparator(), System.lineSeparator() + "[System] 도서 목록 끝" + System.lineSeparator(), new LinkedList<>()),
        SEARCH_BY_NAME(3, "제목으로 도서 검색", System.lineSeparator() + "[System] 제목으로 도서 검색 메뉴로 넘어갑니다." + System.lineSeparator(), System.lineSeparator() + "[System] 검색된 도서 끝" + System.lineSeparator(), new LinkedList<>(Arrays.asList("Q. 검색할 도서 제목 일부를 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> "))),
        BORROW(4, "도서 대여", System.lineSeparator() + "[System] 도서 대여 메뉴로 넘어갑니다." + System.lineSeparator(), System.lineSeparator() + "[System] 도서가 대여 처리 되었습니다." + System.lineSeparator(), new LinkedList<>(Arrays.asList("Q. 대여할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> "))),
        RESTORE(5, "도서 반납", System.lineSeparator() + "[System] 도서 반납 메뉴로 넘어갑니다." + System.lineSeparator(), System.lineSeparator() + "[System] 도서가 반납 처리 되었습니다." + System.lineSeparator(), new LinkedList<>(Arrays.asList("Q. 반납할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> "))),
        LOST(6, "도서 분실", System.lineSeparator() + "[System] 도서 분실 처리 메뉴로 넘어갑니다." + System.lineSeparator(), System.lineSeparator() + "[System] 도서가 분실 처리 되었습니다." + System.lineSeparator(), new LinkedList<>(Arrays.asList("Q. 분실 처리할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> "))),
        DELETE(7, "도서 삭제", System.lineSeparator() + "[System] 도서 삭제 처리 메뉴로 넘어갑니다." + System.lineSeparator(), System.lineSeparator() + "[System] 도서가 삭제 처리 되었습니다." + System.lineSeparator(), new LinkedList<>(Arrays.asList("Q. 삭제 처리할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> ")));
        private static final String BASIC_QUESTION = "Q. 사용할 기능을 선택해주세요." + System.lineSeparator()
                + String.join(System.lineSeparator(), Stream.of(values()).map(type -> type.NUMBER + ". " + type.NAME_KOR).toArray(String[]::new)) + System.lineSeparator() + "> ";
        private static final Map<Integer, Type> BY_NUMBER =
                Stream.of(values()).collect(Collectors.toMap(Type::getNum, Function.identity()));
        private final int NUMBER;
        private final String NAME_KOR;
        private final String START_MESSAGE;
        private final String SUCCESS_MESSAGE;
        private final LinkedList<String> QUESTIONS;

        Type(int number, String name_kor, String start_msg, String success_msg, LinkedList<String> questions) {
            this.NUMBER = number;
            this.NAME_KOR = name_kor;
            this.START_MESSAGE = start_msg;
            this.SUCCESS_MESSAGE = success_msg;
            this.QUESTIONS = questions;
        }

        private static Type valueOfNumber(int num) {
            return BY_NUMBER.get(num);
        }

        private int getNum() {
            return NUMBER;
        }
    }
}
