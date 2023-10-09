package app.library.management.infra.console;

import java.util.Scanner;

public class Console {

    public int inputInt() {
        Scanner sc = new Scanner(System.in);
        return Integer.parseInt(sc.nextLine());
    }

    public void selectMode() {
        System.out.println();
        System.out.println("Q. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드\n");
    }

    public void selectMenu() {
        System.out.println();
        System.out.println("Q. 사용할 기능을 선택해주세요.");
        System.out.println("1. 도서 등록");
        System.out.println("2. 전체 도서 목록 조회");
        System.out.println("3. 제목으로 도서 검색");
        System.out.println("4. 도서 대여");
        System.out.println("5. 도서 반납");
        System.out.println("6. 도서 분실");
        System.out.println("7. 도서 삭제");
        System.out.println("8. 종료\n");
    }
}
