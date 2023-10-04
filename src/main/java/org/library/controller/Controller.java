package org.library.controller;

import org.library.entity.Book;
import org.library.entity.Func;
import org.library.entity.Message;
import org.library.service.BookService;
import org.library.utils.ConsoleInputManager;
import org.library.vo.BookVo;

import java.util.List;


public class Controller {

    private BookService bookService;
    private ConsoleInputManager consoleInputManager;

    public Controller(BookService bookService, ConsoleInputManager consoleInputManager) {
        this.bookService = bookService;
        this.consoleInputManager = consoleInputManager;
    }

    // 1. 도서 등록
    public void register(){
        BookVo bookVo = consoleInputManager.register();
        Long id = bookService.generateId();
        bookService.save(new Book(id, bookVo.getTitle(), bookVo.getAuthor(), bookVo.getPage()));
        System.out.println(Message.SUCCESS_REGISTER.getMessage());
    }

    // 2. 전체 목록 보여주기
    public void showAll(){
        List<Book> books = bookService.findAll();
        consoleInputManager.showAll(books);
    }

    // 3. 도서 제목 기준 도서 찾기
    public void findBookByTitle(){
        String title = consoleInputManager.inputTitle();
        List<Book> books = bookService.findByTitle(title);
        consoleInputManager.printBookList(books);
        System.out.println(Message.END_FIND_BY_TITLE.getMessage());
    }

    // 4. 도서 대여
    public void rent(){
        Long id = consoleInputManager.rent();
        bookService.rent(id);
    }

    // 5. 도서 반납
    public void returns(){
        Long id = consoleInputManager.returns();
        bookService.returns(id);
    }

    // 6. 도서 분실
    public void reportLost(){
        Long id = consoleInputManager.reportLost();
        bookService.reportLost(id);
    }

    // 7. 도서 삭제
    public void delete(){
        Long id = consoleInputManager.delete();
        bookService.delete(id);
    }

    // 8. 프로그램 종료
    public void exit() {
        System.out.println(Message.QUIT.getMessage());
        bookService.saveFile();
        System.exit(0);
    }

    public void run(){
        int functionNum = consoleInputManager.inputFunctionNum();
        Func func = Func.of(functionNum);
        func.call(this);
    }
}
