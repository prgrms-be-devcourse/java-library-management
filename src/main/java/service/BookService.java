package service;

import constant.Guide;
import io.Input;
import io.Output;
import model.Book;
import model.Status;
import repository.Repository;
import util.CsvFileUtil;

import java.util.List;

public class BookService {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    // 도서 등록
    public void saveBook() {
        Output.printGuide(Guide.REGISTER_START);

        System.out.println("Q. 등록할 도서 제목을 입력하세요.");
        String title = Input.inputString();

        System.out.println("Q. 작가 이름을 입력하세요");
        String author = Input.inputString();

        System.out.println("Q. 페이지 수를 입력하세요.");
        int pageNum = Input.inputNumber();

        Output.printGuide(Guide.REGISTER_END);

        Long id = CsvFileUtil.findLastId("book.csv") + 1;
        Book book = new Book(id, title, author, pageNum, Status.AVAILABLE);
        repository.saveBook(book);
    }

    // 도서 전체 목록 조회
    public void findAllBook() {
        List<Book> books = repository.findAllBook();
        Output.printGuide(Guide.FIND_ALL_START);
        for (Book book : books) {
            System.out.println(book.toString());
        }
        Output.printGuide(Guide.FIND_ALL_END);
    }

    // 특정 도서 제목으로 조회
    public void findBooksByTitle() {
        Output.printGuide(Guide.FIND_BY_TITLE_START);
        System.out.println("Q. 검색할 도서 제목 일부를 입력하세요.");
        String title = Input.inputString();
        List<Book> books = repository.findBookByTitle(title);

        for (Book book : books) {
            System.out.println(book.toString());
        }
        Output.printGuide(Guide.FIND_BY_TITLE_END);
    }
}
