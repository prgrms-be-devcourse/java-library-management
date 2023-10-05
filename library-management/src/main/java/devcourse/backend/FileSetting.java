package devcourse.backend;

public enum FileSetting {
    FILE_PATH(System.getProperty("user.dir") + "/data/"),
    FILE_NAME("도서 목록.csv"),
    TEST_FILE_PATH(System.getProperty("user.dir") + "/data/"),
    TEST_FILE_NAME("테스트 도서 목록.csv");

    private String value;

    FileSetting(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
