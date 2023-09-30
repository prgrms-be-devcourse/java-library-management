package com.programmers.library.controller;

import com.programmers.library.utils.Menu;
import com.programmers.library.service.LibraryService;

import java.util.Scanner;

public class LibraryController {

    private final LibraryService libraryService;
    private final Scanner scanner;


    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean isRunning = true;

        while(isRunning) {
            Menu menuType = Menu.selectMenu();

            switch (menuType) {
                case ADD:
                    String title = getStringInput(scanner, "등록할 도서 제목을 입력하세요.");
                    String author = getStringInput(scanner, "작가 이름을 입력하세요.");
                    int pages = getIntInput(scanner, "페이지 수를 입력하세요.");
                    libraryService.addBook(title, author, pages);
                    break;
                case VIEW:
                    libraryService.viewAllBooks();
                    break;
                case SEARCH:
                    title = getStringInput(scanner, "검색할 도서 제목 일부를 입력하세요.");
                    libraryService.searchBookByTitle(title);
                    break;
                case RENT:
                    int bookId = getIntInput(scanner, "대여할 도서번호를 입력하세요.");
                    libraryService.rentBook(bookId);
                    break;
                case RETURN:
                    bookId = getIntInput(scanner, "반납할 도서번호를 입력하세요.");
                    libraryService.returnBook(bookId);
                    break;
                case LOST:
                    bookId = getIntInput(scanner, "분실 처리할 도서번호를 입력하세요.");
                    libraryService.lostBook(bookId);
                    break;
                case DELETE:
                    bookId = getIntInput(scanner, "삭제 처리할 도서번호를 입력하세요.");
                    libraryService.deleteBook(bookId);
                    break;
                case EXIT:
                    isRunning = false;
                    break;
            }
        }

    }

    private static String getStringInput(Scanner scanner, String message) {
        System.out.print("Q. " + message + "\n> ");
        String input = scanner.nextLine();

        if(input.isEmpty()) {   // 빈 입력값에 대한 예외 처리
            System.out.println("[System] 입력값이 비어있습니다. 다시 입력해주세요.");
            return getStringInput(scanner, message);
        }
        return input;
    }

    private static int getIntInput(Scanner scanner, String message) {
        System.out.print("Q. " + message + "\n> ");
        int input = scanner.nextInt();
        scanner.nextLine(); // 개행문자 처리

        if(input <= 0) {    // 0보다 작은 입력값에 대한 예외 처리
            System.out.println("[System] 유효하지 않은 입력값입니다. 다시 입력해주세요.");
            return getIntInput(scanner, message);
        }

        return input;
    }
}