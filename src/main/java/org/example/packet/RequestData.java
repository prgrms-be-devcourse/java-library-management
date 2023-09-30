package org.example.packet;

// Optional 처리
public class RequestData {
    public int id; // 5000 미만
    public String name; // 100자 미만
    public String author; // 100자 미만
    public int pages; // 5000 미만

    public RequestData() {
    } // 전체 조회

    public RequestData(int id) {
        this.id = id;
    } // 그 외

    public RequestData(String name) {
        this.name = name;
    } // 이름 검색

    public RequestData(String name, String author, int pages) {
        this.name = name;
        this.author = author;
        this.pages = pages;
    } // 책 등록용 생성자
}
