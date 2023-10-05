package domain;

import java.util.List;

public class Book {
    private static final int PROCESSING_TIME_MILLIS = 5 * 60 * 1_000;
    private static final int NOT_REVERTED = -1;

    private static int numberCnt = 1;
    private final int number;
    private final String title;
    private final String author;
    private final int pageNum;
    private BookState bookState;
    private long lastReturn;

    public Book(String title, String author, int pageNum) {
        this.number = numberCnt++;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.bookState = BookState.AVAILABLE;
        this.lastReturn = NOT_REVERTED;
    }

    public Book(int number, String title, String author, int pageNum, BookState bookState, long lastReturn) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.bookState = bookState;
        this.lastReturn = lastReturn;
        numberCnt = Math.max(numberCnt, number) + 1;
    }

    public void setBookState(BookState bookState) {
        this.bookState = bookState;
    }

    public void setLastReturn(long lastReturn) {
        this.lastReturn = lastReturn;
    }

    public int getNumber() {
        return number;
    }

    public BookState getBookState() {
        return bookState;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getPageNum() {
        return pageNum;
    }

    public long getLastReturn() {
        return lastReturn;
    }

    public boolean isDeleted(){
        return this.bookState.equals(BookState.DELETED);
    }

    public boolean hasText(String text){
        return this.title.contains(text) && !this.isDeleted();
    }

    public void changeStateAfter5Minutes(){
        if(this.bookState == BookState.PROCESSING && System.currentTimeMillis() - this.lastReturn >= PROCESSING_TIME_MILLIS)
            this.bookState = BookState.AVAILABLE;
    }

    @Override
    public String toString(){
        String[] fields = {
                String.valueOf(this.number),
                this.title, this.author,
                String.valueOf(this.pageNum),
                this.bookState.getDisplayName()
        };
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            sb.append(FIELD_DISPLAY_NAMES.get(i));
            sb.append(": ");
            sb.append(fields[i]);
            sb.append(System.lineSeparator());
        }

        return sb.toString();
    }
}
