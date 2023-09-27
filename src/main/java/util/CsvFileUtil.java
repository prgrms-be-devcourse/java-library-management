package util;


import model.Book;
import model.Status;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CsvFileUtil {

    private final String FILE_PATH;

    public CsvFileUtil(String FILE_PATH) {
        this.FILE_PATH = FILE_PATH;
    }

    private void createFile() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    public Map<Long, Book> readAllBooksFromCsv() {
        Map<Long, Book> bookMemory = new HashMap<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    Book book = new Book(Long.parseLong(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), Status.findStatusByString(parts[4]));
                    // 정리 상태 도서가 있으면 대여 가능으로 변경
                    if (book.getStatus().equals(Status.ORGANIZING)) {
                        book.toAvailable();
                    }
                    bookMemory.put(Long.parseLong(parts[0]), book);
                }
            } catch (IOException ignore) {
                System.out.println("[System] 잘못된 파일 접근입니다.\n");
            } catch (Exception e) {
                System.out.println("[System] 손상된 파일입니다. 데이터 초기화 후 진행합니다.\n");
            }
        }
        return bookMemory;
    }

    public void writeFile(Map<Long, Book> bookMemory ) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {

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
