package devcourse.backend.view;

public class BookDto {
    private String title;
    private String author;
    private int totalPages;

    public BookDto(String title, String author, int totalPages) {
        if (title.equals("")) throw new IllegalArgumentException("제목은 빈칸일 수 없습니다.");
        if (author.equals("")) throw new IllegalArgumentException("작가 이름은 빈칸일 수 없습니다.");
        if (totalPages <= 0) throw new IllegalArgumentException("페이지 수는 0보다 커야 합니다.");

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
