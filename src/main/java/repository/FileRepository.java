package repository;

import model.Book;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}