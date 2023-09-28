package org.example.type;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum MenuType {
    REGISTER(1, "1. 도서 등록\n");
//    READ_ALL(2, "2. 전체 도서 목록 조회\n"),
//    SEARCH_BY_NAME(3, "3. 도서 대여\n"),
//    BORROW(4, "4. 도서 반납\n"),
//    LOST(5, "5. 도서 분실\n"),
//    RETURN(6, "6. 도서 삭제\n");

    private int menuNum;
    private String menuName;

    private MenuType(int menuNum, String menuName) {
        this.menuNum = menuNum;
        this.menuName = menuName;
    }

    public int getMenuNum() {
        return menuNum;
    }

    public String getMenuName() {
        return menuName;
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
