package util;


import model.Book;
import model.Status;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CsvFileUtil {

    private final String FILE_PATH = "book.csv";

    public CsvFileUtil() throws IOException {
        createFile();
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
                    bookMemory.put(Long.parseLong(parts[0]), new Book(Long.parseLong(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), Status.findStatusByString(parts[4])));
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
