package dev.course.repository;

import dev.course.domain.Book;
import dev.course.domain.BookState;
import dev.course.exception.FuncFailureException;
import dev.course.manager.JSONFileManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BiConsumer;

/**
 * 어떤 모드를 사용하는지에 대한 인터페이스
 *
 * 도서 기능에 대해서, 대여나 반납, 분실, 삭제 등이 실패하는 경우에 대해 FuncFailureException 예외를 발생시킴
 * RuntimeException 을 상속한 커스텀 예외 클래스를 만들고, 예외 발생 시 메시지를 출력하도록 함
 * 테스트에서는 도서 기능이 실패하는 경우, 해당 예외가 발생하는지를 assertThatThrownBy 를 통해 검증했음
 *
 * 이 때, 굳이 예외로 발생시킨 이유는 테스트 코드 상에서 좀 더 명시적인 검증을 할 수 있을 것이라고 판단했기 때문
 * 추상화를 적용시켜, 예외 인터페이스를 만들고, 각종 기능의 예외 클래스를 구현하도록 할 수 있었지만, -> 장점: 예외가 어디서 발생했는지 자세히 알 수 있기 때문
 * 테스트 코드 상에서 예외 메시지를 발생시키기만 하는 작은 역할을 하기 때문에 인터페이스를 따로 두지 않고 하나의 예외 클래스를 사용함
 *
 * 궁금한 점
 * 1. returns 메서드에서 비동기 callback 함수와 BiConsumer 함수를 이용한 부분에 대해서 어떻게 생각하시는지 궁금합니다.
 *    객체지향적으로 리팩토링하는 과정에서, 책임에 따른 분리를 최대한 하려고 노력하였으나 완전하게 책임이 분리된 메서드 구현인지 확신은 부족한 것 같습니다.
 */

public interface Mode {

    // 다음에 등록될 도서의 id를 받아옴
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

    // 인자로 들어온 정보를 토대로 Book 객체를 반환
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
                throw new FuncFailureException("[System] 분실된 도서입니다.\n");
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
        } else {
            throw new FuncFailureException("[System] 존재하지 않는 도서번호 입니다.\n");
        }
    }

    /**
     * 도서 반납 메서드
     * 1. 반납이 실패하는 경우, FuncFailureException 예외 발생
     * 2. 반납이 성공하는 경우, 도서가 반납 처리 됨 -> '도서 정리중' 상태로 변함
     * 3. 반납 처리 후, 5분 뒤에 해당 도서는 '대여 가능' 상태로 변함
     * 4. 5분 뒤에 '대여 가능' 상태로 변화하도록 하는 비동기 callback 함수를 구현
     */

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

    /**
     * 비동기 callback 함수
     * 1. delay 시간이 지난 뒤, 해당 도서의 상태를 state 로 변경함 ('도서 정리중'을 '대여 가능'으로 변경)
     * 2. state 가 변경된 후, 변경된 내용을 JSON 파일에 update 해줌 -> BiConsumer 인자를 통해 update 메서드를 호출함
     *    - 일반 모드에서는 update 메서드 호출
     *    - 테스트 모드에서는 null
     */
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
    }

    default void findByTitle(Map<Integer, Book> map, String title) {

        boolean flag = false;
        Iterator<Book> iterator = map.values().iterator();
        while (iterator.hasNext()) {
            Book elem = iterator.next();
            if (elem.getTitle().contains(title)) {
                flag = true;
                get(elem);
            }
        }
        if  (!flag) throw new FuncFailureException("[System] 해당 도서가 존재하지 않습니다.\n");
    }

    // filePath 에 있는 JSON 파일에 있는 내용을 map 에 저장함
    default void load(Map<Integer, Book> map, String filePath) {

        try {
            JSONFileManager fileManager = new JSONFileManager();
            fileManager.readFile(map, filePath, this::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // map 에 저장된 내용을 filePath 에 있는 JSON 파일에 업데이트함
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
