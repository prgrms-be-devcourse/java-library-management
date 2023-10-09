package devcourse.backend.model;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

public enum BookStatus {
    AVAILABLE("대여 가능"),
    BORROWED("대여 중"),
    ARRANGING("도서 정리 중"),
    LOST("분실됨");

    private String description;

    BookStatus(String description) {
        this.description = description;
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
}
