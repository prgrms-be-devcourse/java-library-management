package org.example.type;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MenuType {
    REGISTER(1, "1. 도서 등록\n", "\n[System] 도서 등록 메뉴로 넘어갑니다.\n\n"),

    READ_ALL(2, "2. 전체 도서 목록 조회\n", "\n[System] 전체 도서 목록입니다.\n\n"),

    SEARCH_BY_NAME(3, "3.제목으로 도서 검색\n", "\n[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n\n");
//    BORROW(3, "3. 도서 대여\n"),3. 제목으로 도서 검색
//    RETURN(4, "4. 도서 반납\n"),
//    LOST(5, "5. 도서 분실\n"),
//    RETURN(6, "6. 도서 삭제\n");

    private int menuNum;
    private String menuName;
    private String menuStartMent;

    private MenuType(int menuNum, String menuName, String menuStartMent) {
        this.menuNum = menuNum;
        this.menuName = menuName;
        this.menuStartMent = menuStartMent;
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
    private static final Map<Integer, MenuType> BY_NUMBER =
            Stream.of(values()).collect(Collectors.toMap(MenuType::getMenuNum, Function.identity()));

    public static MenuType valueOfNumber(int number) {
        return BY_NUMBER.get(number);
    }


}
