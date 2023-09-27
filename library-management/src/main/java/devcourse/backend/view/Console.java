package devcourse.backend.view;
import devcourse.backend.business.BookService;
import devcourse.backend.medel.Book;

import java.util.List;
import java.util.Scanner;

public class Console implements Runnable {
    private static Scanner sc = new Scanner(System.in);
    private static BookService service;

    public Console(BookService service) {
        this.service = service;
    }

    public void menu() {
        StringBuffer prompt = new StringBuffer("사용할 기능을 선택해주세요.");
        for(Menu menu : Menu.values())
            prompt.append("\n").append(menu);

        Menu.selected(intInput(prompt.toString()));
    }

    public static void registerMenu() {
        BookDto data = new BookDto();

        system("도서 등록 메뉴로 넘어갑니다.");

        data.setTitle(stringInput("등록할 도서 제목을 입력하세요."));
        data.setAuthor(stringInput("작가 이름을 입력하세요."));
        data.setTotalPages(intInput("페이지 수를 입력하세요."));

        service.registerBook(data);

        system("도서 등록이 완료되었습니다.");
    }

    public static void allBookMenu() {
        system("전체 도서 목록입니다.");

        service.getAllBooks().stream().forEach(b -> {
            System.out.println(b);
            partition();
        });

        system("도서 목록 끝");
    }

    public static void searchMenu() {
        system("제목으로 도서 검색 메뉴로 넘어갑니다.");

        service.searchBooks(stringInput("검색할 도서 제목 일부를 입력하세요."))
                .stream()
                .forEach(b -> {
                    System.out.println(b);
                    partition();
                });

        system("검색된 도서 끝");
    }

    public static void rentMenu() {
        system("도서 대여 메뉴로 넘어갑니다.");
        try {
            service.rentBook(longInput("대여할 도서번호를 입력하세요"));
            system("도서가 대여 처리 되었습니다.");
        } catch (IllegalArgumentException e) {
            switch (e.getMessage()) {
                case "대여 중" : system("이미 대여 중인 도서입니다."); break;
                case "도서 정리중" : system("정리 중인 도서입니다. 5분 후 다시 대여해 주세요."); break;
                case "분실됨" : system("현재 분실 처리된 도서입니다."); break;
            }
        }

    }

    private static void system(String s) {
        System.out.println();
        System.out.println("[System] " + s);
        System.out.println();
    }

    private static void partition() {
        System.out.println();
        System.out.println("------------------------------");
        System.out.println();
    }

    public void inputError() {
        System.out.println();
        System.out.println("입력이 잘못되었습니다.");
        System.out.println();
    }

    public static int intInput(String prompt) {
        System.out.println("Q. " + prompt);
        System.out.println();
        System.out.print("> ");
        int input = Integer.parseInt(sc.nextLine());
        return input;
    }

    public static long longInput(String prompt) {
        System.out.println("Q. " + prompt);
        System.out.println();
        System.out.print("> ");
        long input = Long.parseLong(sc.nextLine());
        return input;
    }

    public static String stringInput(String prompt) {
        System.out.println("Q. " + prompt);
        System.out.println();
        System.out.print("> ");
        String input = sc.nextLine();
        System.out.println();

        return input;
    }

    @Override
    public void run() {
        while (true) {
            try {
                menu();
            } catch (NumberFormatException e) {
                inputError();
            }
        }
    }
}
