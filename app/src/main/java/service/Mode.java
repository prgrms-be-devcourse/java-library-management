package service;

import repository.NormalRepository;
import repository.TestRepository;

import java.io.*;

public class Mode {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    Service service;
    public Mode(String mode) throws IOException {
        if(mode.equals("normal")) service = new Service(new NormalRepository());
        else if(mode.equals("test")) service = new Service(new TestRepository());
    }

    public void run() throws IOException {
        printSelectList();

        String num = bf.readLine();
        switch (num) {
            case "1" -> {
                System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.");
                service.register();
            }
            case "2" -> {
                System.out.println("[System] 전체 도서 목록입니다.");
                service.list();
            }
            case "3" -> {
                System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");
                service.search();
            }
            case "4" -> {
                System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.");
                service.rental();
            }
            case "5" -> {
                System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.");
                service.returnBook();
            }
            case "6" -> {
                System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.");
                service.lostBook();
            }
            case "7" -> {
                System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.");
                service.deleteBook();
            }
            default -> {
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            }
        }
    }

    private void printSelectList() {
        System.out.println("Q. 사용할 기능을 선택해주세요.\n" +
                "1. 도서 등록\n" +
                "2. 전체 도서 목록 조회\n" +
                "3. 제목으로 도서 검색\n" +
                "4. 도서 대여\n" +
                "5. 도서 반납\n" +
                "6. 도서 분실\n" +
                "7. 도서 삭제");
    }
}
