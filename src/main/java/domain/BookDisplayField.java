package domain;

public enum BookDisplayField {
    NUMBER("도서번호"),
    TITLE("제목"),
    AUTHOR("작가"),
    PAGE_NUM("페이지 수"),
    STATE("상태");

    private final String displayValue;

    public String getDisplayValue() {
        return displayValue;
    }

    BookDisplayField(String displayValue) {
        this.displayValue = displayValue;
    }
}
