package devcourse.backend.view;

public class BookDto {
    private String title;
    private String author;
    private int totalPages;

    public BookDto(String title, String author, int totalPages) {
        this.title = title;
        this.author = author;
        this.totalPages = totalPages;
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getTotalPages() { return totalPages; }
}
