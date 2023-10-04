package domain;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

import static domain.BookStatus.*;

@Getter
public class Book {
    private final Integer id;
    private final String title;
    private final String author;
    private final Integer page;
    private BookStatus status;
    private LocalDateTime returnTime;

    @Builder
    public Book(Integer id, String title, String author, int page, BookStatus status, LocalDateTime returnTime) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = status;
        this.returnTime = returnTime;
    }

    public Book(Integer id, String title, String author, Integer page) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
        this.status = AVAILABLE;
        this.returnTime = null;
    }


    public void printBookInfo() {
        System.out.println("\n" +"도서번호 : "+ id +"\n"
                +"제목 : " + title + "\n"
                +"작가 이름 : " + author + "\n"
                +"페이지 수 : " + page + "\n"
                +"상태 : " + status.getLabel()+ "\n\n"
                +"-------------------------------------"
        );
    }


    //책 상태 관련 함수
    public void borrow(){
        status = BORROWED;
    }

    public void doReturn() {
        returnTime = LocalDateTime.now();
        status = CLEANING;
    }

    public boolean isStillCleaning(){
        if (Duration.between(returnTime, LocalDateTime.now()).toMinutes()<5) {
            return true;
        }
        status = AVAILABLE;
        return false;
    }

    public void report(){
        status = LOST;
    }
}
