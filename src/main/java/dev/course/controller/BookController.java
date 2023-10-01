package dev.course.controller;

import dev.course.config.AppConfig;
import dev.course.domain.AppConstants;
import dev.course.domain.Book;
import dev.course.exception.ConsoleIOFailureException;
import dev.course.exception.FuncFailureException;
import dev.course.manager.ConsoleManager;
import dev.course.service.LibraryManagement;

public class BookController {

    private final AppConfig appConfig;
    private final ConsoleManager consoleManager;
    private LibraryManagement library;

    public BookController(AppConfig appConfig) {
        this.appConfig = appConfig;
        this.consoleManager = appConfig.getConsoleManager();
    }

    public void start() {

        int modeId;
        boolean flag = false;

        try {
            while (true) {

                consoleManager.printMode();
                modeId = consoleManager.getInteger();

                switch (modeId) {
                    case AppConstants.GENERAL:
                        System.out.println("[System] 일반 모드 애플리케이션을 실행합니다.\n");
                        flag = true;
                        break;
                    case AppConstants.TEST:
                        System.out.println("[System] 테스트 모드 애플리케이션을 실행합니다.\n");
                        flag = true;
                        break;
                    default:
                        System.out.println("[System] 잘못된 모드의 접근입니다.\n");
                }

                if (flag) break;
            }

            this.library = appConfig.getLibrary(modeId);
            play();

        } catch (ConsoleIOFailureException e) {
            System.out.println(e.getMessage());
        }
    }

    public void play() {

        boolean quit = false;

        while (true) {

            consoleManager.printMenu();

            try {
                switch (consoleManager.getInteger()) {
                    case 1:
                        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                        library.add(consoleAboutBookInfo());
                        break;
                    case 2:
                        System.out.println("[System] 전체 도서 목록입니다.\n");
                        library.getAll();
                        break;
                    case 3:
                        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
                        library.findByTitle(consoleManager.getString());
                        break;
                    case 4:
                        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
                        System.out.println("Q. 대여할 도서번호를 입력하세요.");
                        library.borrow(consoleManager.getLong());
                        break;
                    case 5:
                        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
                        System.out.println("Q. 반납할 도서번호를 입력하세요.");
                        library.returns(consoleManager.getLong());
                        break;
                    case 6:
                        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
                        System.out.println("Q. 분실 처리할 도서번호를 입력하세요.");
                        library.lost(consoleManager.getLong());
                        break;
                    case 7:
                        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
                        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요.");
                        library.delete(consoleManager.getLong());
                        break;
                    case 8:
                        System.out.println("[System] 시스템이 종료되었습니다.\n");
                        quit = true;
                        break;
                }
                if (quit) break;
            } catch (FuncFailureException | ConsoleIOFailureException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Book consoleAboutBookInfo() {

        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = consoleManager.getString();

        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = consoleManager.getString();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int pageNum = consoleManager.getInteger();

        return library.createBook(title, author, pageNum);
    }
}
