package dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookInfoDTO {
    private Long bookNo;
    private String title;
    private String author;
    private Integer pageNum;
}
// 요청에 따른 Request DTO를 만들어주자 -> 필수값 알기가 어려움
// java 17 이상 dto는 data 클래스
// builder - 생성자 validation