package domain;

public enum BookStatusType {
    AVAILABLE("대여가능"), BORROWED("대여중"), ORGANIZING("정리중"), LOST("분실");

    private final String description;

    private BookStatusType(String description) {
        this.description = description;
    }
    public String getDescription(){
        return description;
    }
}
// enum 네이밍 - type
// 정리중 - 스케줄러 사용, 이력 관리 필요시 테이블로 저장
