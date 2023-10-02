package devcourse.backend.view;

import devcourse.backend.business.ModeType;
import devcourse.backend.business.BookService;
import devcourse.backend.medel.BookStatus;

import java.util.Scanner;

import static devcourse.backend.medel.BookStatus.*;

public class Console implements Runnable {
    private static Scanner sc = new Scanner(System.in);
    private static BookService service;

    public Console(BookService service) {
        this.service = service;
    }

    public void menu() {
        StringBuffer prompt = new StringBuffer("사용할 기능을 선택해주세요.");
        for (Menu menu : Menu.values())
            prompt.append("\n").append(menu);

        Menu.selected(intInput(prompt.toString()));
    }

    public static void registerMenu() {
        BookDto data = new BookDto();

        system("도서 등록 메뉴로 넘어갑니다.");
        try {
            data.setTitle(stringInput("등록할 도서 제목을 입력하세요."));
            data.setAuthor(stringInput("작가 이름을 입력하세요."));
            data.setTotalPages(intInput("페이지 수를 입력하세요."));

            service.registerBook(data);
        } catch (IllegalArgumentException e) {
            system(e.getMessage());
            return;
        }

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
        } catch (NumberFormatException e) {
            system("숫자를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            BookStatus status = BookStatus.get(e.getMessage()).get();
            if (status == BORROWED) system("이미 대여 중인 도서입니다.");
            else if (status == ARRANGING) system("정리 중인 도서입니다. 5분 후 다시 대여해 주세요.");
            else if (status == LOST) system("현재 분실 처리된 도서입니다.");
        }
    }

    public static void returnMenu() {
        system("도서 반납 메뉴로 넘어갑니다.");

        try {
            service.returnBook(longInput("반납할 도서번호를 입력하세요"));
            system("도서가 반납 처리 되었습니다.");
        } catch (NumberFormatException e) {
            system("숫자를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            BookStatus status = BookStatus.get(e.getMessage()).get();
            if (status == AVAILABLE) system("원래 대여가 가능한 도서입니다.");
            else if (status == ARRANGING) system("정리 중인 도서입니다.");
        }
    }

    public static void reportMenu() {
        system("도서 분실 처리 메뉴로 넘어갑니다.");

        try {
            service.reportLoss(longInput("분실 처리할 도서번호를 입력하세요"));
            system("도서가 분실 처리 되었습니다.");
        } catch (NumberFormatException e) {
            system("숫자를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            system("이미 분실 처리된 도서입니다.");
        }
    }

    public static void deleteMenu() {
        system("도서 삭제 처리 메뉴로 넘어갑니다.");

        try {
            service.deleteBook(longInput("삭제 처리할 도서번호를 입력하세요"));
            system("도서가 삭제 처리 되었습니다.");
        } catch (NumberFormatException e) {
            system("숫자를 입력해주세요.");
        } catch (IllegalArgumentException e) {
            system(e.getMessage());
        }
    }

    public static int selectMode() {
        StringBuffer prompt = new StringBuffer("모드를 선택해주세요.");
        for (ModeType mode : ModeType.values())
            prompt.append("\n").append(mode);

        try {
            return intInput(prompt.toString());
        } catch (NumberFormatException e) {
            system(e.getMessage());
            return selectMode();
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

    public static int intInput(String prompt) {
        System.out.println("Q. " + prompt);
        System.out.println();
        System.out.print("> ");
        try {
            int input = Integer.parseInt(sc.nextLine());
            return input;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("숫자를 입력해주세요.");
        }
    }

    public static long longInput(String prompt) {
        System.out.println("Q. " + prompt);
        System.out.println();
        System.out.print("> ");
        try {
            long input = Long.parseLong(sc.nextLine());
            return input;
        } catch (NumberFormatException e) {
            throw new NumberFormatException("숫자를 입력해주세요.");
        }
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
            } catch (IllegalArgumentException e) {
                system(e.getMessage());
            }
        }
    }
}
