package org.library.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Book {

    private Long id;
    private String title;
    private String author;
    private int page;
    private State state = State.AVAILABLE;
    private LocalDateTime editDateTime;

    private static Long baseId = 0L;
    private static final String successRentString = "도서가 대여 처리 되었습니다.";
    private static final String successLostString = "도서가 분실 처리 되었습니다.";
    private static final String failLostString = "이미 분실 처리된 도서입니다.";

    public Book(String title, String author, int page) {
        this.title = title;
        this.author = author;
        this.page = page;
    }

    public String doRent(){
        if(!state.equals(State.AVAILABLE)){
            return getReason();
        }
        state = State.RENT;
        return successRentString;
    }

    public String doReportLost(){
        if(state.equals(State.LOST)){
            return failLostString;
        }
        this.state = State.LOST;
        return successLostString;
    }

    private String getReason(){
        return state.getDescription();
    }

    public String getTitle(){
        return title;
    }

    public Long getId(){
        return id;
    }
}
