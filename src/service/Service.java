package service;

import domain.Book;
import repository.Repository;

import java.io.*;

public class Service {
    private static Repository repository;
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void register() throws IOException {
        Book book = new Book();
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        book.setTitle(bf.readLine());

        System.out.println("Q. 작가 이름을 입력하세요.");
        book.setWriter(bf.readLine());

        System.out.println("Q. 페이지 수를 입력하세요.");
        book.setPage(Integer.parseInt(bf.readLine()));

        repository.register(book);
        System.out.println("[System] 도서 등록이 완료되었습니다.");
    }

    public void list() {
        System.out.println("[System] 전체 도서 목록입니다.");
        repository.printList();
        System.out.println("[System] 도서 목록 끝");
    }

    public void search() throws IOException {
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        String titleWord = bf.readLine();
        repository.search(titleWord);
        System.out.println("[System] 검색된 도서 끝");
    }

    public void rental() throws IOException {
        System.out.println("Q. 대여할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.rental(id);
    }

    public void returnBook() throws IOException {
        System.out.println("Q. 반납할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.returnBook(id);
    }

    public void lostBook() throws IOException {
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.lostBook(id);
    }

    public void deleteBook() throws IOException {
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요");
        int id = Integer.parseInt(bf.readLine());
        repository.deleteBook(id);
    }
}
