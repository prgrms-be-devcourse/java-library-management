package controller;

import manager.IOManager;
import service.BookService;

public class BookController {
    private BookService bookService;
    private final IOManager ioManager = new IOManager();

    public void selectMode() {
        ioManager.printSelectMode();
        String mode;
        try {
            mode = ioManager.getInput();
        } catch (Exception e) {
            ioManager.printSystem(e.getMessage());
            return;
        }
        runMode(mode);
    }

    public void runMode(String mode) {
        ModeType modeType = ModeType.findMode(mode);
        if (modeType == null) {
            ioManager.printSystem("잘못된 입력입니다.");
            return;
        }
        bookService = ModeType.findService(modeType, ioManager);
        ioManager.printSystem(modeType.getMessage());
        selectFunction();
    }

    public void selectFunction() {
        String function = "";
        while (!function.equals("8")) {
            ioManager.printSelectFunction();
            try {
                function = ioManager.getInput();
                switch (function) {
                    case "1" -> saveBook();
                    case "2" -> showBookList();
                    case "3" -> searchBook();
                    case "4" -> borrowBook();
                    case "5" -> returnBook();
                    case "6" -> reportLostBook();
                    case "7" -> deleteBook();
                }
            } catch (NumberFormatException e) {
                ioManager.printSystem("숫자를 입력해주세요.");
            } catch (Exception e) {
                ioManager.printSystem(e.getMessage());
            }
        }
    }

    private void reportLostBook() throws Exception {
        ioManager.printSystem("도서 분실 처리 메뉴로 넘어갑니다.");
        ioManager.printQuestion("분실 처리할 도서번호를 입력하세요.");
        bookService.reportLostBook(Integer.valueOf(ioManager.getInput()));
        ioManager.printSystem("도서가 분실 처리 되었습니다.");
    }

    private void returnBook() throws Exception {
        ioManager.printSystem("도서 반납 메뉴로 넘어갑니다.");
        ioManager.printQuestion("반납할 도서번호를 입력하세요.");
        bookService.returnBook(Integer.valueOf(ioManager.getInput()));
        ioManager.printSystem("도서가 반납 처리 되었습니다.");
    }

    private void borrowBook() throws Exception {
        ioManager.printSystem("도서 대여 메뉴로 넘어갑니다.");
        ioManager.printQuestion("대여할 도서번호를 입력하세요.");
        bookService.borrowBook(Integer.valueOf(ioManager.getInput()));
        ioManager.printSystem("도서가 대여 처리 되었습니다.");
    }

    private void searchBook() throws Exception {
        ioManager.printSystem("제목으로 도서 검색 메뉴로 넘어갑니다.");
        ioManager.printQuestion("검색할 도서 제목 일부를 입력하세요.");
        bookService.findBookByTitle(ioManager.getInput());
        ioManager.printSystem("검색된 도서 끝");
    }

    private void showBookList() {
        ioManager.printSystem("전체 도서 목록입니다.");
        bookService.showBookList();
        ioManager.printSystem("도서 목록 끝");
    }

    private void saveBook() throws Exception {
        ioManager.printSystem("도서 등록 메뉴로 넘어갑니다.");
        ioManager.printQuestion("등록할 도서 제목을 입력하세요.");
        String title = ioManager.getInput();

        ioManager.printQuestion("작가 이름을 입력하세요.");
        String author = ioManager.getInput();

        ioManager.printQuestion("페이지 수를 입력하세요.");
        Integer page = Integer.parseInt(ioManager.getInput());

        bookService.saveBook(title, author, page);

        ioManager.printSystem("도서 등록이 완료되었습니다.");
    }

    private void deleteBook() throws Exception {
        ioManager.printSystem("도서 삭제 처리 메뉴로 넘어갑니다.");
        ioManager.printQuestion("삭제 처리할 도서번호를 입력하세요.");
        Integer id = Integer.valueOf(ioManager.getInput());
        bookService.removeBook(id);
        ioManager.printSystem("도서가 삭제 처리 되었습니다.");
    }
}
