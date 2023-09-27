package entity;

public class Book {
    private static int numberCnt = 1;
    static String[] fieldKoreanName = {"도서번호", "제목", "작가", "페이지 수", "상태"};
    private final int number;
    private final String title;
    private final String author;
    private final int pageNum;
    private State state;
    private long lastReturn;

    private Book(String title, String author, int pageNum) {
        this.number = numberCnt++;
        this.title = title;
        this.author = author;
        this.pageNum = pageNum;
        this.state = State.AVAILABLE;
        this.lastReturn = -1;
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

    public boolean isDeleted(){
        return this.state.equals(State.DELETED);
    }

    public boolean hasText(String text){
        return this.title.contains(text);
    }

    public boolean isOver5Minutes(){
        return System.currentTimeMillis() - this.lastReturn >= 5 * 60 * 1000;
    }
}
