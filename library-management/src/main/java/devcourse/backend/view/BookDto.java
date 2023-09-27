package devcourse.backend.view;

public class BookDto {
    private String title;
    private String author;
    private int totalPages;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title.equals("")) throw new IllegalArgumentException("제목은 빈칸일 수 없습니다.");
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        if(author.equals("")) throw new IllegalArgumentException("작가 이름은 빈칸일 수 없습니다.");
        this.author = author;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        if (totalPages <= 0) throw new IllegalArgumentException("페이지 수는 0이상 입니다.");
        this.totalPages = totalPages;
    }
}
