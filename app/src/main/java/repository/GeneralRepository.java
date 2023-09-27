package repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import domain.Book;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class GeneralRepository implements Repository{

    private final static String csvFileName = "/Users/kimnamgyu/desktop/study/dev-course/csvFileEmpty.csv";

    @Override
    public void load(List<Book> list) {
        list.clear();
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
                list.add(new Book(id, title, author, page, condition));
            }
            csvReader.close();
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(int id, String title, String author, int page, List<Book> list) {

        try (CSVPrinter csvPrinter = new CSVPrinter(new FileWriter(csvFileName, true), CSVFormat.DEFAULT)) {

            list.add(new Book(id, title, author, page, "대여 가능"));
            csvPrinter.printRecord(id, title, author, page, "대여 가능");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findByTitle(String searchTitle, List<Book> list) {
        List<Book> foundBooks = new ArrayList<>();

        Iterator<Book> iterator = list.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
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
    public String rentById(int rentId, List<Book> list) {
        String message = "";
        boolean isBookExist = false;
        Iterator<Book> iterator = list.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == rentId) {
                isBookExist = true;
                if(Objects.equals(book.getCondition(), "대여 가능")){
                    book.setCondition("대여 중");
                    message = "도서가 대여 처리 되었습니다.";
                }
                else if (Objects.equals(book.getCondition(), "대여 중")) {
                    message = "이미 대여중인 도서입니다.";
                }
                else message = "현재 대여가 불가능한 도서입니다.";
                break; // ID를 찾았으므로 루프 종료
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(list);

        return message;
    }

    //반납 하면 5분 동안 대여 불가능 한 상태로
    @Override
    public String returnById(int returnId, List<Book> list) {
        String message = "";
        boolean isBookExist = false;
        Iterator<Book> iterator = list.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == returnId) {
                isBookExist = true;
                if(Objects.equals(book.getCondition(), "대여 중") || Objects.equals(book.getCondition(), "분실됨")) { //대여 중 or 분실됨이면 반납 가능
                    book.setCondition("대여 가능");
                    message = "도서가 반납 처리 되었습니다";
                } else { // 대여 가능
                    message = "원래 대여가 가능한 도서입니다.";
                }
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(list);

        return message;
    }

    @Override
    public String lostById(int lostId, List<Book> list) {
        String message = "";
        boolean isBookExist = false;

        Iterator<Book> iterator = list.iterator();

        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == lostId) {
                isBookExist = true;
                if(Objects.equals(book.getCondition(), "분실됨")) {
                    message = "이미 분실 처리된 도서입니다.";
                } else {
                    book.setCondition("분실됨");
                    message = "도서가 분실 처리 되었습니다.";
                }
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(list);

        return message;
    }

    @Override
    public String deleteById(int deleteId, List<Book> list) {
        String message = "";
        boolean isBookExist = false;

        Iterator<Book> iterator = list.iterator();
        while (iterator.hasNext()) {
            Book book = iterator.next();
            if (book.getId() == deleteId) {
                iterator.remove(); // delete Id를 가진 레코드를 삭제 -> enhanced for vs iterator 결정 이유
                isBookExist = true;
                message = "도서가 삭제 처리 되었습니다.";
                break;
            }
        }

        if(!isBookExist) message = "존재하지 않는 도서번호 입니다.";

        saveToCSV(list);

        return message;
    }

    // 변경된 점을 CSV파일에 저장하는 코드(덮어쓰기)
    private static void saveToCSV(List<Book> list) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(csvFileName))) {
            for (Book book : list) {
                String[] record = {
                        String.valueOf(book.getId()),
                        book.getTitle(),
                        book.getAuthor(),
                        String.valueOf(book.getPage()),
                        book.getCondition()
                };
                writer.writeNext(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
