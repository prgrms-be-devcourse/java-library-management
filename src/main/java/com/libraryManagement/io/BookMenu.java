package com.libraryManagement.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.libraryManagement.exception.ExceptionMessage.INVALID_BOOK_MENU;
import static java.lang.System.exit;

public class BookMenu {

    private final List<String> menuList;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public BookMenu() {
        menuList = new ArrayList<>();

        menuList.add("사용할 기능을 선택해주세요.");
        menuList.add("도서 등록");
        menuList.add("전체 도서 목록 조회");

        menuList.add("제목으로 도서 검색");
        menuList.add("도서 대여");
        menuList.add("도서 반납");

        menuList.add("도서 분실");
        menuList.add("도서 삭제");
    }

    public int startBookMenu() throws IOException {

        int selectedBookMenuNum;

        while(true){
            for(int i = 0; i < menuList.size(); i++){
                System.out.println(i + ". " + menuList.get(i));
            }
            System.out.println();

            selectedBookMenuNum = Integer.parseInt(br.readLine());
            boolean isRepeat = false;

            switch (selectedBookMenuNum) {
                case 0 :
                    System.out.println("[System] 0번을 입력하여 프로그램을 종료합니다.\n");
                    exit(0);
                    break;
                case 1 :
                    System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                    break;
                case 2 :
                    System.out.println("[System] 전체 도서 목록입니다.\n");
                    break;
                case 3 :
                    System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                    break;
                case 4 :
                    System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
                    break;
                case 5 :
                    System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
                    break;
                case 6 :
                    System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n\n");
                    break;
                case 7 :
                    System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
                    break;
                default :
                    System.out.println(INVALID_BOOK_MENU.getMessage());
                    System.out.println();
                    isRepeat = true;
            }

            if(!isRepeat) break;
        }

        return selectedBookMenuNum;
    }

}