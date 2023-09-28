package org.example.client.type;

import org.example.client.console.MethodConsole;
import org.example.packet.RequestData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum ClientMethod {
    REGISTER(1, "1. 도서 등록\n", "\n[System] 도서 등록 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList(
            ("Q. 등록할 도서 제목을 입력하세요.\n\n> "),
            ("Q. 작가 이름을 입력하세요.\n\n> "),
            ("Q. 페이지 수를 입력하세요.\n\n> ")
    )), MethodConsole::scanAndSetBookInfo),
    READ_ALL(2, "2. 전체 도서 목록 조회\n", "\n[System] 전체 도서 목록입니다.\n\n", new ArrayList<>(), (RequestData::new)),
    SEARCH_BY_NAME(3, "3. 제목으로 도서 검색\n", "\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 검색할 도서 제목 일부를 입력하세요.\n\n> ")), MethodConsole::scanAndSetBookName),
    BORROW(4, "4. 도서 대여\n", "\n[System] 도서 대여 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 대여할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId),
    RETURN(5, "5. 도서 반납\n", "\n[System] 도서 반납 메뉴로 넘어갑니다.\n", new ArrayList<>(Arrays.asList("Q. 반납할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId),
    LOST(6, "6. 도서 분실\n", "\n[System] 도서 분실 처리 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 분실 처리할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId),
    DELETE(7, "7. 도서 삭제\n", "\n[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n\n", new ArrayList<>(Arrays.asList("Q. 삭제 처리할 도서번호를 입력하세요\n\n> ")), MethodConsole::scanAndSetBookId);
    private static final Map<Integer, ClientMethod> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(ClientMethod::getNum, Function.identity()));

    public static ClientMethod valueOfNumber(int num) {
        return BY_NUMBER.get(num);
    }

    public static final String MENU_CONSOLE = "Q. 사용할 기능을 선택해주세요.\n"
            + String.join("", Stream.of(values()).map(type -> type.name).toArray(String[]::new)) + "\n> ";

    private final int num;
    private final String name;
    public final String alert;
    private final ArrayList<String> questions;
    private final Supplier<RequestData> setInfoFunction;

    private ClientMethod(int num, String name, String alert, ArrayList<String> questions, Supplier<RequestData> setInfoFunction) {
        this.num = num;
        this.name = name;
        this.alert = alert;
        this.questions = questions;
        this.setInfoFunction = setInfoFunction;
    }

    public int getNum() {
        return num;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public RequestData setInfo() {
        return this.setInfoFunction.get();
    }
}
