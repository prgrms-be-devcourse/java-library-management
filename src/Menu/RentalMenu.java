package Menu;

import Menu.Menu;

import java.io.IOException;

public class RentalMenu implements Menu {
    public void run() throws IOException {
        bw.write("Q. 대여할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.rental(id);
        bw.write("[System] 도서가 대여 처리 되었습니다.");
    }
}
