import repository.Book;
import repository.Repository;

import java.io.*;

public class Menu {
    public static Repository repository = null;
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    Menu(Repository repository) {
        this.repository = repository;
    }

    public void register() throws IOException {
        Book book = new Book();
        bw.write("Q. 등록할 도서 제목을 입력하세요.");
        book.setTitle(bf.readLine());

        bw.write("Q. 작가 이름을 입력하세요.");
        book.setWriter(bf.readLine());

        bw.write("Q. 페이지 수를 입력하세요.");
        book.setPage(Integer.parseInt(bf.readLine()));

        repository.register(book);
        bw.write("[System] 도서 등록이 완료되었습니다.");
    }

    public void list() throws IOException {
        bw.write("[System] 전체 도서 목록입니다.");
        repository.printList();
        bw.write("[System] 도서 목록 끝");
    }

    public void search() throws IOException {
        bw.write("Q. 검색할 도서 제목 일부를 입력하세요.");
        String titleWord = bf.readLine();
        repository.search(titleWord);
        bw.write("[System] 검색된 도서 끝");
    }

    public void rental() throws IOException {
        bw.write("Q. 대여할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.rental(id);
    }

    public void returnBook() throws IOException {
        bw.write("Q. 반납할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.returnBook(id);
    }

    public void lostBook() throws IOException {
        bw.write("Q. 분실 처리할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.lostBook(id);
    }

    public void deleteBook() throws IOException {
        bw.write("Q. 삭제 처리할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.deleteBook(id);
    }

}
