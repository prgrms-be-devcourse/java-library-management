package domain;

public class BackGround extends Thread{
    private final Book book;
    private final Long mills;

    public BackGround(Book book, Long mills){
        this.book = book;
        this.mills = mills;
    }

    @Override
    public void run() {
        try {
            sleep(mills);
            book.returnBook();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
