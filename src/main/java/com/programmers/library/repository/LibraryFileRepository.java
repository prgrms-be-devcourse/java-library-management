package com.programmers.library.repository;

import com.programmers.library.domain.Book;
import com.programmers.library.utils.BookStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryFileRepository implements LibraryRepository {
    private static List<Book> books = new ArrayList<>();
    private static int sequence = 0;

    private static String resourceName = "\\library.csv";
    private static String filePath = System.getProperty("user.dir") + resourceName; // 파일 경로

    public LibraryFileRepository() {
        readBooksFromCSV();
    }

    void readBooksFromCSV() {   // CSV 파일 읽기
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();  // 헤더

            while((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data.length == 4) {
                    int bookId = ++sequence;
                    String title = data[0];
                    String author = data[1];
                    int pages = Integer.parseInt(data[2]);
                    BookStatus status = BookStatus.getStatus(data[3]);

                    Book book = new Book(bookId, title, author, pages, status);
                    books.add(book);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int save(Book book) {
        book.setBookId(++sequence);
        books.add(book);
        return book.getBookId();
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public List<Book> findByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findById(int bookId) {
        return books.stream()
                .filter(book -> book.getBookId() == bookId)
                .findFirst();
    }


    @Override
    public void delete(int bookId) {
        books.removeIf(book -> book.getBookId() == bookId);
    }

    String booksToString(Book book) {
        StringBuilder sb = new StringBuilder();
        sb.append(book.getTitle()).append(",");
        sb.append(book.getAuthor()).append(",");
        sb.append(book.getPages()).append(",");
        sb.append(book.getStatus().getDescription());
        return sb.toString();
    }

    @Override
    public void saveAll() { // CSV 파일 덮어쓰기 (= writeBooksToCSV)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            bw.write("title, author, pages, status");   // 헤더
            bw.newLine();

            for (Book book :books) {
                String csvLine = booksToString(book);
                bw.write(csvLine);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
