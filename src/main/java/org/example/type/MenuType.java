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
    SEARCH_BY_NAME(3, "3.제목으로 도서 검색\n", "\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n\n", new LinkedList<>(Arrays.asList("Q. 검색할 도서 제목 일부를 입력하세요.\n\n> "))),
    BORROW(4, "4. 도서 대여\n", "\n[System] 도서 대여 메뉴로 넘어갑니다.\n\n", new LinkedList<>(Arrays.asList("Q. 대여할 도서번호를 입력하세요\n\n> "))),
    RETURN(5, "5. 도서 반납\n", "\n[System] 도서 반납 메뉴로 넘어갑니다.\n", new LinkedList<String>(Arrays.asList("Q. 반납할 도서번호를 입력하세요\n\n> ")));
//    LOST(5, "5. 도서 분실\n"),
//    RETURN(6, "6. 도서 삭제\n");

    private int menuNum;
    private String menuName;
    private String menuStartMent;

    private LinkedList<String> menuQuestion;

    private MenuType(int menuNum, String menuName, String menuStartMent, LinkedList<String> menuQuestion) {
        this.menuNum = menuNum;
        this.menuName = menuName;
        this.menuStartMent = menuStartMent;
        this.menuQuestion = menuQuestion;
    }

    public int getMenuNum() {
        return menuNum;
    }

    public String getMenuName() {
        return menuName;
    }

    public String getMenuStartMent() {
        return menuStartMent;
    }

    //    public static String getMenuNames() {
//        return
//    }
    public LinkedList<String> getMenuQuestion() {
        return menuQuestion;
    }

    private static final Map<Integer, MenuType> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(MenuType::getMenuNum, Function.identity()));

    public static MenuType valueOfNumber(int number) {
        return BY_NUMBER.get(number);
    }


}
