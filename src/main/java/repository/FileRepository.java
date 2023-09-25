package repository;

import model.Book;
import model.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements Repository {

    private final String FILE_PATH = "book.csv";

    @Override
    public void saveBook(Book book) {
        File csv = new File(FILE_PATH);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv, true))) {
            String csvLine = String.format("%d,%s,%s,%d,%s",
                    book.getBookNo(), book.getTitle(), book.getAuthor(), book.getPageNum(), book.getStatus().getStatus());
            writer.write(csvLine);
            writer.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Failed to save book: " + e.getMessage(), e);
        }
    }

    // CSV 파일에서 모든 Book 객체를 찾는 메서드
    @Override
    public List<Book> findAllBook() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Long bookNo = Long.parseLong(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    int pageNum = Integer.parseInt(parts[3]);
                    Status status = Status.findStatusByString(parts[4]);
                    books.add(new Book(bookNo, title, author, pageNum, status));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    // CSV 파일에서 특정 bookNo에 해당하는 Book 객체를 찾는 메서드
    @Override
    public List<Book> findBookByTitle(String searchTitle) {
        List<Book> foundBooks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Long bookNo = Long.parseLong(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    int pageNum = Integer.parseInt(parts[3]);
                    Status status = Status.findStatusByString(parts[4]);

                    // 제목(title)에 입력한 문자열이 포함되어 있는 경우 해당 책을 추가
                    if (title.contains(searchTitle)) {
                        foundBooks.add(new Book(bookNo, title, author, pageNum, status));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foundBooks;
    }
}