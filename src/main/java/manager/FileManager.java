package manager;

import domain.Book;
import domain.BookStatus;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private String path = System.getProperty("user.dir");

    public FileManager(String path) {
        this.path += path;
    }

    // file -> list
    public List<Book> loadData() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                LocalDateTime returnTime = null;
                if (!data[5].equals("null"))
                    returnTime = LocalDateTime.parse(data[5]);

                Book book = Book.builder()
                        .id(Integer.valueOf(data[0]))
                        .title(data[1])
                        .author(data[2])
                        .page(Integer.parseInt(data[3]))
                        .status(BookStatus.valueOf(data[4]))
                        .returnTime(returnTime)
                        .build();
                books.add(book);
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
        return books;
    }

    // list -> file
    public void updateFile(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (Book book : books) {
                bw.write(book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPage() + "," + book.getStatus() + "," + book.getReturnTime());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
