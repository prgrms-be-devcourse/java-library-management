package com.programmers.library_management.business;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.repository.ProductBookRepository;
import com.programmers.library_management.repository.TestBookRepository;
import com.programmers.library_management.service.LibraryManagementService;
import com.programmers.library_management.utils.ConsoleIOManager;

import java.io.IOException;

public class LibraryManagement {
    private final ConsoleIOManager consoleIOManager;
    private LibraryManagementService libraryManagementService;

    public LibraryManagement(ConsoleIOManager consoleIOManager) {
        this.consoleIOManager = consoleIOManager;
    }

    public boolean selectManagementMode() {
        consoleIOManager.printModeMenu();
        try {
            String input = consoleIOManager.getInput();
            switch (input) {
                case "1" -> {
                    libraryManagementService = new LibraryManagementService(new ProductBookRepository());
                    consoleIOManager.printSystemMsg("일반 모드로 애플리케이션을 실행합니다.");
                }
                case "2" -> {
                    libraryManagementService = new LibraryManagementService(new TestBookRepository());
                    consoleIOManager.printSystemMsg("테스트 모드로 애플리케이션을 실행합니다.");
                }
                default -> {
                    consoleIOManager.printSystemMsg("잘못된 모드 선택입니다.");
                    return false;
                }
            }
        } catch (IOException e) {
            consoleIOManager.printIOExceptionMsg();
            return false;
        }
        return true;
    }

    public void run() {
        String input = "";
        do {
            consoleIOManager.printFuncMenu();
            try {
                input = consoleIOManager.getInput();
                switch (input) {
                    case "1" -> addBook();
                    case "2" -> showAllBook();
                    case "3" -> searchBook();
                    case "4" -> rantBook();
                    case "5" -> returnBook();
                    case "6" -> lostBook();
                    case "7" -> deleteBook();
                    case "0" -> consoleIOManager.printSystemMsg("종료합니다.");
                    default -> consoleIOManager.printSystemMsg("잘못된 기능 선택입니다.");
                }
            } catch (IOException e) {
                consoleIOManager.printIOExceptionMsg();
                break;
            } catch (NumberFormatException e) {
                consoleIOManager.printNumberFormatExceptionMsg();
            } catch (Exception e) {
                consoleIOManager.printSystemMsg(e.getMessage());
            }
        } while (!input.equals("0"));
    }

    private void addBook() throws Exception {
        consoleIOManager.printSystemMsg("도서 등록 메뉴로 넘어갑니다.");
        consoleIOManager.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = consoleIOManager.getInput();
        consoleIOManager.println("Q. 작가 이름을 입력하세요");
        String writer = consoleIOManager.getInput();
        consoleIOManager.println("Q. 페이지 수를 입력하세요");
        String bookPages = consoleIOManager.getInput();
        libraryManagementService.addBook(title, writer, Integer.parseInt(bookPages));
        consoleIOManager.printSystemMsg("도서 등록이 완료되었습니다.");
    }

    private void showAllBook() {
        consoleIOManager.printSystemMsg("전체 도서 목록입니다.");
        for (Book book : libraryManagementService.showAllBooks()) {
            consoleIOManager.println(book.toString());
            consoleIOManager.println("------------------------------");
        }
        consoleIOManager.printSystemMsg("도서 목록 끝");
    }

    private void searchBook() throws IOException {
        consoleIOManager.printSystemMsg("제목으로 도서 검색 메뉴로 넘어갑니다.");
        consoleIOManager.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        String searchText = consoleIOManager.getInput();
        for (Book book : libraryManagementService.searchBook(searchText)) {
            consoleIOManager.println(book.toString());
            consoleIOManager.println("------------------------------");
        }
        consoleIOManager.printSystemMsg("검색된 도서 끝");
    }

    private void rantBook() throws Exception {
        consoleIOManager.printSystemMsg("도서 대여 메뉴로 넘어갑니다.");
        consoleIOManager.println("Q. 대여할 도서 번호를 입력하세요");
        String bookNumber = consoleIOManager.getInput();
        libraryManagementService.rantBook(Integer.parseInt(bookNumber));
        consoleIOManager.printSystemMsg("도서가 대여 처리 되었습니다.");

    }

    private void returnBook() throws Exception {
        consoleIOManager.printSystemMsg("도서 반납 메뉴로 넘어갑니다.");
        consoleIOManager.println("Q. 반납할 도서 번호를 입력하세요");
        String bookNumber = consoleIOManager.getInput();
        libraryManagementService.returnBook(Integer.parseInt(bookNumber));
        consoleIOManager.printSystemMsg("도서가 반납 처리 되었습니다.");
    }

    private void lostBook() throws Exception {
        consoleIOManager.printSystemMsg("도서 분실 처리 메뉴로 넘어갑니다.");
        consoleIOManager.println("Q. 분실 처리할 도서번호를 입력하세요");
        String bookNumber = consoleIOManager.getInput();
        libraryManagementService.lostBook(Integer.parseInt(bookNumber));
        consoleIOManager.printSystemMsg("도서가 분실 처리 되었습니다.");
    }

    private void deleteBook() throws Exception {
        consoleIOManager.printSystemMsg("도서 삭제 처리 메뉴로 넘어갑니다.");
        consoleIOManager.println("Q. 삭제 처리할 도서번호를 입력하세요");
        String bookNumber = consoleIOManager.getInput();
        libraryManagementService.deleteBook(Integer.parseInt(bookNumber));
        consoleIOManager.printSystemMsg("도서가 삭제 처리 되었습니다.");

    }

}
