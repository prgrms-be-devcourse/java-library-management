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

public class AppConfig {

    public void init() throws IOException {

        ModeMenu modeMenu = new ModeMenu();
        modeMenu.displayModeMenu();

        Repository repository = null;

        if(modeMenu.getSelectMode().equals("일반 모드")){
            repository = new FileRepository();
        }else if(modeMenu.getSelectMode().equals("테스트 모드")) {
            repository = new MemoryRepository();
        }

        if(repository == null){
            System.out.println("모드 선택에 실패했습니다. 프로그램을 종료합니다.");
            exit(0);
        }
        GlobalVariables globalVariables = new GlobalVariables(repository);

        BookMenu bookMenu = new BookMenu(new BookController(new BookService(repository), new BookIO()));
        bookMenu.displayBookMenu();
    }
}
