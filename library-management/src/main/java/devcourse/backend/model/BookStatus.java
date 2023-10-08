package devcourse.backend.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public enum BookStatus {
    AVAILABLE("대여 가능", (after) -> after == "BORROWED" || after == "LOST"),
    BORROWED("대여 중", (after) -> after == "ARRANGING" || after == "LOST"),
    ARRANGING("도서 정리 중", (after) -> after == "AVAILABLE" || after == "LOST"),
    LOST("분실됨", (after) -> after == "ARRANGING");

    private String description;
    private Predicate<String> canSwitchTo;

    BookStatus(String description, Predicate<String> canSwitchTo) {
        this.description = description;
        this.canSwitchTo = canSwitchTo;
    }

    @Override
    public String toString() {
        return description;
    }

    public static BookStatus getByDescription(String description) {
        return Arrays.stream(BookStatus.values())
                .filter(e -> e.description.equals(description))
                .findAny().orElseThrow(() -> new IllegalArgumentException(description + "은 존재하지 않은 도서 상태입니다."));
    }

    public boolean canSwitch(BookStatus after) {
        return this.canSwitchTo.test(after.name());
    }
}
