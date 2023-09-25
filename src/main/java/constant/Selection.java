package constant;

public enum Selection {

    MODE_CHOOSE("Q. 모드를 선택해주세요."),
    MODE_NORMAL("1. 일반 모드"),
    MODE_TEST("2. 테스트 모드"),
    FUNCTION_CHOOSE("Q. 사용할 기능을 선택해주세요."),
    FUNCTION_REGISTER("1. 도서 등록"),
    FUNCTION_FIND_ALL("2. 전체 도서 목록 조회"),
    FUNCTION_FIND_BY_TITLE("3. 제목으로 도서 검색"),
    FUNCTION_BORROW("4. 도서 대여"),
    FUNCTION_RETURN("5. 도서 반납"),
    FUNCTION_LOST("6. 도서 분실"),
    FUNCTION_DELETE("7. 도서 삭제"),
    ;

    private final String value;

    Selection(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static void printModeOptions() {
        for (Selection selection : Selection.values()) {
            if (selection.name().startsWith("MODE_")) {
                System.out.println(selection.getValue());
            }
        }
    }
    public static void printFunctionOptions() {
        for (Selection selection : Selection.values()) {
            if (selection.name().startsWith("FUNCTION_")) {
                System.out.println(selection.getValue());
            }
        }
    }
}
