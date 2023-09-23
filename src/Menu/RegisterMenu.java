package Menu;

import java.io.IOException;

public class RegisterMenu implements Menu {

    Book book = new Book();
    public void run() throws IOException {
        bw.write("Q. 등록할 도서 제목을 입력하세요.");
        book.setTitle(bf.readLine());

        bw.write("Q. 작가 이름을 입력하세요.");
        book.setWriter(bf.readLine());

        bw.write("Q. 페이지 수를 입력하세요.");
        book.setPage(Integer.parseInt(bf.readLine()));

        register(book);
        bw.write("[System] 도서 등록이 완료되었습니다.");
    }

    private void register(Book book) {}
}
