package app.library.management.infra.port.dto.response;

import app.library.management.core.domain.BookStatus;

public class BookResponseDto {

    private final long id;
    private final String title;
    private final String author;
    private final int pages;
    private final BookStatus status;

    public BookResponseDto(long id, String title, String author, int pages, BookStatus status) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.status = status;
    }

    @Override
    public String toString() {
        return "도서번호 : " + id + '\n' +
                "제목 : " + title + '\n' +
                "작가 이름 : " + author + '\n' +
                "페이지 수 : " + pages + '\n' +
                "상태 : " + status.getTitle() + "\n\n";
    }
}
