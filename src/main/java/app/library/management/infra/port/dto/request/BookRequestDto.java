package app.library.management.infra.port.dto.request;

public class BookRequestDto {

    private final String title;
    private final String author;
    private final int pages;

    public BookRequestDto(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPages() {
        return pages;
    }
}
