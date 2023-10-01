package org.example.repository;

import org.example.domain.Book;
import org.example.domain.BookStatusType;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository implements Repository {
    private final File bookInfoCsv;

    public FileRepository(String bookInfoCSVPath) {
        bookInfoCsv = new File(bookInfoCSVPath);
        prepareCsv();
    }

    @Override
    public void saveBook(Book book) {
        writeBookInfoOnCSV(book);
    }

    @Override
    public List<Book> findAllBooks() {
        return getAllBooksFromCSV();
    }

    @Override
    public List<Book> findBookByTitle(String title) {
        List<Book> books = getAllBooksFromCSV();
        return books.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findBookById(Integer bookId) {
        List<Book> books = getAllBooksFromCSV();
        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
    }

    @Override
    public void updateBookStatus(Integer bookId, BookStatusType status) {
        List<Book> updatedBooks = getAllBooksFromCSV().stream()
                .map(book -> {
                    if (book.getId().equals(bookId)) {
                        book.setStatus(status);
                        if (BookStatusType.ORGANIZING == status)
                            book.setReturnTime(LocalDateTime.now());
                        else
                            book.setReturnTime(null);
                    }
                    return book;
                }).collect(Collectors.toList());
        updateBooksInfoOnCsv(updatedBooks);
    }

    @Override
    public void deleteBookById(Integer bookId) {
        List<Book> books = getAllBooksFromCSV();
        books.removeIf(book -> book.getId().equals(bookId));
        updateBooksInfoOnCsv(books);
    }

    @Override
    public Integer getNextBookId() {
        Integer lastBookId = 0;

        List<Book> books = getAllBooksFromCSV();
        if (books.size() > 0) lastBookId = books.get(books.size() - 1).getId();

        return lastBookId + 1;
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

    private List<Book> getAllBooksFromCSV() {
        List<Book> books = new ArrayList<>();

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
                LocalDateTime returnTime = null;
                if (!bookInfo[5].equals("null")) {
                    returnTime = LocalDateTime.parse(bookInfo[5]);
                }

                Book book = new Book(id, title, author, pageSize, status, returnTime);
                books.add(book);
            }
        } catch (IOException e) {
            System.out.println("파일 읽기 에러");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        return books;
    }

    private void updateBooksInfoOnCsv(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookInfoCsv))) {
            bw.write("id, title, author, pageSize, status, returnTime");
            bw.newLine();
            for (Book book : books) {
                bw.write(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + ", " + book.getPageSize() + ", " + book.getStatus().getName() + ", " + book.getReturnTime());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("파일 쓰기 에러");
        }
    }

    private void writeBookInfoOnCSV(Book book) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(bookInfoCsv, true))) {
            bw.write(book.getId() + ", " + book.getTitle() + ", " + book.getAuthor() + ", " + book.getPageSize() + ", " + book.getStatus().getName() + ", " + book.getReturnTime());
            bw.newLine();
        } catch (IOException ioException) {
            System.out.println("파일 쓰기 에러");
        }
    }
}
