package repository;

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

    public void rental(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();
        if (selectedBook.getState().equals("대여중")) {
            bw.write("[System] 이미 대여중인 도서입니다.");
        } else if(selectedBook.getState().equals("대여 가능")) {
            selectedBook.setState("대여중");
            bw.write("[System] 도서가 대여 처리 되었습니다.");
        } else if(selectedBook.getState().equals("도서 정리중")){
            bw.write("[System] 정리 중인 도서입니다.");
        } else if(selectedBook.getState().equals("분실됨")) {
            bw.write("[System] 분실된 도서입니다.");
        }
    }

    @Override
    public void returnBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();

        if (selectedBook.getState().equals("대여중") || selectedBook.getState().equals("분실됨")) {
            selectedBook.setState("도서 정리중");
            //5분 지나면 selectedBook.setState("대여 가능");
            bw.write("[System] 도서가 반납 처리 되었습니다.");
        } else if(selectedBook.getState().equals("대여 가능")) {
            bw.write("[System] 원래 대여가 가능한 도서입니다.");
        } else {
            bw.write("[System] 반납이 불가능한 도서입니다.");
        }
    }

    @Override
    public void lostBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();
        if (selectedBook.getState().equals("대여중")) {
            selectedBook.setState("분실됨");
            bw.write("[System] 도서가 분실 처리 되었습니다.");
        } else if(selectedBook.getState().equals("대여 가능") || selectedBook.getState().equals("도서 정리중")) {
            bw.write("[System] 분실 처리가 불가능한 도서입니다.");
        } else if(selectedBook.getState().equals("분실됨")){
            bw.write("[System] 이미 분실 처리된 도서입니다.");
        }
    }

    @Override
    public void deleteBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .get();
        if(selectedBook == null) {
            bw.write("[System] 존재하지 않는 도서번호 입니다.");
        } else {
            books.remove(selectedBook);
            bw.write("[System] 도서가 삭제 처리 되었습니다.");
        }
    }

}
