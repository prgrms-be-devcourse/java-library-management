import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Repository {
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    List<Book> books = new ArrayList<>();

    public void register(Book book) {
        books.add(book);
        //파일에 등록
    }

    public void printList() throws IOException {
        books.stream().forEach(book -> {
            try {
                printBookInfo(book);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void printBookInfo(Book book) throws IOException {
        bw.write("\n도서번호 : " + String.valueOf(book.getId())
                    + "\n제목 : " + book.getTitle()
                    + "\n작가 이름 : " + book.getWriter()
                    + "\n페이지 수: " + String.valueOf(book.getPage()) + "페이지"
                    + "\n상태 : " + book.getState()
                    + "\n\n------------------------------");
    }
}
