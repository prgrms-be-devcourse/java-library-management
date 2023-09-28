package main.entity;

public class Book {
    private static int numberCnt = 1;
    private static String[] fieldKoreanName = {"도서번호", "제목", "작가", "페이지 수", "상태"};
    private static final int PROCESSING_TIME = 5 * 60 * 1000;
    private final int number;
    private final String title;
    private final String author;
    private final int pageNum;
    private State state;
    private long lastReturn;

    public Book(String title, String author, int pageNum) {
        this.number = numberCnt++;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.state = State.AVAILABLE;
        this.lastReturn = -1;
    }

    public Book(int number, String title, String author, int pageNum, State state, long lastReturn) {
        this.number = number;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.state = state;
        this.lastReturn = lastReturn;
        numberCnt = Math.max(numberCnt, number) + 1;
    }

    public String printInfo(){
        String[] fields = {
                String.valueOf(this.number),
                this.title, this.author,
                String.valueOf(this.pageNum),
                this.state.getKoreanState()
        };
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            sb.append(fieldKoreanName[i]);
            sb.append(": ");
            sb.append(fields[i]);
            sb.append("\n");
        }

        return sb.toString();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setLastReturn(long lastReturn) {
        this.lastReturn = lastReturn;
    }

    public int getNumber() {
        return number;
    }

    public State getState() {
        return state;
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
        return this.state.equals(State.DELETED);
    }

    public boolean hasText(String text){
        return this.title.contains(text) && !this.isDeleted();
    }

    public void isOver5Minutes(){
        if(this.state == State.PROCESSING && System.currentTimeMillis() - this.lastReturn >= PROCESSING_TIME)
            this.state = State.AVAILABLE;
    }
}
