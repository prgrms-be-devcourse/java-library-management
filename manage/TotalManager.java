package manage;

import entity.Book;
import entity.State;
import exception.EntityNotFoundException;

import java.util.List;
import java.util.Scanner;

public class TotalManager {
    private static BookManager bookManager;
    private static Scanner sc = new Scanner(System.in);
    public static void run(){
        int cmd = -1;
        init();
        while (cmd != 8){ // enum이나 다른 방식으로 추후 리팩토링
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
    }

    private static void init(){
        System.out.print("모드를 선택합니다. 1. 운용 모드 2. 테스트 모드\n> ");
        int mode = sc.nextInt();
        String ignore = sc.nextLine();

        if(mode == 1)
            bookManager = new FileBookManager();
        else if (mode == 2)
            bookManager = new TestBookManager();
    }

    private static void register(){
        System.out.println("등록 도서 정보를 입력합니다.\n도서 제목과 작가이름, 페이지 수를 공백으로 구분하여 입력해 주세요");
        System.out.print("> ");
        String[] bookInfo = sc.nextLine().split(" ");

        Book createdBook = Book.createBook(bookInfo);
        bookManager.register(createdBook);
        System.out.println("정상 등록 되었습니다!\n");
    }

    private static void searchAll(){
        System.out.println("전체 도서 목록을 조회합니다.");
        List<Book> books = bookManager.searchAll();

        printingBookList(books);
    }

    private static void search(){
        System.out.println("검색할 도서 제목 일부를 입력해주세요!");
        System.out.print("> ");
        String text = sc.nextLine().trim();
        List<Book> books = bookManager.search(text);

        printingBookList(books);
    }

    private static void printingBookList(List<Book> books){
        System.out.println();

        books.stream().forEach(book -> {
            System.out.println(book.printInfo());
            System.out.println();
        });

        System.out.println("총 " + books.size() + "개의 도서가 조회되었습니다!\n");
    }

    private static void rent(){
        System.out.print("대여할 도서 번호를 입력해주세요.\n> ");
        int number = Integer.parseInt(sc.nextLine().trim());
        State initState = bookManager.rent(number);

        if (initState != State.AVAILABLE)
            System.out.println("도서가 " + initState + " 상태이기 때문에 대여할 수 없습니다.\n");
        else
            System.out.println("도서가 대여되었습니다!\n");
    }

    private static void returnBook(){
        System.out.print("반납할 도서 번호를 입력해 주세요\n> ");
        int number = Integer.parseInt(sc.nextLine().trim());
        State initState = bookManager.returnBook(number);
        if (initState == State.RENTED)
            System.out.println("정상 반납되었습니다.\n");
        else
            System.out.println("반납 대상 도서가 아닙니다!\n해당 도서 상태:" + initState + "\n");
    }

    private static void lost(){
        int number = Integer.parseInt(sc.nextLine().trim());
        State initState = bookManager.lost(number);
        if (initState != State.LOST && initState != State.DELETED)
            System.out.println("분실 처리되었습니다.\n");
        else
            System.out.println("분실 대상 도서가 아닙니다! 이미 분실되었거나 삭제된 도서입니다.\n해당 도서 상태:" + initState + "\n");
    }

    private static void delete(){
        int number = Integer.parseInt(sc.nextLine().trim());
        State initState = bookManager.delete(number);
        if (initState == State.DELETED)
            System.out.println("정상 삭제되었습니다.\n");
        else
            System.out.println("반납 대상 도서가 아닙니다!\n해당 도서 상태:" + initState + "\n");
    }
}
