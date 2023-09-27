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
        System.out.println("Q. 사용할 기능을 선택해주세요.");
        for(Menu menu : Menu.values())
            System.out.println(menu);

        Menu.selected(intInput());
    }

    public static void registerMenu() {
        BookDto data = new BookDto();

        system("도서 등록 메뉴로 넘어갑니다.");

        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        data.setTitle(stringInput());

        System.out.println("Q. 작가 이름을 입력하세요.");
        data.setAuthor(stringInput());

        System.out.println("Q. 페이지 수를 입력하세요.");
        data.setTotalPages(intInput());

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

    public static int intInput() {
        System.out.println();
        System.out.print("> ");
        int input = Integer.parseInt(sc.nextLine());
        System.out.println();
        return input;
    }

    public static String stringInput() {
        System.out.println();
        System.out.print("> ");
        String input = sc.nextLine();
        System.out.println();

        return input;
    }

    @Override
    public void run() {
        while(true) {
            try {
                menu();
            } catch (NumberFormatException e) { inputError(); }
        }
    }
}
