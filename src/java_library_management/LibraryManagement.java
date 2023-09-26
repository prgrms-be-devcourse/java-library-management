package java_library_management;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;

public class LibraryManagement {

    private Mode mode;
    private BiConsumer<Map<Integer, Book>, String> loadCallback;
    private BiConsumer<Map<Integer, Book>, String> updateCallback;

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public void setCallback(BiConsumer<Map<Integer, Book>, String> loadCallback, BiConsumer<Map<Integer, Book>, String> updateCallback) {
        this.loadCallback = loadCallback;
        this.updateCallback = updateCallback;
    }

    public BiConsumer<Map<Integer, Book>, String> getLoadCallback() {
        return this.loadCallback;
    }

    public BiConsumer<Map<Integer, Book>, String> getUpdateCallback() {
        return this.updateCallback;
    }

    public void printStartMsg() {
        this.mode.printStartMsg();
    }

    public void load(Map<Integer, Book> map, String filePath) {
        this.loadCallback.accept(map, filePath);
    }

    public void update(Map<Integer, Book> map, String filePath) {
        this.updateCallback.accept(map, filePath);
    }

    public void play(Map<Integer, Book> map, String filePath, BiConsumer<Map<Integer, Book>, String> loadCallback, BiConsumer<Map<Integer, Book>, String> updateCallback) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        boolean quit = false;
        String title;
        int book_id;

        if (loadCallback != null) loadCallback.accept(map, filePath);

        while (true) {

            mode.printMenu();
            int func = Integer.parseInt(bufferedReader.readLine());

            try {
                switch (func) {
                    case 1:
                        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n");
                        Book obj = mode.register(map);
                        mode.add(map, obj);
                        if (updateCallback != null) updateCallback.accept(map, filePath);
                        break;
                    case 2:
                        System.out.println("[System] 전체 도서 목록입니다.\n");
                        mode.getAll(map);
                        break;
                    case 3:
                        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.\n");
                        title = bufferedReader.readLine();
                        mode.findByTitle(map, title);
                        break;
                    case 4:
                        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.\n");
                        book_id = Integer.parseInt(bufferedReader.readLine());
                        mode.borrow(map, book_id);
                        if (updateCallback != null) updateCallback.accept(map, filePath);
                        break;
                    case 5:
                        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.\n");
                        book_id = Integer.parseInt(bufferedReader.readLine());
                        mode.returns(map, book_id, null, 300000, null);
                        break;
                    case 6:
                        System.out.println("[System] 도서 분실 처리 메뉴로 넘어갑니다.\n");
                        book_id = Integer.parseInt(bufferedReader.readLine());
                        mode.lost(map, book_id);
                        if (updateCallback != null) updateCallback.accept(map, filePath);
                        break;
                    case 7:
                        System.out.println("[System] 도서 삭제 처리 메뉴로 넘어갑니다.\n");
                        book_id = Integer.parseInt(bufferedReader.readLine());
                        mode.delete(map, book_id);
                        if (updateCallback != null) updateCallback.accept(map, filePath);
                        break;
                    case 8:
                        System.out.println("[System] 시스템이 종료되었습니다.\n");
                        quit = true;
                        break;
                }
                if (quit) break;
            } catch (FuncFailureException e) {
                System.out.println(e.getMessage());
            }
            if (quit) break;
        }
    }

    public void add(Map<Integer, Book> map, Book obj) {
        this.mode.add(map, obj);
    }

    public void get(Book obj) {
        this.mode.get(obj);
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
