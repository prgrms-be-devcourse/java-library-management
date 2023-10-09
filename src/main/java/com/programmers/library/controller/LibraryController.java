package com.programmers.library.controller;

import com.programmers.library.utils.ConsoleIO;
import com.programmers.library.utils.MenuType;
import com.programmers.library.service.LibraryService;

public class LibraryController {

    private final LibraryService libraryService;
    private final ConsoleIO console;


    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.console = new ConsoleIO();
    }

    public void run() {
        boolean isRunning = true;

        while(isRunning) {
            MenuType menuType = MenuType.selectMenu();

            switch (menuType) {
                case ADD:
                    String title = console.getStringInput("Q. 등록할 도서 제목을 입력하세요.");
                    String author = console.getStringInput("Q. 작가 이름을 입력하세요.");
                    int pages = console.getIntInput("Q. 페이지 수를 입력하세요.");
                    libraryService.addBook(title, author, pages);
                    break;
                case VIEW:
                    libraryService.viewAllBooks();
                    break;
                case SEARCH:
                    title = console.getStringInput("Q. 검색할 도서 제목 일부를 입력하세요.");
                    libraryService.searchBookByTitle(title);
                    break;
                case RENT:
                    int bookId = console.getIntInput("Q. 대여할 도서번호를 입력하세요.");
                    libraryService.rentBook(bookId);
                    break;
                case RETURN:
                    bookId = console.getIntInput("Q. 반납할 도서번호를 입력하세요.");
                    libraryService.returnBook(bookId);
                    break;
                case LOST:
                    bookId = console.getIntInput("Q. 분실 처리할 도서번호를 입력하세요.");
                    libraryService.lostBook(bookId);
                    break;
                case DELETE:
                    bookId = console.getIntInput("Q. 삭제 처리할 도서번호를 입력하세요.");
                    libraryService.deleteBook(bookId);
                    break;
                case EXIT:
                    isRunning = false;
                    break;
            }
        }

    }
}