package controller;

import manager.console.InputManager;
import manager.console.OutputManager;
import service.BookService;

public class BookController {
    private final BookService bookService;
    private final InputManager inputManager;
    private final OutputManager outputManager;
    private MenuType menuType;


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
                function = inputManager.getStringInput();
                menuType = MenuType.findMenuTypeByMenu(function);
                switch (menuType) {
                    case SAVE -> saveBook();
                    case SHOW -> showBookList();
                    case SEARCH -> searchBook();
                    case BORROW -> borrowBook();
                    case RETURN -> returnBook();
                    case REPORT -> reportLostBook();
                    case DELETE -> deleteBook();
                }
            } catch (NumberFormatException e) {
                outputManager.printSystem("숫자를 입력해주세요.");
            } catch (Exception e) {
                outputManager.printSystem(e.getMessage());
            }
        }
    }

    protected void saveBook() throws Exception {
        menuType.printEntryMsg();

        String title = MenuType.getStrInput("등록할 도서 제목을 입력하세요.");
        String author = MenuType.getStrInput("작가 이름을 입력하세요.");
        Integer page = MenuType.getIdInput("페이지 수를 입력하세요.");

        bookService.saveBook(title, author, page);

        menuType.printEndMsg();
    }

    protected void showBookList() {
        menuType.printEntryMsg();
        bookService.showBookList();
        menuType.printEndMsg();
    }

    protected void searchBook() throws Exception {
        menuType.printEntryMsg();
        bookService.findBookByTitle(MenuType.getStrInput("검색할 도서 제목 일부를 입력하세요."));
        menuType.printEndMsg();
    }

    protected void borrowBook() throws Exception {
        menuType.printEntryMsg();
        bookService.borrowBook(MenuType.getIdInput("대여할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }

    protected void returnBook() throws Exception {
        menuType.printEntryMsg();
        bookService.returnBook(MenuType.getIdInput("반납할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }

    protected void reportLostBook() throws Exception{
        menuType.printEntryMsg();
        bookService.reportLostBook(MenuType.getIdInput("분실 처리할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }

    protected void deleteBook() throws Exception {
        menuType.printEntryMsg();
        bookService.removeBook(MenuType.getIdInput("삭제 처리할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }
}
