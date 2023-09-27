package service;

import domain.Book;
import repository.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Service{

    private Repository repository;

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public Service(Repository repository) {
        this.repository = repository;
    }

    public void addBook() throws IOException {
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        System.out.print("\n >");
        String bookName = br.readLine();
        System.out.println("Q. 작가 이름을 입력하세요.");
        System.out.print("\n >");
        String author = br.readLine();
        System.out.println("Q. 페이지 수를 입력하세요.");
        System.out.print("\n >");
        int page = Integer.parseInt(br.readLine());

        repository.addBook(new Book(bookName, author, page));
        System.out.println("[System] 도서 등록이 완료되었습니다.\n");
    }

    public void getAll() {
        repository.getAll().forEach(book -> {
            System.out.println("도서제목 : " + book.getId());
            System.out.println("제목 : " + book.getName());
            System.out.println("작가 이름 : " + book.getPage());
            System.out.println("페이지 수 : " + book.getPage());
            System.out.println("상태 : " + book.getStatus().getStatusName());
            System.out.println("----------------------");
        });
        System.out.println("[System] 도서 목록 끝\n");
    }

    public void searchName() throws IOException {
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        System.out.print("\n >");
        String name = br.readLine();
        Book book = repository.searchBook(name);

        System.out.println("도서제목 : " + book.getId());
        System.out.println("제목 : " + book.getName());
        System.out.println("작가 이름 : " + book.getAuthor());
        System.out.println("페이지 수 : " + book.getPage());
        System.out.println("상태 : " + book.getStatus().getStatusName());
        System.out.println("----------------------");
        System.out.println("[System] 검색된 도서 끝\n");
    }

    public void rentalBook() throws IOException {
        System.out.println("Q. 대여할 도서번호를 입력하세요");
        System.out.print("\n >");
        Long bookNumber = Long.parseLong(br.readLine());
        Book book = repository.getBook(bookNumber);
        book.rentalBook();
    }

    public void organizeBook() throws IOException {
        System.out.println("Q. 반납할 도서번호를 입력하세요");
        System.out.print("\n >");
        Long bookNumber = Long.parseLong(br.readLine());
        Book book = repository.getBook(bookNumber);
        book.organizeBook();
        BackGround backGroundTimer = new BackGround(book);
        backGroundTimer.start();
    }

    public void lostBook() throws IOException {
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요");
        System.out.print("\n >");
        Long bookNumber = Long.parseLong(br.readLine());
        Book book = repository.getBook(bookNumber);
        book.lostBook();
    }

    public void deleteBook() throws IOException {
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요");
        System.out.print("\n >");
        Long bookNumber = Long.parseLong(br.readLine());
        repository.deleteBook(bookNumber);
        System.out.println("[System] 도서가 삭제 처리 되었습니다.");
    }

    private static class BackGround extends Thread{
        private Book book;

        BackGround(Book book){
            this.book = book;
        }

        @Override
        public void run() {
            try {
                sleep(300000);
                book.returnBook();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
