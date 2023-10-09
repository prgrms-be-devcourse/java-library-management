package controller;

import controller.menu.MenuType;
import service.BookService;

public class BookController {
    private final BookService bookService;
    private final MenuType menuType;


    public BookController(MenuType menuType, BookService bookService) {
        this.menuType = menuType;
        this.bookService = bookService;
    }

    public void saveBook() throws Exception {
        menuType.printEntryMsg();

        String title = MenuType.getStrInput("등록할 도서 제목을 입력하세요.");
        String author = MenuType.getStrInput("작가 이름을 입력하세요.");
        Integer page = MenuType.getIdInput("페이지 수를 입력하세요.");

        bookService.saveBook(title, author, page);

        menuType.printEndMsg();
    }

    public void showBookList() {
        menuType.printEntryMsg();
        bookService.showBookList();
        menuType.printEndMsg();
    }

    public void searchBook() throws Exception {
        menuType.printEntryMsg();
        bookService.findBookByTitle(MenuType.getStrInput("검색할 도서 제목 일부를 입력하세요."));
        menuType.printEndMsg();
    }

    public void borrowBook() throws Exception {
        menuType.printEntryMsg();
        bookService.borrowBook(MenuType.getIdInput("대여할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }

    public void returnBook() throws Exception {
        menuType.printEntryMsg();
        bookService.returnBook(MenuType.getIdInput("반납할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }

    public void reportLostBook() throws Exception {
        menuType.printEntryMsg();
        bookService.reportLostBook(MenuType.getIdInput("분실 처리할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }

    public void deleteBook() throws Exception {
        menuType.printEntryMsg();
        bookService.removeBook(MenuType.getIdInput("삭제 처리할 도서번호를 입력하세요."));
        menuType.printEndMsg();
    }
}
