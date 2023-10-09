package devcourse.backend.view;

import devcourse.backend.business.ModeType;
import devcourse.backend.business.BookService;
import devcourse.backend.model.Book;
import devcourse.backend.model.BookStatus;

import java.util.Scanner;

import static devcourse.backend.model.BookStatus.*;

public class Console implements Runnable {
    private static final String NUMBER_REQUIRED = "숫자를 입력해주세요.";
    private static Scanner sc = new Scanner(System.in);
    private static BookService service;

    public Console(BookService service) {
        this.service = service;
    }

    public void menu() {
        StringBuffer prompt = new StringBuffer("사용할 기능을 선택해주세요.");
        for (Menu menu : Menu.values())
            prompt.append("\n").append(menu);

        Menu.selected(getIntInput(prompt.toString()));
    }

    public static void registerMenu() {
        printSystemMessage("도서 등록 메뉴로 넘어갑니다.");
        try {
            String title = getStringInput("등록할 도서 제목을 입력하세요.");
            String author = getStringInput("작가 이름을 입력하세요.");
            int totalPages = getIntInput("페이지 수를 입력하세요.");

            CreateBookDto data = new CreateBookDto(title, author, totalPages);

            service.registerBook(data);
        } catch (IllegalArgumentException e) {
            printSystemMessage(e.getMessage());
            return;
        }

        printSystemMessage("도서 등록이 완료되었습니다.");
    }

    public static void allBookMenu() {
        printSystemMessage("전체 도서 목록입니다.");

        service.getAllBooks().stream().forEach(b -> {
            printBook(b);
            printPartition();
        });

        printSystemMessage("도서 목록 끝");
    }

    public static void searchMenu() {
        printSystemMessage("제목으로 도서 검색 메뉴로 넘어갑니다.");

        service.searchBooks(getStringInput("검색할 도서 제목 일부를 입력하세요."))
                .stream()
                .forEach(b -> {
                    printBook(b);
                    printPartition();
                });

        printSystemMessage("검색된 도서 끝");
    }

    public static void rentMenu() {
        printSystemMessage("도서 대여 메뉴로 넘어갑니다.");

        try {
            service.rentBook(getLongInput("대여할 도서번호를 입력하세요"));
            printSystemMessage("도서가 대여 처리 되었습니다.");
        } catch (IllegalArgumentException e) {
            printSystemMessage(e.getMessage());
        }
    }

    public static void returnMenu() {
        printSystemMessage("도서 반납 메뉴로 넘어갑니다.");

        try {
            service.returnBook(getLongInput("반납할 도서번호를 입력하세요"));
            printSystemMessage("도서가 반납 처리 되었습니다.");
        } catch (IllegalArgumentException e) {
            printSystemMessage(e.getMessage());
        }
    }

    public static void reportMenu() {
        printSystemMessage("도서 분실 처리 메뉴로 넘어갑니다.");

        try {
            service.reportLoss(getLongInput("분실 처리할 도서번호를 입력하세요"));
            printSystemMessage("도서가 분실 처리 되었습니다.");
        } catch (IllegalArgumentException e) {
            printSystemMessage(e.getMessage());
        }
    }

    public static void deleteMenu() {
        printSystemMessage("도서 삭제 처리 메뉴로 넘어갑니다.");

        try {
            service.deleteBook(getLongInput("삭제 처리할 도서번호를 입력하세요"));
            printSystemMessage("도서가 삭제 처리 되었습니다.");
        } catch (NumberFormatException e) {
            printSystemMessage(NUMBER_REQUIRED);
        } catch (IllegalArgumentException e) {
            printSystemMessage(e.getMessage());
        }
    }

    public static int selectMode() {
        StringBuffer prompt = new StringBuffer("모드를 선택해주세요.");
        for (ModeType mode : ModeType.values())
            prompt.append("\n").append(mode);

        try {
            return getIntInput(prompt.toString());
        } catch (NumberFormatException e) {
            printSystemMessage(e.getMessage());
            return selectMode();
        }
    }

    private static void printSystemMessage(String s) {
        System.out.println();
        System.out.println("[System] " + s);
        System.out.println();
    }

    private static void printPartition() {
        System.out.print("""
                
                ------------------------------
                
                """);
    }

    private static void printBook(Book book) {
        System.out.println("도서번호 : " + book.getId());
        System.out.println("제목 : " + book.getTitle());
        System.out.println("작가 이름 : " + book.getAuthor());
        System.out.println("페이지 수 : " + book.getTotalPages());
        System.out.println("상태 : " + book.getStatus());
    }

    private static int getIntInput(String prompt) {
        System.out.println("Q. " + prompt);
        System.out.println();
        System.out.print("> ");
        try {
            int input = Integer.parseInt(sc.nextLine());
            return input;
        } catch (NumberFormatException e) {
            throw new NumberFormatException(NUMBER_REQUIRED);
        }
    }

    private static long getLongInput(String prompt) {
        System.out.println("Q. " + prompt);
        System.out.println();
        System.out.print("> ");
        try {
            long input = Long.parseLong(sc.nextLine());
            return input;
        } catch (NumberFormatException e) {
            throw new NumberFormatException(NUMBER_REQUIRED);
        }
    }

    private static String getStringInput(String prompt) {
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
                printSystemMessage(e.getMessage());
            }
        }
    }
}
