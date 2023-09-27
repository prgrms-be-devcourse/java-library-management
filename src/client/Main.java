package client;

import repository.FileRepository;
import repository.TestRepository;
import service.Service;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    private static Service service;
    public static void main(String args[]) throws IOException {
        boolean mode = selectMode();
        if(!mode) {
            return;
        }

        while(true){

            Integer function = selectFunction();
            if(function==null){
                continue;
            }

            run(function);
        }
    }

    public static boolean selectMode(){

        System.out.println("Q. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");

        System.out.print("\n >");
        int mode = sc.nextInt();

        if (mode == 1){
            service = new Service(new FileRepository());
            System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
            return true;
        }
        else if(mode == 2){
            service = new Service(new TestRepository());
            System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
            return true;
        }
        else {
            System.out.println("오류");
            return false;
        }
    }

    public static Integer selectFunction(){
        System.out.println("Q. 사용할 기능을 선택해주세요.\n" +
                "1. 도서 등록\n" +
                "2. 전체 도서 목록 조회\n" +
                "3. 제목으로 도서 검색\n" +
                "4. 도서 대여\n" +
                "5. 도서 반납\n" +
                "6. 도서 분실\n" +
                "7. 도서 삭제");

        System.out.print("\n >");
        int function = sc.nextInt();
        return function>=1&&function<=7?function:null;

    }

    public static void run(int function) throws IOException {
        switch (function){
            case 1:
                System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                service.addBook();
                break;
            case 2:
                System.out.println("[System] 전체 도서 목록입니다.\n");
                service.getAll();
                break;
            case 3:
                System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                service.searchName();
                break;
            case 4:
                System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
                service.rentalBook();
                break;
            case 5:
                System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
                service.organizeBook();
                break;
            case 6:
                System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
                service.lostBook();
                break;
            case 7:
                System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
                service.deleteBook();
                break;
        }
    }
}