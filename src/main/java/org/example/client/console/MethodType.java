package org.example.client.console;

import org.example.client.io.IO;
import org.example.packet.RequestData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MethodType {
    REGISTER(1, "1. 도서 등록", System.lineSeparator() + "[System] 도서 등록 메뉴로 넘어갑니다." + System.lineSeparator(), new ArrayList<>(Arrays.asList(
            ("Q. 등록할 도서 제목을 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> "),
            ("Q. 작가 이름을 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> "),
            ("Q. 페이지 수를 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> ")
    )), MethodConsole::scanAndSetBookInfo),
    READ_ALL(2, "2. 전체 도서 목록 조회", System.lineSeparator() + "[System] 전체 도서 목록입니다." + System.lineSeparator(), new ArrayList<>(), (io -> new RequestData())),
    SEARCH_BY_NAME(3, "3. 제목으로 도서 검색", System.lineSeparator() + "[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n" + System.lineSeparator(), new ArrayList<>(List.of("Q. 검색할 도서 제목 일부를 입력하세요." + System.lineSeparator() + System.lineSeparator() + "> ")), MethodConsole::scanAndSetBookName),
    BORROW(4, "4. 도서 대여", System.lineSeparator() + "[System] 도서 대여 메뉴로 넘어갑니다." + System.lineSeparator(), new ArrayList<>(List.of("Q. 대여할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> ")), MethodConsole::scanAndSetBookId),
    RESTORE(5, "5. 도서 반납", System.lineSeparator() + "[System] 도서 반납 메뉴로 넘어갑니다." + System.lineSeparator(), new ArrayList<>(List.of("Q. 반납할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> ")), MethodConsole::scanAndSetBookId),
    LOST(6, "6. 도서 분실", System.lineSeparator() + "[System] 도서 분실 처리 메뉴로 넘어갑니다." + System.lineSeparator(), new ArrayList<>(List.of("Q. 분실 처리할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> ")), MethodConsole::scanAndSetBookId),
    DELETE(7, "7. 도서 삭제", System.lineSeparator() + "[System] 도서 삭제 처리 메뉴로 넘어갑니다." + System.lineSeparator(), new ArrayList<>(List.of("Q. 삭제 처리할 도서번호를 입력하세요" + System.lineSeparator() + System.lineSeparator() + "> ")), MethodConsole::scanAndSetBookId);
    private static final Map<Integer, MethodType> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(MethodType::getNum, Function.identity()));
    public final String alert;
    private final int num;
    private final String name;
    public final String BASIC_QUESTION = "Q. 사용할 기능을 선택해주세요." + System.lineSeparator()
            + String.join("", Stream.of(values()).map(type -> type.name + System.lineSeparator()).toArray(String[]::new)) + System.lineSeparator() + "> ";
    private final ArrayList<String> questions;
    private final Function<IO, RequestData> scanInfoFunction;

    MethodType(int num, String name, String alert, ArrayList<String> questions, Function<IO, RequestData> scanInfoFunction) {
        this.num = num;
        this.name = name;
        this.alert = alert;
        this.questions = questions;
        this.scanInfoFunction = scanInfoFunction;
    }

    public static MethodType valueOfNumber(int num) {
        return BY_NUMBER.get(num);
    }

    public int getNum() {
        return num;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public String getQuestion() {
        return questions.get(0);
    }

    public RequestData scanInfo(IO io) {
        return this.scanInfoFunction.apply(io);
    }
}