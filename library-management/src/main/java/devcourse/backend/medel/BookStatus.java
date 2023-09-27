package devcourse.backend.medel;

import java.util.Arrays;
import java.util.Optional;

public enum BookStatus {
    AVAILABLE("대여 가능"), BORROWED("대여 중"), ARRANGING("도서 정리 중"), LOST("분실됨");

    private final String description;

    BookStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() { return description; }

    public static Optional<BookStatus> get(String description) {
        return Arrays.stream(BookStatus.values())
                .filter(e -> e.description.equals(description))
                .findAny();
    }

    public static final boolean canSwitch(BookStatus before, BookStatus after) {
        if (after == BORROWED) {
            if (before == BookStatus.AVAILABLE) return true;
        }

        if(after == ARRANGING) {
            if (before == BORROWED || before == LOST) return true;
        }

        if(after == AVAILABLE) {
            if(before == ARRANGING) return true;
        }

        if(after == LOST) {
            return (before == LOST)? false : true;
        }

        return false;
    }
}
