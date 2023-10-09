package com.programmers.library.utils;

import com.programmers.library.domain.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class CSVFileHandler {
    private static final String resourceName = "library.csv";
    private static final String filePath = System.getProperty("user.dir") + File.separator + resourceName;   // 파일 경로

    public List<Book> readBooksFromCSV() {  // 파일 읽기
        List<Book> books = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length == 5) {
                    int bookId = Integer.parseInt(data[0]);
                    String title = data[1];
                    String author = data[2];
                    int pages = Integer.parseInt(data[3]);
                    StatusType status = StatusType.getStatus(data[4]);

                    Book book = new Book(bookId, title, author, pages, status);
                    books.add(book);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return books;
    }

    private String booksToString(Book book) {
        String bts = book.getBookId() + "," +
                book.getTitle() + "," +
                book.getAuthor() + "," +
                book.getPages() + "," +
                book.getStatus().getDescription();
        return bts;
    }

    public void writeBooksToCSV(List<Book> books) {     // 파일에 쓰기
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Book book :books) {
                String csvLine = booksToString(book);
                bw.write(csvLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void appendBookToCSV(Book book) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            String csvLine = booksToString(book);
            bw.write(csvLine);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCSV() {
        try (PrintWriter writer = new PrintWriter(filePath)) {
            writer.print("");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}