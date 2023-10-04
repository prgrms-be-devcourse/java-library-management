package org.library.controller;

import org.library.entity.Book;
import org.library.entity.Func;
import org.library.entity.Message;
import org.library.service.BookService;
import org.library.utils.ConsoleManager;
import org.library.vo.BookVo;

import java.util.List;


public class Controller {

    private BookService bookService;
    private ConsoleManager consoleManager;

    public Controller(BookService bookService, ConsoleManager consoleManager) {
        this.bookService = bookService;
        this.consoleManager = consoleManager;
    }

    // 1. 도서 등록
    public void register(){
        BookVo bookVo = consoleManager.register();
        Long id = bookService.generateId();
        bookService.save(new Book(id, bookVo.getTitle(), bookVo.getAuthor(), bookVo.getPage()));
        System.out.println(Message.SUCCESS_REGISTER.getMessage());
    }

    // 2. 전체 목록 보여주기
    public void showAll(){
        List<Book> books = bookService.findAll();
        consoleManager.showAll(books);
    }

    // 3. 도서 제목 기준 도서 찾기
    public void findBookByTitle(){
        String title = consoleManager.inputTitle();
        List<Book> books = bookService.findByTitle(title);
        consoleManager.printBookList(books);
        System.out.println(Message.END_FIND_BY_TITLE.getMessage());
    }

    // 4. 도서 대여
    public void rent(){
        Long id = consoleManager.rent();
        String result = bookService.rent(id);
        consoleManager.printResult(result);
    }

    // 5. 도서 반납
    public void returns(){
        Long id = consoleManager.returns();
        String result = bookService.returns(id);
        consoleManager.printResult(result);
    }

    // 6. 도서 분실
    public void reportLost(){
        Long id = consoleManager.reportLost();
        String result = bookService.reportLost(id);
        consoleManager.printResult(result);
    }

    // 7. 도서 삭제
    public void delete(){
        Long id = consoleManager.delete();
        String result = bookService.delete(id);
        consoleManager.printResult(result);
    }

    // 8. 프로그램 종료
    public void exit() {
        System.out.println(Message.QUIT.getMessage());
        bookService.flush();
        System.exit(0);
    }

    public void run(){
        int functionNum = consoleManager.inputFunctionNum();
        Func func = Func.of(functionNum);
        bookService.processAvailable();
        func.call(this);
    }
}
