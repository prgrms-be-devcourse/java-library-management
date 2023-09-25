package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {

    private Long bookNo;
    private String title;
    private String author;
    private int pageNum;
    private Status status;
}
