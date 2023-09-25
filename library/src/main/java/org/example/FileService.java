package org.example;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileService {
    private File csv = new File("/Users/eugene/Documents/GitHub/java-library-management/library/src/main/resources/books.csv");
    BufferedReader br = null;
    BufferedWriter bw = null;

    public FileService() throws FileNotFoundException {
    }

    public List<Book> readFile() {
        List<Book> bookList = new ArrayList<>();
        String line = "";
        try{
            br = new BufferedReader(new FileReader(csv));
            while((line = br.readLine()) != null) {
                String[] lineArr = line.split(",");
                Book book = new Book(Integer.parseInt(lineArr[0]), lineArr[1],lineArr[2],
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
        bw = new BufferedWriter(new FileWriter(csv, true));

        String data = "";
        data = book.toString();
        bw.write(data);
        bw.newLine();

        bw.flush();
    }

}
