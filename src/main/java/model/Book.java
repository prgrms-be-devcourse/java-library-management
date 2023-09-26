package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {

    private Long bookNo;
    private String title;
    private String author;
    private int pageNum;
    private Status status;

    @Override
    public String toString() {
        return "\n도서 번호 : " + bookNo +
                "\n제목 : " + title +
                "\n작가 이름 : " + author +
                "\n페이지 수 : " + pageNum +
                "\n상태 : " + status.getStatus() +
                "\n------------------------------";
    }

    public void changeStatus(Status status) {
        this.status = status;
    }
}
