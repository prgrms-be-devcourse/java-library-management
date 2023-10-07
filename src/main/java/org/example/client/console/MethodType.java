package org.example.client.console;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MethodType {
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
    public static final String BASIC_QUESTION = "Q. 사용할 기능을 선택해주세요." + System.lineSeparator()
            + String.join(System.lineSeparator(), Stream.of(values()).map(methodType -> methodType.number + ". " + methodType.nameKor).toArray(String[]::new)) + System.lineSeparator() + "> ";
    private static final Map<Integer, MethodType> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(MethodType::getNum, Function.identity()));
    public final String startMessage;
    public final String successMessage;
    public final LinkedList<String> questions;
    private final int number;
    private final String nameKor;

    MethodType(int number, String nameKor, String startMsg, String successMsg, LinkedList<String> questions) {
        this.number = number;
        this.nameKor = nameKor;
        this.startMessage = startMsg;
        this.successMessage = successMsg;
        this.questions = questions;
    }

    public static MethodType valueOfNumber(int num) {
        return BY_NUMBER.get(num);
    }

    private int getNum() {
        return number;
    }
}