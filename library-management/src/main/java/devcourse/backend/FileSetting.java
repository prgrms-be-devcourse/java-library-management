package devcourse.backend;

public enum FileSetting {
    FILE_PATH("src/main/resources/"),
    FILE_NAME("도서 목록.csv"),
    TEST_FILE_PATH("src/test/resources/"),
    TEST_FILE_NAME("도서 목록.csv");

    private String value;

    FileSetting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
