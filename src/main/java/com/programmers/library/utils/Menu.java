package com.programmers.library.utils;

import java.util.Scanner;

public enum Menu {
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

    Menu(int menuNum, String menuName) {
        this.menuNum = menuNum;
        this.menuName = menuName;
    }

    private static Menu getMenuByNum(int menuNum) {
        for(Menu menu : Menu.values()) {
            if(menu.menuNum == menuNum) {
                if(menuNum == 0) {
                    System.out.println("\n[System] 애플리케이션을 종료합니다.\n");
                } else {
                    System.out.println("\n[System] " + menu.menuName + "메뉴로 넘어갑니다.\n");
                }
                return menu;
            }
        }
        throw new IllegalArgumentException("메뉴를 찾을 수 없습니다");
    }

    public static Menu selectMenu() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Q. 사용할 기능을 선택해주세요.");
        for(Menu menu : Menu.values()) {
            System.out.println(menu.menuNum + ". " + menu.menuName);
        }
        System.out.print("\n> ");

        int menuInput = scanner.nextInt();
        return getMenuByNum(menuInput);
    }
}