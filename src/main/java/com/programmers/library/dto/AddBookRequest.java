package com.programmers.library.dto;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.StatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
public class AddBookRequest {
    private String title;
    private String author;
    private int pages;

    public Book toEntity() { // 생성자를 사용해 객체 생성
        return Book.builder()
                .title(title)
                .author(author)
                .pages(pages)
                .status(StatusType.AVAILABLE)
                .build();
    }
}
