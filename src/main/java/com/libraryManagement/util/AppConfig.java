package com.libraryManagement.util;

import com.libraryManagement.controller.BookController;
import com.libraryManagement.io.BookIO;
import com.libraryManagement.io.BookMenu;
import com.libraryManagement.io.ModeMenu;
import com.libraryManagement.repository.FileRepository;
import com.libraryManagement.repository.MemoryRepository;
import com.libraryManagement.repository.Repository;
import com.libraryManagement.service.BookService;

import java.io.IOException;

import static java.lang.System.exit;

// 설정만 할수있도록 고려
public class AppConfig {

    public void init() throws IOException, InterruptedException {

        ModeMenu modeMenu = new ModeMenu();
        modeMenu.displayModeMenu();

        //==의존성 주입==//
        Repository repository = null;
        if(modeMenu.getSelectMode().equals("일반 모드")){
            repository = new FileRepository();
        }else if(modeMenu.getSelectMode().equals("테스트 모드")) {
            repository = new MemoryRepository();
        }

        if(repository == null){
            System.out.println("[System] 모드 선택에 실패했습니다. 프로그램을 종료합니다.");
            exit(0);
        }

        BookService bookService = new BookService(repository);
        BookIO bookIO = new BookIO();
        BookMenu bookMenu = new BookMenu();

        BookController bookController = new BookController(bookService, bookIO, bookMenu);

        bookController.startBookMenu();
    }
}
