package app.library.management.core.service.response.dto.status;

public enum Stage {
    RENT("대여"),
    RETURN("반납"),
    LOST("분실"),
    DELETE("삭제");

    private final String title;

    Stage(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
