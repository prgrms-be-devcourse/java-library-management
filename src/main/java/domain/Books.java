package domain;

import lombok.Builder;
import lombok.Getter;

import java.awt.print.Book;

@Getter
@Builder
public class Books {
    private Long bookNo;
    private String title;
    private String author;
    private Integer pageNum;
    private BookStatus bookStatus;

    public void setBookNo(Long bookNo){
        this.bookNo = bookNo;
    }

    public void toAvailable(){
        this.bookStatus = BookStatus.AVAILABLE;
    }
    public void toLost(){
        this.bookStatus = BookStatus.LOST;
    }
    public void toBorrowed(){
        this.bookStatus = BookStatus.BORROWED;
    }
    public void toOrganizing(){
        this.bookStatus = BookStatus.ORGANIZING;
    }
}
