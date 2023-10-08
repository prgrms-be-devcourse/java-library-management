package domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer pageNum;
    private BookStatusType bookStatusType;

    public void setId(Long id){
        this.id = id;
    }

    public void toAvailable(){
        this.bookStatusType = BookStatusType.AVAILABLE;
    }
    public void toLost(){
        this.bookStatusType = BookStatusType.LOST;
    }
    public void toBorrowed(){
        this.bookStatusType = BookStatusType.BORROWED;
    }
    public void toOrganizing(){
        this.bookStatusType = BookStatusType.ORGANIZING;
    }
}
// 네이밍 복수형 -> 단수형 - 헷갈린다