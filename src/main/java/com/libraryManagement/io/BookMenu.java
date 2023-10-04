package com.libraryManagement.io;

import com.libraryManagement.controller.BookController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.libraryManagement.domain.ChangeBookStatus.*;
import static java.lang.System.exit;

public class BookMenu {

    private final BookController bookController;
    private List<String> menuList;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public BookMenu(BookController bookController) {
        this.bookController = bookController;

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

    public void displayBookMenu() throws IOException, InterruptedException {
        while(true) {
            for(int i = 0; i < menuList.size(); i++){
                System.out.println(i + ". " + menuList.get(i));
            }
            System.out.println();

            int selectMenuNum = Integer.parseInt(br.readLine());

            if(selectMenuNum == 0){
                System.out.println("0번을 입력하여 프로그램을 종료합니다.\n");
                exit(0);
            }
            String selectMenu = menuList.get(selectMenuNum);

            switch(selectMenu) {
                case "도서 등록" :
                    System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                    bookController.insertBook();
                    System.out.println("[System] 도서 등록이 완료되었습니다.\n");
                    break;
                case "전체 도서 목록 조회" :
                    System.out.println("[System] 전체 도서 목록입니다.\n");
                    bookController.findBooks();
                    System.out.println("[System] 도서 목록 끝\n");
                    break;
                case "제목으로 도서 검색" :
                    System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                    bookController.findBookByTitle();
                    System.out.println("[System] 검색된 도서 끝\n");
                    break;
                case "도서 대여" :
                    System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
                    bookController.updateBookStatus(APPLYRENT.name());
                    break;
                case "도서 반납" :
                    System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
                    bookController.updateBookStatus(APPLYRETURN.name());
                    break;
                case "도서 분실" :
                    System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n\n");
                    bookController.updateBookStatus(APPLYLOST.name());
                    break;
                case "도서 삭제" :
                    System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
                    bookController.updateBookStatus(APPLYDELETE.name());
                    break;
                default :
                    System.out.println("번호를 다시 입력해주세요.");
                    System.out.println("종료하려면 -1을 입력해주세요.\n");
            }

        }
    }

}