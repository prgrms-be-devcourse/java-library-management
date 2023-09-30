package org.example.server.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// 실제 저장되는 book 데이터 정보
public class Book {
    public static Calendar calendar = Calendar.getInstance();
    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만
    public String state;
    public String endLoadTime;

    public Book(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.state = BookState.CAN_BORROW.name();
        this.endLoadTime = ""; // Q. 날짜는 보통은 DB에 저장할 때 어떤 형식으로 저장하는지 궁금합니다!
    } // 서비스 레이어에서 새로 생성 할 때. id는 레포지토리 저장시 생성.

    public Book(int id, String name, String author, int pages, String state, String borrowTime) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.state = state;
        this.endLoadTime = borrowTime;
    }// 레포지토리 레이어에서 파일에서 로드 할 때

    @Override
    public String toString() {
        return "\n도서번호 : " + id + "\n" +
                "제목 : " + name + "\n"
                + "작가 이름 : " + author + "\n"
                + "페이지 수 : " + pages + " 페이지\n" +
                "상태 : " + BookState.valueOf(state).getStatus() + "\n" +
                "도서 정리 완료 예정 시간 : " + endLoadTime + "\n" + // 테스트용
                "\n------------------------------\n";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEndLoadTime() {
        return endLoadTime;
    }

    public void setEndLoadTime(String endLoadTime) {
        this.endLoadTime = endLoadTime;
    }
}
