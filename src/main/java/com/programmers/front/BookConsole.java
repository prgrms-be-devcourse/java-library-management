package com.programmers.front;

import com.programmers.domain.Book;

import java.util.List;
import java.util.Scanner;

public class BookConsole {

    private static final Scanner scanner = new Scanner(System.in);


    public static String chooseMode(){
        System.out.println("모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");
        System.out.println();
        System.out.print("> ");
        return scanner.next();
    }

    public static void commonModeStart(){
        System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.");
    }

    public static void testModeStart(){
        System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.");
    }

    public static String chooseFunction(){
        System.out.println("Q. 사용할 기능을 선택해주세요.");
        System.out.println("1. 도서 등록");
        System.out.println("2. 전체 도서 목록 조회");
        System.out.println("3. 제목으로 도서 검색");
        System.out.println("4. 도서 대여");
        System.out.println("5. 도서 반납");
        System.out.println("6. 도서 분실");
        System.out.println("7. 도서 삭제");
        System.out.println("8. 종료");
        System.out.println();
        System.out.print("> ");
        return scanner.next();
    }
    public static void showAllBooks(List<Book> books){
        if(books.size() == 0){
            System.out.println("[System] 도서가 존재하지 않습니다.");
            return;
        }
        System.out.println();
        System.out.println("[System] 전체 도서 목록입니다.");
        for(Book book : books){
            System.out.println("도서번호 : " + book.getBookId());
            System.out.println("제목 : " + book.getTitle());
            System.out.println("작가 이름 : " + book.getAuthor());
            System.out.println("페이지 수 : " + book.getTotalPageNumber());
            System.out.println("상태 : " + book.getBookStatus().getDetailStatus());
            System.out.println();
            System.out.println("------------------------------ ");
            System.out.println();
        }
        System.out.println("[System] 도서 목록 끝");
    }

    public static void startEnrollMode(){
        System.out.println();
        System.out.println("[System] 도서 등록 메뉴로 애플리케이션을 실행합니다.");
        System.out.println();
    }

    public static String inputBookName(){
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        System.out.println();
        System.out.print("> ");
        //TODO: 왜 첫 nextLine 이 제목을 못 받는지 확인 필요
        scanner.nextLine();
        return scanner.nextLine();
    }

    public static String inputAuthorName(){
        System.out.println("Q. 작가 이름을 입력하세요.");
        System.out.println();
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static String inputBookTotalPage(){
        System.out.println("Q. 페이지 수를 입력하세요.");
        System.out.println();
        System.out.print("> ");
        return scanner.next();
    }

    public static void completeBookEnrollment(){
        System.out.println();
        System.out.println("[System] 도서 등록이 완료되었습니다.");
    }

    public static void wrongMenu(){
        System.out.println();
        System.out.println("[System] 메뉴를 다시 선택해 주십시오.");
    }

    public static String searchBookMode() {
        System.out.println();
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");
        System.out.println();
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요. \n");
        System.out.print("> ");
        return scanner.nextLine();
    }

    public static String rentBookMode() {
        System.out.println();
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.");
        System.out.println();
        System.out.println("Q. 대여할 도서번호를 입력하세요 \n");
        System.out.println();
        System.out.print("> ");
        return scanner.next();
    }

    public static void rentSuccess() {
        System.out.println("[System] 도서가 대여 처리 되었습니다.");
    }

    public static String returnBookMode() {
        System.out.println();
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.");
        System.out.println();
        System.out.println("Q. 반납할 도서번호를 입력하세요 \n");
        System.out.println();
        System.out.print("> ");
        return scanner.next();
    }

    public static void returnSuccess() {
        System.out.println("[System] 도서가 반납 처리 되었습니다.");
    }

    public static String loseBookMode() {
        System.out.println();
        System.out.println("[System] 도서 분실 메뉴로 넘어갑니다.");
        System.out.println();
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요 \n");
        System.out.println();
        System.out.print("> ");
        return scanner.next();
    }

    public static void loseBookFinished() {
        System.out.println("[System] 도서가 분실 처리 되었습니다. ");
    }

    public static String deleteBookMode() {
        System.out.println();
        System.out.println("[System] 도서 삭제 메뉴로 넘어갑니다.");
        System.out.println();
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요 \n");
        System.out.println();
        System.out.print("> ");
        return scanner.next();    }

    public static void deleteBookFinished() {
        System.out.println("[System] 도서가 삭제 처리 되었습니다. ");
    }

    public static void nothingFound() {
        System.out.println("[System] 찾으시는 도서가 존재하지 않습니다. ");
    }
}
