package repository;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import domain.Book;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

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
    public List<Book> load(List<Book> list) {
        list = new ArrayList<>();
        try {
            // CSV 파일을 읽어오는 CSVReader 객체 생성
            CSVReader csvReader = new CSVReader(new FileReader(csvFileName));

            // CSV 파일 내용을 읽어오기
            List<String[]> records = csvReader.readAll();
            // 각 레코드(행)를 처리
            for (String[] record : records) {
                // CSV 파일에서 읽어온 데이터 처리
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
        return list;
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

        try (FileReader fileReader = new FileReader(csvFileName); CSVParser csvParser = CSVFormat.DEFAULT.parse(fileReader)) {
            for (CSVRecord record : csvParser) {
                String title = record.get(1); //id, title, author, page, condition 순서

                // title에서 검색어가 포함되어 있는지 확인
                if (title.contains(searchTitle)) {
                    int id = Integer.parseInt(record.get(0));
                    String author = record.get(2);
                    int page = Integer.parseInt(record.get(3));
                    String condition = record.get(4);

                    foundBooks.add(new Book(id, title, author, page, condition));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return foundBooks;
    }

    @Override
    public int rentById(int rentId, List<Book> list) {

        int flag = 1; // -> 대여 중인 상황

        for (Book book : list) {
            if (book.getId() == rentId) {
                if(Objects.equals(book.getCondition(), "대여 가능")){
                    flag = 2; // 대여 가능한 상황
                    book.setCondition("대여 중");
                }
                else if (book.getCondition().equals("분실됨") || book.getCondition().equals("도서 정리중")) {
                    flag = 3; // 분실이거나 정리중인 상황이거나 책이 없는 경우
                }
                break; // ID를 찾았으므로 루프 종료
            }
        }

        return flag;
    }

    @Override
    public int returnById(int returnId, List<Book> list) {

        int flag = 1; // -> 대여 중인 상황

        for (Book book : list) {
            if (book.getId() == returnId) {
                if(Objects.equals(book.getCondition(), "대여 중")){
                    flag = 2; // 대여 가능한 상황으로 (반납 가능)
                    book.setCondition("대여 가능");
                }
                else if (book.getCondition().equals("분실됨") || book.getCondition().equals("도서 정리중")) {
                    flag = 3; // 반납 불가능
                }
                break; // ID를 찾았으므로 루프 종료
            }
        }

        saveToCSV(list);

        return flag;
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
