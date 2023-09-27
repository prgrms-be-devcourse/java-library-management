package repository;

import domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


class NormalRepositoryTest {

    Repository repository = new NormalRepository();
    private static ByteArrayOutputStream outputMessage;
    File file = new File("C:/데브코스/java-library-management/app/src/main/resources/도서.csv");
    BufferedReader bf = new BufferedReader(new FileReader(file));

    String original = "";
    NormalRepositoryTest() throws IOException {
        original = "";
        String line = "";
        while((line = bf.readLine()) != null) {
            original += line + "\n";
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        outputMessage = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputMessage));
    }

    @AfterEach
    void tearDown() throws IOException {

        System.setOut(System.out);

        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        bw.write(original);
        bw.close();
    }

    @Test
    void register() throws IOException {
        Book book = new Book();
        book.setTitle("어린왕자");
        book.setWriter("생떽쥐베리");
        book.setPage(20);

        repository.register(book);

        bf = new BufferedReader(new FileReader(file));

        String line = "";
        boolean flag = false;

        while((line = bf.readLine()) != null) {
            String[] split = line.split(",");
            if(Integer.parseInt(split[0]) == book.getId()
                && split[1].equals(book.getTitle())
                && split[2].equals(book.getWriter())
                && Integer.parseInt(split[3]) == book.getPage()
                && split[4].equals(book.getState())) {
                flag = true;
                break;
            }
        }
        Assertions.assertTrue(flag);
    }

    @Test
    void printList() throws IOException {
        List<Book> books = new ArrayList<>();
        fileToList(books, file);

        repository.printList();
        StringBuilder tmp = new StringBuilder();
        for(Book book : books) {
            tmp.append("\n도서번호 : ").append(String.valueOf(book.getId())).append("\n제목 : ").append(book.getTitle()).append("\n작가 이름 : ").append(book.getWriter()).append("\n페이지 수: ").append(String.valueOf(book.getPage())).append("페이지").append("\n상태 : ").append(book.getState()).append("\n\n------------------------------\r\n");
        }
        Assertions.assertEquals(tmp.toString(), outputMessage.toString());
    }

    private void fileToList(List<Book> books, File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = "";

        while((line = bf.readLine()) != null) {
            String[] split = line.split(",");
            Book tmpBook = new Book();

            tmpBook.setId(Integer.parseInt(split[0]));
            tmpBook.setTitle(split[1]);
            tmpBook.setWriter(split[2]);
            tmpBook.setPage(Integer.parseInt(split[3]));
            tmpBook.setState(split[4]);

            books.add(tmpBook);
        }
    }

    @Test
    void search() throws IOException {
        List<Book> books = new ArrayList<>();
        fileToList(books, file);

        repository.search("여우");
        StringBuilder tmp = new StringBuilder();
        for(Book book : books) {
            if(book.getTitle().contains("여우"))
                tmp.append("\n도서번호 : ").append(String.valueOf(book.getId())).append("\n제목 : ").append(book.getTitle()).append("\n작가 이름 : ").append(book.getWriter()).append("\n페이지 수: ").append(String.valueOf(book.getPage())).append("페이지").append("\n상태 : ").append(book.getState()).append("\n\n------------------------------\r\n");
        }
        Assertions.assertEquals(tmp.toString(), outputMessage.toString());
    }

    @Test
    void rental() throws IOException {
        String tmp = "";
        repository.rental(32434); //없는 번호
        tmp += "[System] 존재하지 않는 도서 번호입니다.\r\n";
        
        repository.rental(713338630); //대여중
        tmp += "[System] 이미 대여중인 도서입니다.\r\n";

        repository.rental(1); //대여 가능
        tmp += "[System] 도서가 대여 처리 되었습니다.\r\n";
        String state = getStateFromId(1);
        Assertions.assertEquals(state, "대여중");


        Book book = new Book();
        book.setId(3);
        book.setTitle("순수 이성 비판");
        book.setWriter("칸트");
        book.setPage(432);
        book.setState("도서 정리중");
        repository.register(book);
        repository.rental(3); //도서 정리중
        tmp += "[System] 정리 중인 도서입니다.\r\n";
        repository.deleteBook(3);
        tmp += "[System] 도서가 삭제 처리 되었습니다.\r\n";

        repository.rental(302155142); //분실됨
        tmp += "[System] 분실된 도서입니다.\r\n";

        Assertions.assertEquals(tmp, outputMessage.toString());
    }

    @Test
    void returnBook() throws IOException {
        String tmp = "";
        repository.returnBook(32434); //없는 번호
        tmp += "[System] 존재하지 않는 도서 번호입니다.\r\n";

        repository.returnBook(713338630); //대여중
        tmp += "[System] 도서가 반납 처리 되었습니다.\r\n";
        String state = getStateFromId(713338630);
        Assertions.assertEquals(state, "도서 정리중");

        repository.returnBook(1); //대여 가능
        tmp += "[System] 원래 대여가 가능한 도서입니다.\r\n";
        //롤백

        Book book = new Book();
        book.setId(3);
        book.setTitle("순수 이성 비판");
        book.setWriter("칸트");
        book.setPage(432);
        book.setState("도서 정리중");
        repository.register(book);
        repository.returnBook(3); //도서 정리중
        tmp += "[System] 반납이 불가능한 도서입니다.\r\n";
        repository.deleteBook(3);
        tmp += "[System] 도서가 삭제 처리 되었습니다.\r\n";

        repository.returnBook(302155142); //분실됨
        tmp += "[System] 도서가 반납 처리 되었습니다.\r\n";
        state = getStateFromId(302155142);
        Assertions.assertEquals(state, "도서 정리중");

        Assertions.assertEquals(tmp, outputMessage.toString());
    }

    @Test
    void lostBook() throws IOException {
        String tmp = "";
        repository.lostBook(32434); //없는 번호
        tmp += "[System] 존재하지 않는 도서 번호입니다.\r\n";

        repository.lostBook(713338630); //대여중
        tmp += "[System] 도서가 분실 처리 되었습니다.\r\n";
        String state = getStateFromId(713338630);
        Assertions.assertEquals(state, "분실됨");

        repository.lostBook(1); //대여 가능
        tmp += "[System] 분실 처리가 불가능한 도서입니다.\r\n";

        repository.lostBook(821270929); //도서 정리중
        tmp += "[System] 분실 처리가 불가능한 도서입니다.\r\n";

        repository.lostBook(302155142); //분실됨
        tmp += "[System] 이미 분실 처리된 도서입니다.\r\n";

        Assertions.assertEquals(tmp, outputMessage.toString());
    }

    @Test
    void deleteBook() throws IOException {
        repository.deleteBook(1);

        bf = new BufferedReader(new FileReader(file));
        String line = "";
        boolean flag = false;

        while((line = bf.readLine()) != null) {
            String[] split = line.split(",");
            if(Integer.parseInt(split[0]) == 1) {
                flag = true;
                break;
            }
        }
        Assertions.assertFalse(flag);
    }

    private String getStateFromId(int id) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = "";
        while((line = bf.readLine()) != null) {
            String[] split = line.split(",");
            if(Integer.parseInt(split[0]) == id) {
                return split[4];
            }
        }
        return "";
    }
}