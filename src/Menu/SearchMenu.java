package Menu;

import java.io.IOException;

public class SearchMenu implements Menu {

    public void run() throws IOException {
        bw.write("Q. 검색할 도서 제목 일부를 입력하세요.");
        String titleWord = bf.readLine();
        repository.search(titleWord);
        bw.write("[System] 검색된 도서 끝");
    }
}
