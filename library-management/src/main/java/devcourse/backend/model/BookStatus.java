package devcourse.backend.model;

import java.util.Arrays;
import java.util.Optional;

public enum BookStatus {
    AVAILABLE("대여 가능"), BORROWED("대여 중"), ARRANGING("도서 정리 중"), LOST("분실됨");

    private final String description;

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

    public final boolean canSwitch(BookStatus after) {
        if (after == BORROWED) {
            if (this == BookStatus.AVAILABLE) return true;
        }

        if (after == ARRANGING) {
            if (this == BORROWED || this == LOST) return true;
        }

        if (after == AVAILABLE) {
            if (this == ARRANGING) return true;
        }

        if (after == LOST) {
            return (this == LOST) ? false : true;
        }

        return false;
    }
}
