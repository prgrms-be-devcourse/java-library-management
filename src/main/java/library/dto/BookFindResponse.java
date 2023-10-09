package library.dto;

import library.domain.Book;

public class BookFindResponse {

    long bookNumber;
    String title;
    String author;
    int pageCount;
    String statusDescription;

    public BookFindResponse(Book book) {
        this.bookNumber = book.getBookNumber();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.pageCount = book.getPageCount();
        this.statusDescription = book.getStatus().getDescription();
    }

    @Override
    public String toString() {
        return "도서번호 : " + bookNumber + System.lineSeparator() +
                "제목 : " + title + System.lineSeparator() +
                "저자 : " + author + System.lineSeparator() +
                "페이지 수 : " + pageCount + " 페이지" + System.lineSeparator() +
                "상태 : " + statusDescription;
    }
}
