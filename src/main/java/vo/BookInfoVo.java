package vo;

import domain.BookStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookInfoVo {
    private Long bookNo;
    private String title;
    private String author;
    private Integer pageNum;
}
