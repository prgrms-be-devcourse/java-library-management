package org.example.service;

import org.example.domain.Book;
import org.example.domain.BookState;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private File csv;
    private final String filePath;
    BufferedReader br = null;

    public FileService() throws FileNotFoundException {
        filePath = "src/main/resources/book.csv";
        csv = new File(filePath);
    }

    public List<Book> readFile() {
        List<Book> bookList = new ArrayList<>();
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csv));
            int id = 1;
            while ((line = br.readLine()) != null) {
                String[] lineArr = line.split(",");
                Book book = new Book(id++, lineArr[1], lineArr[2],
                        Integer.parseInt(lineArr[3]), BookState.valueOf(lineArr[4]));
                bookList.add(book);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return bookList;
    }

    public void writeFile(Book book) throws IOException {
        csv = new File(filePath);
        try (FileWriter fileWriter = new FileWriter(filePath, true);) {
            String data = "";
            data = book.toString();
            fileWriter.append(data).append("\n");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFiles(List<Book> bookList) {
        csv = new File(filePath);
        bookList.forEach(book -> {
            try {
                writeFile(book);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void deleteFile() {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
