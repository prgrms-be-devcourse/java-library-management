package util;


import model.Book;
import model.Status;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CsvFileUtil {

    private final String filePath;

    public CsvFileUtil(String filePath) {
        this.filePath = filePath;
    }

    public Map<Long, Book> readAllBooksFromCsv() {
        Map<Long, Book> bookMemory = new HashMap<>();
        File file = new File(filePath);
        if (file.exists()) {
            return readAllBooksFromCsv(bookMemory);
        }
        return bookMemory;
    }

    private Map<Long, Book> readAllBooksFromCsv(Map<Long, Book> bookMemory) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Book book = new Book(Long.parseLong(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]));
                book.changeStatus(Status.findStatusByString(parts[4]));
                // 정리 상태 도서가 있으면 대여 가능으로 변경
                if (book.getStatus().equals(Status.ORGANIZING)) {
                    book.toAvailable();
                }
                bookMemory.put(Long.parseLong(parts[0]), book);
            }
        } catch (IOException ignore) {
            System.out.println("[System] 잘못된 파일 접근입니다." + System.lineSeparator());
        } catch (Exception e) {
            System.out.println("[System] 손상된 파일입니다. 데이터 초기화 후 진행합니다." + System.lineSeparator());
        }
        return bookMemory;
    }

    public void writeFile(Map<Long, Book> bookMemory) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            for (Book book : bookMemory.values()) {
                String csvLine = String.format("%d,%s,%s,%d,%s",
                        book.getBookNo(), book.getTitle(), book.getAuthor(), book.getPageNum(), book.getStatus().getStatus());
                writer.write(csvLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
