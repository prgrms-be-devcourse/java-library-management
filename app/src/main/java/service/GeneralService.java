package service;

import domain.Book;
import repository.GeneralRepository;
import repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneralService implements Service{

    Scanner scanner = new Scanner(System.in);
    private List<Book> list;
    Repository repository = new GeneralRepository();
    private int id;

    public GeneralService() {
        load();
    }

    @Override
    public void load() {
        list = repository.load(list);

        if(list.isEmpty()) id = 1;
        else {
            for (Book book : list) {
                id = book.getId() + 1;
            }
        }
    }

    @Override
    public void mainView() {

        System.out.println("Q. 사용할 기능을 선택해주세요.");
        System.out.println("1. 도서 등록");
        System.out.println("2. 전체 도서 목록 조회");
        System.out.println("3. 제목으로 도서 검색");
        System.out.println("4. 도서 대여");
        System.out.println("5. 도서 반납");
        System.out.println("6. 도서 분실");
        System.out.println("7. 도서 삭제");
        System.out.println("8. 종료");
        System.out.println();

        System.out.print("> ");
        int mode = scanner.nextInt();
        scanner.nextLine();

        //스위치문으로 변경
        if(mode == 1) registration();
        if(mode == 2) viewAll();
        if(mode == 3) findByTitle();
        if(mode == 4) rentBook();
        if(mode == 5) returnBook();
        if(mode == 6) lostBook();
        if(mode == 7) deleteBook();
        if(mode == 8) exit();
    }

    @Override
    public void registration() {
        System.out.println();
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.");
        System.out.println();

        //책 제목 입력
        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        System.out.println();
        System.out.print("> ");
        String title = scanner.nextLine(); // 개행 문자를 읽어내기 위해 추가
        System.out.println();

        //작가 입력
        System.out.println("Q. 작가 이름을 입력하세요.");
        System.out.println();
        System.out.print("> ");
        String author = scanner.nextLine();

        //페이지 수 입력
        System.out.println("Q. 페이지 수를 입력하세요.");
        System.out.println();
        System.out.print("> ");
        int page = scanner.nextInt();

        repository.save(id, title, author, page, list);

        System.out.println();
        System.out.println("[System] 도서 등록이 완료되었습니다.");
        System.out.println();

        id++;
        mainView();
    }

    @Override
    public void viewAll() {
        System.out.println();
        System.out.println("[System] 전체 도서 목록입니다.");
        System.out.println();

        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : list) {
            // 각 필드를 StringBuilder에 추가
            stringBuilder.append("도서 번호 : ").append(book.getId()).append("\n");
            stringBuilder.append("제목 : ").append(book.getTitle()).append("\n");
            stringBuilder.append("작가 이름 : ").append(book.getAuthor()).append("\n");
            stringBuilder.append("페이지 수 : ").append(book.getPage()).append("\n");
            stringBuilder.append("상태 : ").append(book.getCondition()).append("\n\n");
            stringBuilder.append("------------------------------").append("\n");
        }
        System.out.println(stringBuilder);

        System.out.println();
        System.out.println("[System] 도서 목록 끝");
        System.out.println();

        mainView();
    }

    @Override
    public void findByTitle() {
        System.out.println();
        System.out.println("[System] 제목으로 도서 검색 메뉴로 넘어갑니다.");
        System.out.println();

        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        System.out.println();
        System.out.print("> ");
        String searchTitle = scanner.nextLine();
        System.out.println();

        List<Book> foundBooks = repository.findByTitle(searchTitle, list);

        if (foundBooks.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            for (Book book : foundBooks) {

                int id = book.getId();
                String title = book.getTitle();
                String author = book.getAuthor();
                int page = book.getPage();
                String condition = book.getCondition();

                System.out.println();
                System.out.println("도서 번호 : " + id);
                System.out.println("제목 : " + title);
                System.out.println("작가 이름 : " + author);
                System.out.println("페이지 수 : " + page);
                System.out.println("상태 :  " + condition);
                System.out.println();
                System.out.println("------------------------------");
            }
            System.out.println("[System] 검색된 도서 끝");
        }

        mainView();
    }

    @Override
    public void rentBook() {
        System.out.println();
        System.out.println("[System] 도서 대여 메뉴로 넘어갑니다.");
        System.out.println();

        System.out.println("Q. 대여할 도서번호를 입력하세요");
        System.out.println();
        System.out.print("> ");
        int rentId = scanner.nextInt();
        scanner.nextLine();

        if(repository.rentById(rentId, list) == 2){
            System.out.println("[System] 도서가 대여 처리 되었습니다.");
        } else if(repository.rentById(rentId, list) == 1) {
            System.out.println("[System] 이미 대여중인 도서입니다.");
        } else {
            System.out.println("[System] 현재 대여가 불가능한 도서입니다.");
        }

        mainView();
    }

    @Override
    public void returnBook() {
        System.out.println();
        System.out.println("[System] 도서 반납 메뉴로 넘어갑니다.");
        System.out.println();

        System.out.println("Q. 반납할 도서번호를 입력하세요");
        System.out.println();
        System.out.print("> ");
        int returnId = scanner.nextInt();
        scanner.nextLine();

        if(repository.returnById(returnId, list) == 2){
            System.out.println("[System] 도서가 반납 처리 되었습니다.");
        } else if(repository.returnById(returnId, list) == 1) {
            System.out.println("[System] 원래 대여가 가능한 도서입니다.");
        } else {
            System.out.println("[System] 잘못된 도서 번호를 입력하셨습니다.");
        }

        mainView();
    }

    @Override
    public void lostBook() {
        System.out.println();
        System.out.println("[System] 도서 분실 메뉴로 넘어갑니다.");
        System.out.println();

        System.out.println("분실 처리할 도서번호를 입력하세요");
        System.out.println();
        System.out.print("> ");
        int lostId = scanner.nextInt();
        scanner.nextLine();

        String result = repository.lostById(lostId, list);
        System.out.println(result);

        mainView();
    }

    @Override
    public void deleteBook() {
        System.out.println();
        System.out.println("[System] 도서 삭제 메뉴로 넘어갑니다.");
        System.out.println();

        System.out.println("삭제 처리할 도서번호를 입력하세요");
        System.out.println();
        System.out.print("> ");
        int deleteId = scanner.nextInt();
        scanner.nextLine();

        String result = repository.deleteById(deleteId, list);
        System.out.println("[System]" + result);

        mainView();
    }

    @Override
    public void exit() {
        System.out.println();
        System.out.println("[System] 애플리케이션을 종료합니다.");

        System.exit(0);
    }

}
