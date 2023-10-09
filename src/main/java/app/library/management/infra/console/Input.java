package app.library.management.infra.console;

import app.library.management.infra.port.dto.request.BookRequestDto;
import app.library.management.infra.port.Request;

import java.util.Scanner;

public class Input implements Request {

    private static Scanner sc;

    @Override
    public BookRequestDto saveBookRequest() {
        System.out.println();
        System.out.println("Q. 등록할 도서 제목을 입력하세요.\n");
        sc = new Scanner(System.in);
        String title = sc.nextLine();
        System.out.println("Q. 작가 이름을 입력하세요.\n");
        sc = new Scanner(System.in);
        String author = sc.nextLine();
        System.out.println("Q. 페이지 수를 입력하세요.\n");
        sc = new Scanner(System.in);
        int pages = sc.nextInt();

        return new BookRequestDto(title, author, pages);
    }

    @Override
    public String findByTitleRequest() {
        System.out.println();
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
        sc = new Scanner(System.in);
        return sc.nextLine();
    }

    @Override
    public int rentByIdRequest() {
        System.out.println();
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 대여할 도서번호를 입력하세요.\n");
        sc = new Scanner(System.in);
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    public int returnByIdRequest() {
        System.out.println();
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 반납할 도서번호를 입력하세요");
        sc = new Scanner(System.in);
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    public int reportLostByIdRequest() {
        System.out.println();
        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요");
        sc = new Scanner(System.in);
        return Integer.parseInt(sc.nextLine());
    }

    @Override
    public int deleteByIdRequest() {
        System.out.println();
        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요.\n");
        sc = new Scanner(System.in);
        return Integer.parseInt(sc.nextLine());
    }

}
