package org.library.entity;

import org.library.error.*;

import java.time.LocalDateTime;

public class Book {

    private Long id;
    private String title;
    private String author;
    private int page;
    private State state = State.AVAILABLE;
    private LocalDateTime organizingTime;

    public Book(Long id, String title, String author, int page) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.page = page;
    }

    public String rent(){
        if(state.equals(State.RENT)){
            throw new AlreadyRentError();
        }
        if(state.equals(State.LOST)){
            throw new AlreadyLostError();
        }
        if(state.equals(State.ORGANIZING)){
            throw new AlreadyOrganizingError();
        }
        state = State.RENT;
        return Message.SUCCESS_RENT.getMessage();
    }

    public String returns(){
        if(!state.equals(State.RENT) && !state.equals(State.LOST)){
            throw new NotReturnsError();
        }
        state = State.ORGANIZING;
        organizingTime = LocalDateTime.now().plusMinutes(5);
        return Message.SUCCESS_RETURNS.getMessage();
    }

    public String reportLost(){
        if(state.equals(State.LOST)){
            throw new AlreadyLostError();
        }
        this.state = State.LOST;
        return Message.SUCCESS_REPORT_LOST.getMessage();
    }

    public void processAvailable(){
        if(organizingTime != null && organizingTime.isBefore(LocalDateTime.now())){
            state = State.AVAILABLE;
            organizingTime = null;
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

    public String getTitle(){
        return title;
    }

    public Long getId(){
        return id;
    }
}
