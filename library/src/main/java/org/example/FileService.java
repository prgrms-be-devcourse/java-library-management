package org.example;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    private final File csv;
    private final String filePath;
    //private final BookService bookService;
    BufferedReader br = null;
    BufferedWriter bw = null;

    public FileService() throws FileNotFoundException {
        filePath = "src/main/resources/book.csv";
        csv = new File(filePath);
    }

    public List<Book> readFile() {
        List<Book> bookList = new ArrayList<>();
        String line = "";
        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) {
                String[] lineArr = line.split(",");
                Book book = new Book(Integer.parseInt(lineArr[0]), lineArr[1], lineArr[2],
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
        try (FileWriter csvFileWriter = new FileWriter(filePath)) {
            String data = "";
            data = book.toString();
            csvFileWriter.append(data);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeFiles(List<Book> bookList) throws IOException {
        bookList.forEach(book -> {
            try {
                writeFile(book);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
