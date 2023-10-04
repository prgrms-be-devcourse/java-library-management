package dev.course.controller;

import dev.course.config.AppConfig;
import dev.course.domain.AppConstants;
import dev.course.domain.Book;
import dev.course.exception.ConsoleIOFailureException;
import dev.course.exception.FuncFailureException;
import dev.course.exception.MaxRetryFailureException;
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

        String input;
        int modeId = 0;
        int count = AppConstants.INIT_RETRY;

        try {

            // WRONG input is available for 3 times
            while (count < AppConstants.MAX_RETRY) {

                consoleManager.printMode();
                input = consoleManager.getInput();

                try {
                    modeId = Integer.parseInt(input);

                    switch (modeId) {
                        case AppConstants.GENERAL:
                            System.out.println("[System] 일반 모드 애플리케이션을 실행합니다.\n");
                            count = AppConstants.SUCCESS;
                            break;
                        case AppConstants.TEST:
                            System.out.println("[System] 테스트 모드 애플리케이션을 실행합니다.\n");
                            count = AppConstants.SUCCESS;
                            break;
                        default:
                            // 1. WRONG input : Non-exist mode
                            ++count;
                            printRetryMessage(count);
                    }

                } catch (NumberFormatException e) {
                    // 2. WRONG input : Invalid-type of input
                    ++count;
                    printRetryMessage(count);
                }
            }

            this.library = appConfig.getLibrary(modeId);
            play();

        } catch (ConsoleIOFailureException | MaxRetryFailureException e) {
            System.out.println(e.getMessage());
        }
    }

    public void play() {

        String input;
        int func = 0;
        int count = AppConstants.INIT_RETRY;

        // WRONG input is available for 3 times
        // 1. Invalid-type of input of GET FUNC
        // 2. Invalid-type of input of FUNC
        while (count < AppConstants.MAX_RETRY) {

            try {

                consoleManager.printMenu();

                input = consoleManager.getInput();
                func = Integer.parseInt(input); // NumberFormatException

                consoleManager.printMsg(func);

                switch (func) {
                    case 1:
                        library.add(createBookUsingConsole());
                        break;
                    case 2:
                        library.getAll();
                        break;
                    case 3:
                        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
                        library.findByTitle(consoleManager.getInput());
                        break;
                    case 4:
                        System.out.println("Q. 대여할 도서번호를 입력하세요.");
                        library.borrow(consoleManager.getLong());
                        break;
                    case 5:
                        System.out.println("Q. 반납할 도서번호를 입력하세요.");
                        library.returns(consoleManager.getLong());
                        break;
                    case 6:
                        System.out.println("Q. 분실 처리할 도서번호를 입력하세요.");
                        library.lost(consoleManager.getLong());
                        break;
                    case 7:
                        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요.");
                        library.delete(consoleManager.getLong());
                        break;
                    case 8:
                        count = AppConstants.QUIT;
                        break;
                    default:
                        // 1. WRONG input : Non-exist func
                        ++count;
                        printRetryMessage(count);
                }

            // 2. WRONG input : Invalid-type of input
            } catch (NumberFormatException e) {
                ++count;
                printRetryMessage(count);
            } catch (FuncFailureException | ConsoleIOFailureException | MaxRetryFailureException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("[System] Unexpected Exception Occurred: ");
                e.printStackTrace();
            }
        }
    }

    public Book createBookUsingConsole() {

        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = consoleManager.getInput();

        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = consoleManager.getInput();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int pageNum = consoleManager.getInteger();

        return library.createBook(title, author, pageNum);
    }

    private void printRetryMessage(int count) {
        if (count == AppConstants.MAX_RETRY) {
            throw new MaxRetryFailureException("[System] 잘못된 정보가 3회 입력되었습니다. 작업을 종료합니다.\n");
        }
        System.out.println("[System] 잘못된 정보가 " + count + "회 입력되었습니다. (재입력: 'Y' / 종료 'N')\n");
    }
}
