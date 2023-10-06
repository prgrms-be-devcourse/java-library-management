package repository;


import domain.Book;

import exception.LoadException;
import exception.SaveException;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

import static domain.BookCondition.*;

public class GeneralRepository implements Repository {

    private static final String CSV_FILE = "csv/csvFile";

    @Override
    public void load(List<Book> bookList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String readRecord;
            while ((readRecord = reader.readLine()) != null) {
                String[] record = readRecord.split(",");

                int id = Integer.parseInt(record[0].trim());
                String title = record[1].trim();
                String author = record[2].trim();
                int page = Integer.parseInt(record[3].trim());
                String condition = record[4].trim();

                // 도서를 가져오던 중 도서 정리중이면 대여 가능으로 변환
                if (Objects.equals(condition, ORGANIZING.getCondition())) {
                    condition = AVAILABLE.getCondition();
                }

                bookList.add(new Book(id, title, author, page, condition));
            }
        } catch (IOException e) {
            throw new LoadException("CSV 파일을 읽어올 수 없습니다");
        }
    }

    @Override
    public void save(int id, String title, String author, int page, List<Book> bookList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE, true))) {
            String newRecord = id + "," + title + "," + author + "," + page + "," + AVAILABLE.getCondition();

            bookList.add(new Book(id, title, author, page, AVAILABLE.getCondition()));

            writer.write(newRecord);
            writer.newLine();
        } catch (IOException e) {
            throw new SaveException("CSV 파일에 저장할 수 없습니다.");
        }
    }

    @Override
    public List<Book> findByTitle(String searchTitle, List<Book> bookList) {
        return bookList.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTitle))
                .map(book -> new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPage(), book.getCondition()))
                .toList();
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
                message = checkRent(book);
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
                message = checkReturn(book);
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
                message = checkLost(book);
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

    //대여 가능한 도서인지 확인하는 메서드
    private static String checkRent(Book book) {
        String message;
        if(Objects.equals(book.getCondition(), AVAILABLE.getCondition())){
            book.setCondition(RENTED.getCondition());
            message = "도서가 대여 처리 되었습니다.";
        }
        else if (Objects.equals(book.getCondition(), RENTED.getCondition())) {
            message = "이미 대여중인 도서입니다.";
        }
        else message = "현재 대여가 불가능한 도서입니다.";
        return message;
    }

    //반납 가능한 도서인지 확인하는 메서드
    private static String checkReturn(Book book) {
        String message;
        if(Objects.equals(book.getCondition(), RENTED.getCondition()) || Objects.equals(book.getCondition(), LOST.getCondition())) { //대여 중 or 분실됨이면 반납 가능
            book.setCondition(ORGANIZING.getCondition());
            message = "도서가 반납 처리 되었습니다";
        } else { // 대여 가능
            message = "원래 대여가 가능한 도서입니다.";
        }
        return message;
    }

    //분실 처리 도서인지 확인하는 메서드
    private static String checkLost(Book book) {
        String message;
        if(Objects.equals(book.getCondition(), LOST.getCondition())) {
            message = "이미 분실 처리된 도서입니다.";
        } else {
            book.setCondition(LOST.getCondition());
            message = "도서가 분실 처리 되었습니다.";
        }
        return message;
    }

    // 변경된 점을 CSV파일에 저장하는 코드(덮어쓰기)
    private static void saveToCSV(List<Book> bookList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CSV_FILE))) {
            for (Book book : bookList) {
                String record = book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPage() + "," + book.getCondition();

                writer.write(record);
                writer.newLine();
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
