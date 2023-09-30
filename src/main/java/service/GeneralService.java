package service;

import domain.Book;
import repository.GeneralRepository;
import repository.Repository;

import exception.InputFormatException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GeneralService implements Service{

    private static final Scanner scanner = new Scanner(System.in);

    Repository repository = new GeneralRepository();

    private List<Book> list = new ArrayList<>();
    private int id;

    public GeneralService() {
        load();
        mainView();
    }

    @Override
    public void load() {
        repository.load(list);

        if(list.isEmpty()) id = 1;
        else {
            for (Book book : list) {
                id = book.getId() + 1;
            }
        }
    }

    @Override
    public void mainView() {
        System.out.println();
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

        switch (mode) {
            case 1 -> registration();
            case 2 -> viewAll();
            case 3 -> findByTitle();
            case 4 -> rentBook();
            case 5 -> returnBook();
            case 6 -> lostBook();
            case 7 -> deleteBook();
            case 8 -> exit();
            default -> throw new InputFormatException("잘못된 입력입니다.");
        }
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
        System.out.println();

        //페이지 수 입력
        System.out.println("Q. 페이지 수를 입력하세요.");
        System.out.println();
        System.out.print("> ");
        int page = scanner.nextInt();

        repository.save(id, title, author, page, list);

        System.out.println();
        System.out.println("[System] 도서 등록이 완료되었습니다.");
        System.out.println();

        id++; // 마지막 책 번호를 기준으로 +1

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
            stringBuilder.append("\n").append(book.toString()).append("\n");
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

        StringBuilder stringBuilder = new StringBuilder();

        if (foundBooks.isEmpty()) {
            System.out.println("검색 결과가 없습니다.");
        } else {
            for (Book book : foundBooks) {
                stringBuilder.append("\n").append(book.toString()).append("\n");
                stringBuilder.append("------------------------------").append("\n");
            }
            System.out.println(stringBuilder);
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

        System.out.println();
        String message = repository.rentById(rentId, list);
        System.out.println("[System] "+ message);

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

        System.out.println();
        String message = repository.returnById(returnId, list);
        System.out.println("[System] " + message);

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

        System.out.println();
        String message = repository.lostById(lostId, list);
        System.out.println("[System] " + message);

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

        System.out.println();
        String message = repository.deleteById(deleteId, list);
        System.out.println("[System] " + message);

        mainView();
    }



    @Override
    public void exit() {
        System.out.println();
        System.out.println("[System] 애플리케이션을 종료합니다.");
        System.out.println();

        GeneralRepository.endApplication(list);

        System.exit(0);
    }

}
