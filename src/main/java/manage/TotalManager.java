package manage;

import entity.Book;
import entity.State;
import exception.EntityNotFoundException;
import manage.book.BookManager;

import java.util.List;
import java.util.Scanner;

public class TotalManager {
    private final BookManager bookManager;
    private final Scanner sc;

    public TotalManager(BookManager bookManager, Scanner sc) {
        this.bookManager = bookManager;
        this.sc = sc;
    }

    public void run(){
        int cmd = -1;
        while (cmd != 8){
            System.out.println("""
                    기능을 선택해 주세요.
                    1. 도서 등록
                    2. 도서 전체 조회
                    3. 도서 제목 검색
                    4. 도서 대여
                    5. 도서 반납
                    6. 도서 분실
                    7. 도서 삭제
                    8. 종료
                    """);
            System.out.print("> ");
            try {
                cmd = Integer.parseInt(sc.nextLine().trim());

                if (cmd == 1)
                    register();
                else if (cmd == 2)
                    searchAll();
                else if (cmd == 3)
                    search();
                else if (cmd == 4)
                    rent();
                else if (cmd == 5)
                    returnBook();
                else if (cmd == 6)
                    lost();
                else if (cmd == 7)
                    delete();
            }catch (IllegalArgumentException e){
                System.out.println("잘못된 입력입니다. 최초 선택으로 이동합니다.\n");
            }catch (EntityNotFoundException e){
                System.out.println("존재하지 않는 도서입니다. 이미 삭제된 도서일 수 있습니다. 최초 선택으로 이동합니다.\n");
            }
        }
        bookManager.saveFile();
    }

    private void register(){
        System.out.print("등록 도서 정보를 입력합니다.\n도서 제목을 입력해 주세요.\n> ");

        String[] bookInfo = new String[3];
        bookInfo[0] = sc.nextLine().trim();

        System.out.print("작가 이름을 입력해 주세요.\n> ");

        bookInfo[1] = sc.nextLine().trim();

        System.out.print("페이지 수를 입력해 주세요.(숫자만 입력해 주세요!)\n> ");

        bookInfo[2] = sc.nextLine().trim();

        Book createdBook = new Book(bookInfo[0], bookInfo[1], Integer.parseInt(bookInfo[2]));
        bookManager.register(createdBook);

        System.out.println("정상 등록 되었습니다!\n");
    }

    private void searchAll(){
        System.out.println("전체 도서 목록을 조회합니다.");
        List<Book> books = bookManager.searchAll();

        printingBookList(books);
    }

    private void search(){
        System.out.println("검색할 도서 제목 일부를 입력해주세요!");
        System.out.print("> ");

        String text = sc.nextLine().trim();
        List<Book> books = bookManager.search(text);

        printingBookList(books);
    }

    private void printingBookList(List<Book> books){
        System.out.println();

        books.stream().forEach(book -> {
            System.out.println(book.printInfo());
            System.out.println();
        });

        System.out.println("총 " + books.size() + "개의 도서가 조회되었습니다!\n");
    }

    private void rent(){
        System.out.print("대여할 도서 번호를 입력해주세요.\n> ");

        int number = Integer.parseInt(sc.nextLine().trim());
        State initState = bookManager.rent(number);

        if (initState != State.AVAILABLE)
            System.out.println("도서가 " + initState.getKoreanState() + " 상태이기 때문에 대여할 수 없습니다.\n");
        else
            System.out.println("도서가 대여되었습니다!\n");
    }

    private void returnBook(){
        System.out.print("반납할 도서 번호를 입력해 주세요\n> ");

        int number = Integer.parseInt(sc.nextLine().trim());
        State initState = bookManager.returnBook(number);

        if (initState == State.RENTED)
            System.out.println("정상 반납되었습니다.\n");
        else
            System.out.println("반납 대상 도서가 아닙니다!\n해당 도서 상태:" + initState.getKoreanState() + "\n");
    }

    private void lost(){
        System.out.print("분실 처리할 도서 번호를 입력해 주세요\n> ");

        int number = Integer.parseInt(sc.nextLine().trim());
        State initState = bookManager.lost(number);

        if (initState != State.LOST)
            System.out.println("분실 처리되었습니다.\n");
        else
            System.out.println("이미 분실된 도서입니다.\n");
    }

    private void delete(){
        System.out.print("삭제할 도서 번호를 입력해 주세요\n> ");

        int number = Integer.parseInt(sc.nextLine().trim());
        bookManager.delete(number);

        System.out.println("정상 삭제되었습니다.\n");
    }
}
