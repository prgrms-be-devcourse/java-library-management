package entity;

import java.lang.reflect.Field;
import java.util.Arrays;

public class Book {
    static int numberCnt = 1;
    static String[] fieldKoreanName = {"도서번호", "제목", "작가", "페이지 수", "상태"};
    int number;
    String title;
    String author;
    int pageNum;
    State state;

    private Book(String title, String author, int pageNum) {
        this.number = numberCnt++;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.state = State.AVAILABLE;
    }

    public static Book createBook(String title, String author, int pageNum){
        return new Book(title, author, pageNum);
    }

    public static Book createBook(String[] bookInfo){
        if (bookInfo.length != 3)
            throw new IllegalArgumentException("입력 개수가 3개가 아닙니다.");
        return createBook(bookInfo[0], bookInfo[1], Integer.parseInt(bookInfo[2]));
    }

    public String printInfo(){
        Field[] fields = this.getClass().getFields();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fieldKoreanName[i]);
            sb.append(" ");
            sb.append(fields[i].getName());
            sb.append("\n");
        }

        return sb.toString();
    }
}
