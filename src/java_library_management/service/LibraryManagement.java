package java_library_management.service;

import java_library_management.config.LibraryConfig;
import java_library_management.config.ModeConfig;
import java_library_management.repository.Mode;
import java_library_management.config.CallbackConfig;
import java_library_management.domain.Book;
import java_library_management.exception.FuncFailureException;
import java_library_management.manager.ConsoleManager;
import lombok.Builder;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;

public class LibraryManagement {

    private Mode mode;
    private ModeConfig modeConfig;
    private ConsoleManager consoleManager;
    private BiConsumer<Map<Integer, Book>, String> loadCallback;
    private BiConsumer<Map<Integer, Book>, String> updateCallback;

    @Builder
    public LibraryManagement(LibraryConfig libraryConfig) {
        this.mode = libraryConfig.getModeConfig().getMode();
        this.modeConfig = libraryConfig.getModeConfig();
        this.loadCallback = libraryConfig.getCallbackConfig().getLoadCallback();
        this.updateCallback = libraryConfig.getCallbackConfig().getUpdateCallback();
        this.consoleManager = libraryConfig.getConsoleManager();
    }

    /**
    public LibraryManagement(Mode mode, CallbackConfig callbackConfig, ConsoleManager consoleManager) {
        this.mode = mode;
        this.loadCallback = callbackConfig.getLoadCallback();
        this.updateCallback = callbackConfig.getUpdateCallback();
        this.consoleManager = consoleManager;
    }
    */

    public void printStartMsg() {
        this.mode.printStartMsg();
    }

    public void load(Map<Integer, Book> map, String filePath) {
        if (this.loadCallback != null) {
            this.loadCallback.accept(map, filePath);
            System.out.println("[System] 도서가 자동 등록되었습니다.\n");
        }
    }

    public void update(Map<Integer, Book> map, String filePath) {
        if (this.updateCallback != null) {
            this.updateCallback.accept(map, filePath);
            System.out.println("[System] 파일이 업데이트되었습니다.\n");
        }
    }

    public Book register(Map<Integer, Book> map) throws IOException {

        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = consoleManager.getString();

        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = consoleManager.getString();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int page_num = consoleManager.getInteger();

        return this.mode.register(map, title, author, page_num);
    }

    public void play(Map<Integer, Book> map, String filePath) throws IOException {

        boolean quit = false;
        String title;
        int book_id;

        load(map, filePath);

        while (true) {

            consoleManager.printMenu();
            int func = consoleManager.getInteger();

            try {
                switch (func) {
                    case 1:
                        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                        Book obj = register(map);
                        add(map, obj);
                        update(map, filePath);
                        break;
                    case 2:
                        System.out.println("[System] 전체 도서 목록입니다.\n");
                        getAll(map);
                        break;
                    case 3:
                        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                        title = consoleManager.getString();
                        findByTitle(map, title);
                        break;
                    case 4:
                        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
                        book_id = consoleManager.getInteger();
                        borrow(map, book_id);
                        update(map, filePath);
                        break;
                    case 5:
                        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
                        book_id = consoleManager.getInteger();
                        returns(map, book_id, filePath, 300000, this::update);
                        break;
                    case 6:
                        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
                        book_id = consoleManager.getInteger();
                        lost(map, book_id);
                        update(map, filePath);
                        break;
                    case 7:
                        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
                        book_id = consoleManager.getInteger();
                        delete(map, book_id);
                        update(map, filePath);
                        break;
                    case 8:
                        System.out.println("[System] 시스템이 종료되었습니다.\n");
                        quit = true;
                        break;
                }
                if (quit) break;
            } catch (FuncFailureException | IOException e) {
                System.out.println(e.getMessage());
            }
            if (quit) break;
        }
    }

    public void add(Map<Integer, Book> map, Book obj) {
        this.mode.add(map, obj);
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    public void getAll(Map<Integer, Book> map) {
        this.mode.getAll(map);
    }

    public void findByTitle(Map<Integer, Book> map, String title) {
        this.mode.findByTitle(map, title);
    }

    public void borrow(Map<Integer, Book> map, int book_id) throws FuncFailureException {
        this.mode.borrow(map, book_id);
    }

    public void returns(Map<Integer, Book> map, int book_id, String filePath, long delay, BiConsumer<Map<Integer, Book>, String> biConsumer) throws FuncFailureException {
        this.mode.returns(map, book_id, filePath, delay, biConsumer);
    }

    public void lost(Map<Integer, Book> map, int book_id) throws FuncFailureException {
        this.mode.lost(map, book_id);
    }

    public void delete(Map<Integer, Book> map, int book_id) throws FuncFailureException {
        this.mode.delete(map, book_id);
    }
}
