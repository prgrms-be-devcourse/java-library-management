package controller;

import manager.console.InputManager;
import manager.console.OutputManager;
import service.BookService;

public class BookController {
    private final BookService bookService;
    private final InputManager inputManager;
    private final OutputManager outputManager;


    public BookController(BookService bookService, InputManager inputManager, OutputManager outputManager) {
        this.bookService = bookService;
        this.inputManager = inputManager;
        this.outputManager = outputManager;
    }

    public void selectMenu() {
        String function = "";
        while (!function.equals("8")) {
            outputManager.printSelectedMenu();
            try {
                switch (function = inputManager.getInput()) {
                    case "1" -> saveBook();
                    case "2" -> showBookList();
                    case "3" -> searchBook();
                    case "4" -> borrowBook();
                    case "5" -> returnBook();
                    case "6" -> reportLostBook();
                    case "7" -> deleteBook();
                }
            } catch (NumberFormatException e) {
                outputManager.printSystem("숫자를 입력해주세요.");
            } catch (Exception e) {
                outputManager.printSystem(e.getMessage());
            }
        }
    }

    protected void reportLostBook() throws Exception{
        outputManager.printSystem("도서 분실 처리 메뉴로 넘어갑니다.");
        outputManager.printQuestion("분실 처리할 도서번호를 입력하세요.");
        bookService.reportLostBook(Integer.valueOf(inputManager.getInput()));
        outputManager.printSystem("도서가 분실 처리 되었습니다.");
    }

    protected void returnBook() throws Exception {
        outputManager.printSystem("도서 반납 메뉴로 넘어갑니다.");
        outputManager.printQuestion("반납할 도서번호를 입력하세요.");
        bookService.returnBook(Integer.valueOf(inputManager.getInput()));
        outputManager.printSystem("도서가 반납 처리 되었습니다.");
    }

    protected void borrowBook() throws Exception {
        outputManager.printSystem("도서 대여 메뉴로 넘어갑니다.");
        outputManager.printQuestion("대여할 도서번호를 입력하세요.");
        bookService.borrowBook(Integer.valueOf(inputManager.getInput()));
        outputManager.printSystem("도서가 대여 처리 되었습니다.");
    }

    protected void searchBook() throws Exception {
        outputManager.printSystem("제목으로 도서 검색 메뉴로 넘어갑니다.");
        outputManager.printQuestion("검색할 도서 제목 일부를 입력하세요.");
        bookService.findBookByTitle(inputManager.getInput());
        outputManager.printSystem("검색된 도서 끝");
    }

    protected void showBookList() {
        outputManager.printSystem("전체 도서 목록입니다.");
        bookService.showBookList();
        outputManager.printSystem("도서 목록 끝");
    }

    protected void saveBook() throws Exception {
        outputManager.printSystem("도서 등록 메뉴로 넘어갑니다.");
        outputManager.printQuestion("등록할 도서 제목을 입력하세요.");
        String title = inputManager.getInput();

        outputManager.printQuestion("작가 이름을 입력하세요.");
        String author = inputManager.getInput();

        outputManager.printQuestion("페이지 수를 입력하세요.");
        Integer page = Integer.parseInt(inputManager.getInput());

        bookService.saveBook(title, author, page);

        outputManager.printSystem("도서 등록이 완료되었습니다.");
    }

    protected void deleteBook() throws Exception {
        outputManager.printSystem("도서 삭제 처리 메뉴로 넘어갑니다.");
        outputManager.printQuestion("삭제 처리할 도서번호를 입력하세요.");
        Integer id = Integer.valueOf(inputManager.getInput());
        bookService.removeBook(id);
        outputManager.printSystem("도서가 삭제 처리 되었습니다.");
    }
}
