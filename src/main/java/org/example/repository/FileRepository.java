package org.example.repository;

import org.example.domain.Book;
import org.example.domain.BookStatusType;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class FileRepository implements Repository {
    private Map<Integer, Book> books;
    private final File bookInfoCsv;

    public FileRepository(String bookInfoCSVPath) {
        bookInfoCsv = new File(bookInfoCSVPath);
        prepareCsv();
        books = getAllBooksFromCSV();
    }

    @Override
    public void saveBook(Book book) {
        books.put(book.getId(), book);
        updateBooksInfoOnCsv();
    }

    @Override
    public List<Book> findAllBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findBookById(Integer bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    @Override
    public void updateBookStatus(Integer bookId, BookStatusType status) {
        Book bookToUpdate = books.get(bookId);
        bookToUpdate.setStatus(status);
        updateBooksInfoOnCsv();
    }

    @Override
    public void deleteBookById(Integer bookId) {
        books.remove(bookId);
        updateBooksInfoOnCsv();
    }

    @Override
    public Integer getNextBookId() {
        return books.keySet().stream().max(Integer::compareTo).orElse(1);
    }

    private void prepareCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookInfoCsv, true))) {
            if (bookInfoCsv.length() == 0) {
                bw.write("id, title, author, pageSize, status, returnTime");
                bw.newLine();
            }
        } catch (IOException ioException) {
            System.out.println("파일 쓰기 에러");
        }
    }

    private Map<Integer, Book> getAllBooksFromCSV() {
        Map<Integer, Book> books = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(bookInfoCsv))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] bookInfo = line.split(", ");
                Integer id = Integer.parseInt(bookInfo[0]);
                String title = bookInfo[1];
                String author = bookInfo[2];
                Integer pageSize = Integer.parseInt(bookInfo[3]);
                BookStatusType status = BookStatusType.getValueByName(bookInfo[4]);

                Book book = new Book(id, title, author, pageSize, status);
                books.put(id, book);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 에러");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return books;
    }

    private void updateBooksInfoOnCsv() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookInfoCsv))) {
            bw.write("id, title, author, pageSize, status");
            bw.newLine();
            for (Book book : new ArrayList<>(this.books.values())) {
                bw.write(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + ", " + book.getPageSize() + ", " + book.getStatus().getName());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일 쓰기 에러");
        }
    }
}
