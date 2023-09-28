package org.example.type;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MenuType {
    REGISTER(1, "1. 도서 등록\n", "\n[System] 도서 등록 메뉴로 넘어갑니다.\n\n", new LinkedList<>(Arrays.asList(
            ("Q. 등록할 도서 제목을 입력하세요.\n\n> "),
            ("Q. 작가 이름을 입력하세요.\n\n> "),
            ("Q. 페이지 수를 입력하세요.\n\n> ")
    ))),
    READ_ALL(2, "2. 전체 도서 목록 조회\n", "\n[System] 전체 도서 목록입니다.\n\n", new LinkedList<>()),
    SEARCH_BY_NAME(3, "3. 제목으로 도서 검색\n", "\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n\n", new LinkedList<>(Arrays.asList("Q. 검색할 도서 제목 일부를 입력하세요.\n\n> "))),
    BORROW(4, "4. 도서 대여\n", "\n[System] 도서 대여 메뉴로 넘어갑니다.\n\n", new LinkedList<>(Arrays.asList("Q. 대여할 도서번호를 입력하세요\n\n> "))),
    RETURN(5, "5. 도서 반납\n", "\n[System] 도서 반납 메뉴로 넘어갑니다.\n", new LinkedList<>(Arrays.asList("Q. 반납할 도서번호를 입력하세요\n\n> "))),
    LOST(6, "6. 도서 분실\n", "\n[System] 도서 분실 처리 메뉴로 넘어갑니다.\n\n", new LinkedList<>(Arrays.asList("Q. 분실 처리할 도서번호를 입력하세요\n\n> "))),
    DELETE(7, "7. 도서 삭제\n", "\n[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n\n", new LinkedList<>(Arrays.asList("Q. 삭제 처리할 도서번호를 입력하세요\n\n> ")));

    private final int num;
    private final String name;
    private final String alert;
    private final LinkedList<String> questions;

    private MenuType(int num, String name, String alert, LinkedList<String> questions) {
        this.num = num;
        this.name = name;
        this.alert = alert;
        this.questions = questions;
    }

    private static final Map<Integer, MenuType> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(MenuType::getNum, Function.identity()));

    public static MenuType valueOfNumber(int num) {
        return BY_NUMBER.get(num);
    }

    public static final String MENU_CONSOLE = "Q. 사용할 기능을 선택해주세요.\n"
            + String.join("", Stream.of(values()).map(type -> type.name).toArray(String[]::new)) + "\n> ";

    public int getNum() {
        return num;
    }

    public String getAlert() {
        return alert;
    }

    public LinkedList<String> getQuestions() {
        return questions;
    }
}
