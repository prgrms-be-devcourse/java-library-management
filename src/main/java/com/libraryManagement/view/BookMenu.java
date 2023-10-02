package com.libraryManagement.view;

import com.libraryManagement.controller.BookController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class BookMenu {

    private final BookController bookController;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public BookMenu(BookController bookController) {
        this.bookController = bookController;
    }

    public void displayBookMenu() throws IOException {

        while(true) {

            System.out.println("0. 사용할 기능을 선택해주세요.");
            System.out.println("1. 도서 등록");
            System.out.println("2. 전체 도서 목록 조회");
            System.out.println("3. 제목으로 도서 검색");
            System.out.println("4. 도서 대여");
            System.out.println("5. 도서 반납");
            System.out.println("6. 도서 분실");
            System.out.println("7. 도서 삭제" + "\n");

            int num = Integer.parseInt(br.readLine());

            switch(num) {
                case 1 :
                    System.out.println("[System] 도서 등록 메뉴로 넘어갑니다." + "\n");
                    bookController.insertBook();
                    System.out.println("[System] 도서 등록이 완료되었습니다." + "\n");
                    break;
                    /*
                case 2 :
                    menuController.bookUpdate(bookId(), bookAdd());
                    break;
                case 3 :
                    menuController.bookDelete(bookId());
                    break;
                case 4 :
                    menuController.bookSelectId(bookId());
                    break;
                case 5 :
                    menuController.bookSelectTitle(bookTitle());
                    break;
                case 6 :
                    menuController.bookSelectAll();
                    break;
*/
                case 7 :
                    System.out.println("프로그램을 종료합니다.");
                    return;
                default :
                    System.out.println("번호를 다시 입력해주세요." + "\n");
            }

        }
    }

}