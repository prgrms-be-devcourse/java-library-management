import domain.Book;
import repository.NormalRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class BookService {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private final NormalRepository normalRepository = new NormalRepository();


    public void saveBook() throws IOException {
        System.out.println("등록할 도서 제목을 입력하세요.\n");
        String title = getInput();
        System.out.println("\n작가 이름을 입력하세요.\n");
        String author = getInput();
        System.out.println("\n페이지 수를 입력하세요.\n");
        Integer page = Integer.parseInt(getInput());

        Book book = new Book(title, author, page);
        normalRepository.register(book);
    }

    public void showBookList() {
        List<Book> bookList = normalRepository.getBookList();
        for (Book book:bookList) {
            book.printBookInfo();
        }
    }

    public void findBookByTitle() throws Exception {
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.\n");
        String title = getInput();

        List<Book> bookList = normalRepository.findByTitle(title);

        for (Book book:bookList) {
            book.printBookInfo();
        }
    }

    public void borrowBook() throws IOException {
        System.out.println("Q. 대여할 도서번호를 입력하세요\n");
        Long id = Long.valueOf(getInput());
        normalRepository.borrow(id);
    }

    public void returnBook() throws IOException {
        System.out.println("Q. 반납할 도서번호를 입력하세요\n");
        Long id = Long.valueOf(getInput());
        normalRepository.returnBook(id);
    }

    public void reportLostBook() throws IOException {
        System.out.println("Q. 분실 처리할 도서번호를 입력하세요\n");
        Long id = Long.valueOf(getInput());
        normalRepository.report(id);
    }

    public void removeBook() throws Exception{
        System.out.println("Q. 삭제 처리할 도서번호를 입력하세요\n");
        Long id = Long.valueOf(getInput());
        normalRepository.remove(id);
    }

    public String getInput() throws IOException {
        System.out.print("> ");
        return br.readLine().strip();
    }
}