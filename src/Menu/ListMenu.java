package Menu;

import Menu.Menu;

import java.io.IOException;

public class ListMenu implements Menu {
    public void run() throws IOException {
        bw.write("[System] 전체 도서 목록입니다.");
        repository.printList();
        bw.write("[System] 도서 목록 끝");
    }
}
