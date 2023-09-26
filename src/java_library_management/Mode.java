package java_library_management;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

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
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    default Book register(Map<Integer, Book> map) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = bufferedReader.readLine();

        System.out.println("Q. 작가 이름을 입력하세요.");
        String author = bufferedReader.readLine();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int page_num = Integer.parseInt(bufferedReader.readLine());

        return new Book(getBookId(map), title, author, page_num, BookState.AVAILABLE);
    }

    default void borrow(Map<Integer, Book> map, int book_id) throws FuncFailureException {
        Book elem = findById(map, book_id);
        if (elem != null) {
            if (elem.getState() == BookState.LOAN) {
                throw new FuncFailureException("[System] 이미 대여중인 도서입니다.");
            } else if (elem.getState() == BookState.ARRANGEMENT) {
                throw new FuncFailureException("[System] 정리중인 도서입니다.");
            } else if (elem.getState() == BookState.LOST) {
                throw new FuncFailureException("[System] 분실된 도서입니다.");
            } else {
                elem.setState(BookState.LOAN);
                System.out.println("[System] 도서가 대여 처리 되었습니다.\n");
            }
        } else {
            throw new FuncFailureException("[System] 해당 도서가 존재하지 않습니다.");
        }
    }

    default void lost(Map<Integer, Book> map, int book_id) {
        Book elem = findById(map, book_id);
        if (elem != null) {
            if (elem.getState() == BookState.LOST) {
                throw new FuncFailureException("[System] 이미 분실 처리된 도서입니다.");
            } else {
                elem.setState(BookState.LOST);
                System.out.println("[System] 도서가 분실 처리 되었습니다.\n");
            }
        } else {
            throw new FuncFailureException("[System] 해당 도서가 존재하지 않습니다.");
        }
    }

    default void delete(Map<Integer, Book> map, int book_id) {
        Book elem = findById(map, book_id);
        if (elem != null) {
            map.remove(elem.getBook_id());
            System.out.println("[System] 도서가 삭제 처리 되었습니다.\n");
        } else {
            throw new FuncFailureException("[System] 존재하지 않는 도서번호 입니다.");
        }
    }

    default void returns(Map<Integer, Book> map, int book_id, String filePath, long delay, BiConsumer<Map<Integer, Book>, String> biConsumer) throws FuncFailureException {
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
                throw new FuncFailureException("[System] 원래 대여가 가능한 도서입니다.");
            } else if (elem.getState() == BookState.ARRANGEMENT) {
                throw new FuncFailureException("[System] 대여되지 않은 도서로 반납이 불가합니다.");
            }
        } else {
            throw new FuncFailureException("[System] 해당 도서가 존재하지 않습니다.");
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

    default void printMenu() {

        System.out.println("0. 사용할 기능을 선택해주세요.");
        System.out.println("1. 도서 등록");
        System.out.println("2. 전체 도서 목록 조회");
        System.out.println("3. 제목으로 도서 검색");
        System.out.println("4. 도서 대여");
        System.out.println("5. 도서 반납");
        System.out.println("6. 도서 분실");
        System.out.println("7. 도서 삭제");
        System.out.println("8. 시스템 종료\n");
    }

    default void get(Book obj) {

        System.out.println("도서번호 : " + obj.getBook_id());
        System.out.println("제목 : " + obj.getTitle());
        System.out.println("작가 이름 : " + obj.getAuthor());
        System.out.println("페이지 수 : " + obj.getPage_num() + " 페이지");
        System.out.println("상태 : " + obj.getState().getState());
    }

    default void getAll(Map<Integer, Book> map) {
        Iterator<Book> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Book obj = iterator.next();
            get(obj);
            if (iterator.hasNext()) System.out.println("\n------------------------------\n");
        }
        System.out.println("[System] 도서 목록 끝");
    }

    default void findByTitle(Map<Integer, Book> map, String title) {

        Iterator<Book> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Book elem = iterator.next();
            if (elem.getTitle().contains(title)) {
                get(elem);
                System.out.println("\n------------------------------\n");
            }
        }
        System.out.println("[System] 검색된 도서 끝\n");
    }

    default void load(Map<Integer, Book> map, String filePath) {

        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader(filePath);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("book");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);

                int book_id = Integer.parseInt(String.valueOf(object.get("book_id")));
                String title = String.valueOf(object.get("title"));
                String author = String.valueOf(object.get("author"));
                int page_num = Integer.parseInt(String.valueOf(object.get("page_num")));
                BookState state = BookState.valueOfState(String.valueOf(object.get("state")));
                map.put(book_id, new Book(book_id, title, author, page_num, state));
            }

            reader.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    default void update(Map<Integer, Book> map, String filePath) {

        Gson gson = new Gson().newBuilder().setPrettyPrinting()
                .registerTypeAdapter(Book.class, new BookSerializer())
                .create();

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write("{\n");
            writer.write(" \"book\" : [\n");

            Iterator<Book> iterator = map.values().iterator();
            while (iterator.hasNext()) {
                Book elem = iterator.next();
                String json = gson.toJson(elem); // Book 객체를 JSON 문자열로 변환
                writer.write(json);
                if (iterator.hasNext()) writer.write(",");
                writer.write("\n");
            }
            writer.write("]\n");
            writer.write("}");
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void printStartMsg();
}
