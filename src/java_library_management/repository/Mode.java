package java_library_management.repository;

import com.google.gson.Gson;
import java_library_management.config.CallbackConfig;
import java_library_management.domain.Book;
import java_library_management.domain.BookState;
import java_library_management.exception.FuncFailureException;
import java_library_management.manager.ConsoleManager;
import java_library_management.manager.JSONFileManager;
import java_library_management.util.BookSerializer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface Mode {

    default int getBookId(Map<Integer, Book> map) {
        int book_id = 1;

        Iterator<Book> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Book elem = iterator.next();
            if (elem.getBook_id() == book_id) book_id++;
        }

        return book_id;
    }

    default Book findById(Map<Integer, Book> map, int book_id) {
        return map.get(book_id);
    }

    default void add(Map<Integer, Book> map, Book obj) {
        map.put(obj.getBook_id(), obj);
    }

    default Book register(Map<Integer, Book> map, String title, String author, int page_num) {
        return new Book(getBookId(map), title, author, page_num, BookState.AVAILABLE);
    }

    default void borrow(Map<Integer, Book> map, int book_id) {
        Book elem = findById(map, book_id);
        if (elem != null) {
            if (elem.getState() == BookState.LOAN) {
                throw new FuncFailureException("[System] 이미 대여중인 도서입니다.\n");
            } else if (elem.getState() == BookState.ARRANGEMENT) {
                throw new FuncFailureException("[System] 정리중인 도서입니다.\n");
            } else if (elem.getState() == BookState.LOST) {
                throw new FuncFailureException("[System] 분실된 도서입니다.");
            } else {
                elem.setState(BookState.LOAN);
                System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
            }
        } else {
            throw new FuncFailureException("[System] 해당 도서가 존재하지 않습니다.\n");
        }
    }

    default void lost(Map<Integer, Book> map, int book_id) {
        Book elem = findById(map, book_id);
        if (elem != null) {
            if (elem.getState() == BookState.LOST) {
                throw new FuncFailureException("[System] 이미 분실 처리된 도서입니다.\n");
            } else {
                elem.setState(BookState.LOST);
                System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
            }
        } else {
            throw new FuncFailureException("[System] 해당 도서가 존재하지 않습니다.\n");
        }
    }

    default void delete(Map<Integer, Book> map, int book_id) {
        Book elem = findById(map, book_id);
        if (elem != null) {
            map.remove(elem.getBook_id());
            System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
        } else {
            throw new FuncFailureException("[System] 존재하지 않는 도서번호 입니다.\n");
        }
    }

    default void returns(Map<Integer, Book> map, int book_id, String filePath, long delay, BiConsumer<Map<Integer, Book>, String> biConsumer) {
        Book elem = findById(map, book_id);
        if (elem != null) {
            if (elem.getState() == BookState.LOAN || elem.getState() == BookState.LOST) {
                callback(map, elem, filePath, 0, BookState.ARRANGEMENT, biConsumer);
                System.out.println("[System] 도서가 반납 처리 되었습니다.\n");

                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.submit(() -> {
                    callback(map, elem, filePath, delay, BookState.AVAILABLE, biConsumer);
                });
                executorService.shutdown();

            } else if (elem.getState() == BookState.AVAILABLE) {
                throw new FuncFailureException("[System] 원래 대여가 가능한 도서입니다.\n");
            } else if (elem.getState() == BookState.ARRANGEMENT) {
                throw new FuncFailureException("[System] 대여되지 않은 도서로 반납이 불가합니다.\n");
            }
        } else {
            throw new FuncFailureException("[System] 해당 도서가 존재하지 않습니다.\n");
        }
    }

    // 설정된 시간이 지난 뒤, '도서 정리중' -> '대여 가능' 변경
    // update
    default void callback(Map<Integer, Book> map, Book book, String filePath, long delay, BookState state, BiConsumer<Map<Integer, Book>, String> biConsumer) {

        Timer m = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
            book.setState(state);

            if (biConsumer != null) {
                biConsumer.accept(map, filePath);
            }
            }
        };
        m.schedule(task, delay);
    }

    default void get(Book obj) {

        System.out.println("도서번호 : " + obj.getBook_id());
        System.out.println("제목 : " + obj.getTitle());
        System.out.println("작가 이름 : " + obj.getAuthor());
        System.out.println("페이지 수 : " + obj.getPage_num() + " 페이지");
        System.out.println("상태 : " + obj.getState().getState());
        System.out.println("\n------------------------------\n");
    }

    default void getAll(Map<Integer, Book> map) {
        Iterator<Book> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Book obj = iterator.next();
            get(obj);
        }
        System.out.println("[System] 도서 목록 끝\n");
    }

    default void findByTitle(Map<Integer, Book> map, String title) {

        Iterator<Book> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Book elem = iterator.next();
            if (elem.getTitle().contains(title)) {
                get(elem);
            }
        }
        System.out.println("[System] 검색된 도서 끝\n");
    }

    default void load(Map<Integer, Book> map, String filePath) {

        try {
            JSONFileManager fileManager = new JSONFileManager();
            fileManager.readFile(map, filePath, this::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    default void update(Map<Integer, Book> map, String filePath) {

        try {
            JSONFileManager fileManager = new JSONFileManager();
            fileManager.writeFile(map, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printStartMsg();
}
