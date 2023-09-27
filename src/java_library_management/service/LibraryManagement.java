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

/**
 * 일반 모드와 테스트 모드 구현체를 분리하기 위해 Mode 인터페이스를 도입 -> 자신이 어떤 Mode 구현체를 사용할지 몰라도 됨!
 * 일반 모드의 관리 시스템 로직과, 테스트 모드의 관리 시스템 로직이 공통적인 부분이 매우 많은 점과
 * 일반 모드에서 JSON 파일을 읽어오는 (load) 메서드와 JSON 파일에 업데이트하는 (update) 기능만 차이난다는 점 때문에
 * 시스템을 구동하는 play() 메서드를 공통으로 뽑아내었음.
 * Callback 함수를 인자로 넣어서, 일반 모드에서는 load 함수에 해당하는 loadCallback, update 함수에 해당하는 updateCallback 을 전달하도록 구현
 * 테스트 모드에서는 각 Callback 인자에 null을 전달함으로써 실행하지 않도록 구현
 *
 * 1. 이렇게 Callback 을 인자로 전달하도록 구현한 설계에 대해서, 좋은 객체지향 설계라고 생각하시는지 궁금합니다.
 *    더 좋은 설계 방법이 있지 않을까 고민이 많이 되었습니다.
 */

public class LibraryManagement {

    private Mode mode;
    private ConsoleManager consoleManager;
    private BiConsumer<Map<Integer, Book>, String> loadCallback;
    private BiConsumer<Map<Integer, Book>, String> updateCallback;

    @Builder
    public LibraryManagement(LibraryConfig libraryConfig) {
        this.mode = libraryConfig.getModeConfig().getMode();
        this.loadCallback = libraryConfig.getCallbackConfig().getLoadCallback();
        this.updateCallback = libraryConfig.getCallbackConfig().getUpdateCallback();
        this.consoleManager = libraryConfig.getConsoleManager();
    }

    public void printStartMsg() {
        this.mode.printStartMsg();
    }

    /**
     * 일반 모드에서는 loadCallback 에 mode::load, updateCallback 에 mode::update 가 전달됨
     * 테스트 모드에서는 null, null 이 전달됨
     */

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
        System.out.println("[System] 도서 목록 조회가 완료되었습니다.\n");
    }

    public void findByTitle(Map<Integer, Book> map, String title) {
        this.mode.findByTitle(map, title);
        System.out.println("[System] 도서 검색 조회가 완료되었습니다.\n");
    }

    public void borrow(Map<Integer, Book> map, int book_id) throws FuncFailureException {
        this.mode.borrow(map, book_id);
    }

    public void returns(Map<Integer, Book> map, int book_id, String filePath, long delay, BiConsumer<Map<Integer, Book>, String> biConsumer) throws FuncFailureException {
        this.mode.returns(map, book_id, filePath, delay, biConsumer);
    }

    public void lost(Map<Integer, Book> map, int book_id) throws FuncFailureException {
        this.mode.lost(map, book_id);
        System.out.println("[System] 도서 분실 처리가 완료되었습니다.\n");
    }

    public void delete(Map<Integer, Book> map, int book_id) throws FuncFailureException {
        this.mode.delete(map, book_id);
        System.out.println("[System] 도서 삭제 처리가 완료되었습니다.\n");
    }
}
