package repository;

import Book;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class TestRepository implements Repository {
    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    List<Book> books = new ArrayList<>();

    public void register(Book book) {
        book.setState("대여 가능");
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

    public void search(String titleWord) {
        books.stream().forEach(book -> {
            String title = book.getTitle();
            if(title.contains(titleWord)) {
                try {
                    printBookInfo(book);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void rental(int id) {
        books.stream().forEach(book -> {
            try {
                if (book.getState().equals("대여중")) {
                    bw.write("[System] 이미 대여중인 도서입니다.");
                } else if(book.getState().equals("대여 가능")) {
                    book.setState("대여중");
                    bw.write("[System] 도서가 대여 처리 되었습니다.");
                } else {
                    bw.write("[System] 대여가 불가능한 도서입니다.");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void returnBook() {}
}
