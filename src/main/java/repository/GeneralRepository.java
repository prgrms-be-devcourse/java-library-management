package repository;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import domain.Book;

import exception.LoadException;
import exception.SaveException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static domain.BookCondition.*;

public class GeneralRepository implements Repository{

    private static final String csvFileName = "/Users/kimnamgyu/desktop/study/dev-course/csvFile.csv";

    @Override
    public void load(List<Book> bookList) {
        try {
            // CSV 파일을 읽어오는 CSVReader 객체 생성
            CSVReader csvReader = new CSVReader(new FileReader(csvFileName));

            // CSV 파일 내용을 읽어오기
            List<String[]> records = csvReader.readAll();

            for (String[] record : records) {
                int id = Integer.parseInt(record[0]);
                String title = record[1];
                String author = record[2];
                int page = Integer.parseInt(record[3]);
                String condition = record[4];
                bookList.add(new Book(id, title, author, page, condition));
            }
            csvReader.close();
        } catch (IOException | CsvException e) {
            throw new LoadException("CSV 파일을 읽어올 수 없습니다");
        }
    }

    @Override
    public void save(int id, String title, String author, int page, List<Book> bookList) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvFileName, true), CSVFormat.DEFAULT)) {

            bookList.add(new Book(id, title, author, page, AVAILABLE.getCondition()));
            csvPrinter.printRecord(id, title, author, page, AVAILABLE.getCondition());

        } catch (IOException e) {
            throw new SaveException("CSV 파일에 저장할 수 없습니다.");
        }
    }

    @Override
    public List<Book> findByTitle(String searchTitle, List<Book> bookList) {
        List<Book> foundBooks = new ArrayList<>();

        Iterator<Book> bookIterator = bookList.iterator();

        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            String title = book.getTitle();
            // title에서 검색어가 포함되어 있는지 확인 (대소문자 무시)
            if (title.toLowerCase().contains(searchTitle)) {
                int id = book.getId();
                String author = book.getAuthor();
                int page = book.getPage();
                String condition = book.getCondition();

                foundBooks.add(new Book(id, title, author, page, condition));
            }
        }
        return foundBooks;
    }

    @Override
    public String rentById(int rentId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;
        Iterator<Book> bookIterator = bookList.iterator();

        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == rentId) {
                isBookExist = true;
                if(Objects.equals(book.getCondition(), AVAILABLE.getCondition())){
                    book.setCondition(RENTED.getCondition());
                    message = "도서가 대여 처리 되었습니다.";
                }
                else if (Objects.equals(book.getCondition(), RENTED.getCondition())) {
                    message = "이미 대여중인 도서입니다.";
                }
                else message = "현재 대여가 불가능한 도서입니다.";
                break; // ID를 찾았으므로 루프 종료
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(bookList);

        return message;
    }

    //반납 하면 5분 동안 대여 불가능 한 상태로
    @Override
    public String returnById(int returnId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;
        Iterator<Book> bookIterator = bookList.iterator();

        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == returnId) {
                isBookExist = true;
                if(Objects.equals(book.getCondition(), RENTED.getCondition()) || Objects.equals(book.getCondition(), LOST.getCondition())) { //대여 중 or 분실됨이면 반납 가능
                    book.setCondition(ORGANIZING.getCondition());
                    message = "도서가 반납 처리 되었습니다";
                } else { // 대여 가능
                    message = "원래 대여가 가능한 도서입니다.";
                }
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(bookList);

        returnCondition(returnId, bookList);

        return message;
    }

    @Override
    public String lostById(int lostId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;

        Iterator<Book> bookIterator = bookList.iterator();

        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == lostId) {
                isBookExist = true;
                if(Objects.equals(book.getCondition(), LOST.getCondition())) {
                    message = "이미 분실 처리된 도서입니다.";
                } else {
                    book.setCondition(LOST.getCondition());
                    message = "도서가 분실 처리 되었습니다.";
                }
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(bookList);

        return message;
    }

    @Override
    public String deleteById(int deleteId, List<Book> bookList) {
        String message = "";
        boolean isBookExist = false;

        Iterator<Book> bookIterator = bookList.iterator();
        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            if (book.getId() == deleteId) {
                bookIterator.remove(); // delete Id를 가진 레코드를 삭제 -> enhanced for vs bookIterator 결정 이유
                isBookExist = true;
                message = "도서가 삭제 처리 되었습니다.";
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(bookList);

        return message;
    }

    //애플리케이션 종료 시 도서 관리중을 바꾸기 위한 메서드
    public static void endApplication(List<Book> bookList) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvFileName), CSVFormat.DEFAULT)) {
            for(Book book : bookList) {
                if(Objects.equals(book.getCondition(), ORGANIZING.getCondition())) book.setCondition(AVAILABLE.getCondition());
                csvPrinter.printRecord(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), book.getCondition());
            }
        } catch (IOException e) {
            throw new SaveException("CSV 파일에 저장할 수 없습니다.");
        }
    }

    // 변경된 점을 CSV파일에 저장하는 코드(덮어쓰기)
    private static void saveToCSV(List<Book> bookList) {
        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvFileName), CSVFormat.DEFAULT)) {
            for(Book book : bookList) {
                csvPrinter.printRecord(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), book.getCondition());
            }
        } catch (IOException e) {
            throw new SaveException("CSV 파일에 저장할 수 없습니다.");
        }
    }

    //도서 정리중에서 대여 가능으로 바꾸는 메서드
    private static void returnCondition(int returnId, List<Book> bookList) {
        Timer m_timer = new Timer(true);
        TimerTask m_task = new TimerTask() {
            @Override
            public void run() {
                bookList.get(returnId-1).setCondition(AVAILABLE.getCondition());
                saveToCSV(bookList);
            }
        };
        m_timer.schedule(m_task, 300000);
    }
}
