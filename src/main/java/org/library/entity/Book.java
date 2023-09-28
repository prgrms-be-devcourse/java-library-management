package org.library.entity;

import java.time.LocalDateTime;

public class Book {

    private Long id;
    private String title;
    private String author;
    private int page;
    private State state = State.AVAILABLE;
    private LocalDateTime organizingTime;

    private static final String successRentString = "도서가 대여 처리 되었습니다.";
    private static final String successLostString = "도서가 분실 처리 되었습니다.";
    private static final String failLostString = "이미 분실 처리된 도서입니다.";
    private static final String successReturnString = "도서가 반납 처리 되었습니다";

    public Book(Long id, String title, String author, int page) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
    }

    public String rent(){
        if(!state.equals(State.AVAILABLE)){
            return getReason();
        }
        state = State.RENT;
        return successRentString;
    }

    public String returns(){
        if(!state.equals(State.RENT) && !state.equals(State.LOST)){
            return getReason();
        }
        state = State.ORGANIZING;
        organizingTime = LocalDateTime.now().plusMinutes(5);
        return successReturnString;
    }

    public String reportLost(){
        if(state.equals(State.LOST)){
            return failLostString;
        }
        this.state = State.LOST;
        return successLostString;
    }

    public void processAvailable(){
        if(organizingTime != null && organizingTime.isBefore(LocalDateTime.now())){
            state = State.AVAILABLE;
        }
    }

    @Override
    public String toString() {
        return "도서번호 : " + id +
                "\n제목 : " + title +
                "\n작가 이름 : " + author +
                "\n페이지 수 : " + page + " 페이지"+
                "\n상태 : " + state.getDescription() +
                "\n------------------------------";
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
