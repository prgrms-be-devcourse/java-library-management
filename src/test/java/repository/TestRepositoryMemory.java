package repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

class TestRepositoryMemory {
    MemoryRepository repository = new MemoryRepository();
    private static ByteArrayOutputStream outputMessage;

    @BeforeEach
    void setUp() throws IOException {
        outputMessage = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputMessage));

        repository.books.add(setBook(1, "이기적 유전자", "리처드", 324, "대여 가능"));
        repository.books.add(setBook(2, "해리포터", "조앤롤링", 224, "대여중"));
        repository.books.add(setBook(3, "어린왕자", "생떽쥐베리", 34, "도서 정리중"));
        repository.books.add(setBook(4, "백설공주", "안데르센", 24, "분실됨"));
    }

    @AfterEach
    void tearDown() throws IOException {
        System.setOut(System.out);
    }

    @Test
    void register() throws IOException {
        Book book = new Book();
        book.setTitle("연애혁명");
        book.setWriter("232");
        book.setPage(20);

        repository.register(book);
        Assertions.assertTrue(repository.books.contains(book));
    }

    @Test
    void printList() {
        repository.printList();
        StringBuilder tmp = new StringBuilder();
        for (Book book : repository.books) {
            tmp.append("\n도서번호 : ").append(String.valueOf(book.getId())).append("\n제목 : ").append(book.getTitle()).append("\n작가 이름 : ").append(book.getWriter()).append("\n페이지 수: ").append(String.valueOf(book.getPage())).append("페이지").append("\n상태 : ").append(book.getState()).append("\n\n------------------------------\r\n");
        }
        Assertions.assertEquals(tmp.toString(), outputMessage.toString());
    }

    @Test
    void search() throws IOException {
        repository.search("백설");
        StringBuilder tmp = new StringBuilder();
        for(Book book : repository.books) {
            if(book.getTitle().contains("백설"))
                tmp.append("\n도서번호 : ").append(String.valueOf(book.getId())).append("\n제목 : ").append(book.getTitle()).append("\n작가 이름 : ").append(book.getWriter()).append("\n페이지 수: ").append(String.valueOf(book.getPage())).append("페이지").append("\n상태 : ").append(book.getState()).append("\n\n------------------------------\r\n");
        }
        Assertions.assertEquals(tmp.toString(), outputMessage.toString());
    }

    @Test
    void rental() throws IOException {
        String tmp = "";
        repository.rental(32434); //없는 번호
        tmp += "[System] 존재하지 않는 도서 번호입니다.\r\n";

        repository.rental(2); //대여중
        tmp += "[System] 이미 대여중인 도서입니다.\r\n";

        repository.rental(1); //대여 가능
        tmp += "[System] 도서가 대여 처리 되었습니다.\r\n";
        repository.books.forEach(book -> {
            if(book.getId() == 1) Assertions.assertEquals(book.getState(), "대여중");
        });

        repository.rental(3); //도서 정리중
        tmp += "[System] 정리 중인 도서입니다.\r\n";

        repository.rental(4); //분실됨
        tmp += "[System] 분실된 도서입니다.\r\n";

        Assertions.assertEquals(tmp, outputMessage.toString());
    }

    @Test
    void returnBook() throws IOException {
        String tmp = "";
        repository.returnBook(32434); //없는 번호
        tmp += "[System] 존재하지 않는 도서 번호입니다.\r\n";

        repository.returnBook(2); //대여중
        tmp += "[System] 도서가 반납 처리 되었습니다.\r\n";
        repository.books.forEach(book -> {
            if(book.getId() == 2) Assertions.assertEquals(book.getState(), "도서 정리중");
        });

        repository.returnBook(1); //대여 가능
        tmp += "[System] 원래 대여가 가능한 도서입니다.\r\n";

        repository.returnBook(3); //도서 정리중
        tmp += "[System] 반납이 불가능한 도서입니다.\r\n";

        repository.returnBook(4); //분실됨
        repository.books.forEach(book -> {
            if(book.getId() == 4) Assertions.assertEquals(book.getState(), "도서 정리중");
        });
        tmp += "[System] 도서가 반납 처리 되었습니다.\r\n";
        Assertions.assertEquals(tmp, outputMessage.toString());
    }

    @Test
    void lostBook() throws IOException {
        String tmp = "";
        repository.lostBook(32434); //없는 번호
        tmp += "[System] 존재하지 않는 도서 번호입니다.\r\n";

        repository.lostBook(2); //대여중
        tmp += "[System] 도서가 분실 처리 되었습니다.\r\n";
        repository.books.forEach(book -> {
            if(book.getId() == 2) Assertions.assertEquals(book.getState(), "분실됨");
        });

        repository.lostBook(1); //대여 가능
        tmp += "[System] 분실 처리가 불가능한 도서입니다.\r\n";

        repository.lostBook(3); //도서 정리중
        tmp += "[System] 분실 처리가 불가능한 도서입니다.\r\n";

        repository.lostBook(4); //분실됨
        tmp += "[System] 이미 분실 처리된 도서입니다.\r\n";

        Assertions.assertEquals(tmp, outputMessage.toString());
    }

    @Test
    void deleteBook() throws IOException {
        repository.deleteBook(1);

        boolean flag = false;
        for(Book book : repository.books) {
            if(book.getId() == 1) {
                flag = true;
                break;
            }
        }
        Assertions.assertFalse(flag);
    }

    private Book setBook(int id, String title, String writer, int page, String state) {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setWriter(writer);
        book.setPage(page);
        book.setState(state);
        return book;
    }
}