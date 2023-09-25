package service;

import constant.Guide;
import io.Input;
import io.Output;
import model.Book;
import model.Status;
import repository.Repository;
import util.CsvFileUtil;

public class BookService {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
    }

    // 도서 등록
    public void saveBookToCsv() {
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
}
