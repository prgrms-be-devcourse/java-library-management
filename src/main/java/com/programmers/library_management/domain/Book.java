package com.programmers.library_management.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Book {
    private final int id;
    private final String title;
    private final String writer;
    private final int pageNumber;
    private StatusType statusType;
    private LocalDateTime returnDateTime;

    public static Book newBookOf(int id, String title, String writer, int pageNumber){
        return new Book(id, title, writer, pageNumber, StatusType.Available, "null");
    }

    public static Book loadBookOf(int id, String title, String writer, int pageNumber, StatusType statusType, String returnDateTime){
        return new Book(id, title, writer, pageNumber, statusType, returnDateTime);
    }

    private Book(int id, String title, String writer, int pageNumber, StatusType statusType, String returnDateTime) {
        this.id = id;
        this.title = title;
        this.writer = writer;
        this.pageNumber = pageNumber;
        this.statusType = statusType;
        this.returnDateTime = returnDateTime.equals("null") ? null : LocalDateTime.parse(returnDateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
    }

    @Override
    public String toString() {
        return "도서번호: " + id + '\n' +
                "제목: " + title + '\n' +
                "작가 이름: " + writer + '\n' +
                "페이지 수: " + pageNumber + " 페이지\n" +
                "상태: " + statusType.getMessage();
    }

    public String toCsvString() {
        return id + "," + title + "," + writer + "," + pageNumber + "," + statusType + "," + returnDateTime + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pageNumber == book.pageNumber && Objects.equals(title, book.title) && Objects.equals(writer, book.writer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, writer, pageNumber);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public StatusType getStatus() {
        return statusType;
    }

    public boolean isOrganized() {
        if (statusType.equals(StatusType.Organized)) {
            int ORGANIZE_TIME = 5;
            return returnDateTime.plusMinutes(ORGANIZE_TIME).isBefore(LocalDateTime.now());
        }
        return false;
    }

    public void rant() {
        this.statusType = StatusType.Ranted;
    }

    public void lost() {
        this.statusType = StatusType.Lost;
    }

    public void available() {
        this.statusType = StatusType.Available;
    }

    public void returned() {
        this.statusType = StatusType.Organized;
        this.returnDateTime = LocalDateTime.now();
    }
}
