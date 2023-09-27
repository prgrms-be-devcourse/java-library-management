package devcourse.backend.view;

import java.util.Arrays;
import java.util.Optional;

public enum Menu {
    REGISTER(1, "도서 등록", Console::registerMenu),
    LOOKUP(2, "전체 도서 목록 조회", Console::allBookMenu),
    SEARCH(3, "제목으로 도서 검색", Console::searchMenu),
    RENT(4, "도서 대여", Console::rentMenu),
    RETURN(5, "도서 반납", Console::returnMenu),
    REPORT(6, "도서 분실", Console::reportMenu);


    private final String description;
    private final int num;

    /**
     *  Q. viewer가 직관적인 이름인가?
     */
    private final Runnable viewer;

    Menu(int num, String description, Runnable viewer) {
        this.num = num;
        this.description = description;
        this.viewer = viewer;
    }

    @Override
    public String toString() {
        return num + ". " + description;
    }

    public static void selected(int num) {
        get(num).orElseThrow().viewer.run();
    }

    private static Optional<Menu> get(int num) {
        return Arrays.stream(Menu.values())
                .filter(e -> e.num == num)
                .findAny();
    }
}
