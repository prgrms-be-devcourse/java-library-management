package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static int modeNum;
    private static int fun;
    private static List<Book> bookList;
    private static BookService bookService;

    public static void main(String[] args) throws IOException {

        System.out.println("0. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");

        System.out.print("\n> ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        modeNum = Integer.parseInt(br.readLine());
        bookList = new ArrayList<>();
        bookService = new BookService();

        if(modeNum == 1) {
            System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
            while(true) {
                System.out.println("0. 사용할 기능을 선택해 주세요.");
                System.out.println("1. 도서 등록");
                System.out.println("2. 전체 도서 목록 조회");
                System.out.println("3. 제목으로 도서 검색");
                System.out.println("4. 도서 대여");
                System.out.println("5. 도서 반납");
                System.out.println("6. 도서 분실");
                System.out.println("7. 도서 삭제");

                System.out.print("\n> ");

                fun = Integer.parseInt(br.readLine());
                switch (fun) {
                    case 1:
                        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                        bookService.createBook(bookList);
                    case 2:
                        System.out.println("[System] 전체 도서 목록입니다.\n");
                        bookService.getAllBooks(bookList);
                }
            }
        }
    }
}