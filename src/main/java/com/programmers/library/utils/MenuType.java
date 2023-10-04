package com.programmers.library.utils;

public enum MenuType {
    ADD(1, "도서 등록"),
    VIEW(2, "전체 도서 목록 조회"),
    SEARCH(3, "제목으로 도서 검색"),
    RENT(4, "도서 대여"),
    RETURN(5, "도서 반납"),
    LOST(6, "도서 분실"),
    DELETE(7, "도서 삭제"),
    EXIT(0, "종료");

    private int menuNum;
    private String menuName;
    private static ConsoleIO console = new ConsoleIO();

    MenuType(int menuNum, String menuName) {
        this.menuNum = menuNum;
        this.menuName = menuName;
    }

    private static MenuType getMenuByNum(int menuNum) {
        for(MenuType menu : MenuType.values()) {
            if(menu.menuNum == menuNum) {
                if(menuNum == 0) {
                    console.printMessage("\n[System] 애플리케이션을 종료합니다.\n");
                } else {
                    console.printMessage("\n[System] " + menu.menuName + "메뉴로 넘어갑니다.\n");
                }
                return menu;
            }
        }
        throw new IllegalArgumentException("메뉴를 찾을 수 없습니다");
    }

    public static MenuType selectMenu() {
        console.printMessage("Q. 사용할 기능을 선택해주세요.");
        for(MenuType menu : MenuType.values()) {
            console.printMessage(menu.menuNum + ". " + menu.menuName);
        }

        int menuInput = console.getIntInput("");
        return getMenuByNum(menuInput);
    }
}