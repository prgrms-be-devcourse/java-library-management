package repository;

import constant.Guide;
import model.Book;
import model.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository implements Repository {

    private final String FILE_PATH = "book.csv";
    private List<Book> bookList = new ArrayList<>();

    public FileRepository() {
        this.bookList = readAllBooksFromCsv();
    }

    // 책 등록
    @Override
    public void saveBook(Book book) {
        File csv = new File(FILE_PATH);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csv, true))) {
            String csvLine = String.format("%d,%s,%s,%d,%s",
                    book.getBookNo(), book.getTitle(), book.getAuthor(), book.getPageNum(), book.getStatus().getStatus());
            writer.write(csvLine);
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 모든 book 반환
    @Override
    public List<Book> findAllBook() {
        return bookList;
    }

    // title에 특정 문자가 포함된 Book 객체를 모두 찾는 메서드
    @Override
    public List<Book> findBookByTitle(String searchTitle) {
        return bookList.stream()
                .filter(book -> book.getTitle().contains(searchTitle))
                .collect(Collectors.toList());
    }

    // 책 대여
    @Override
    public void borrowBook(Long bookNo) {
        Optional<Book> foundBook = bookList.stream()
                .filter(book -> book.getBookNo().equals(bookNo))
                .findFirst();
        if (foundBook.isPresent()) {
            Book book = foundBook.get();

            if (Status.isBorrowed(book.getStatus())) {
                System.out.println(Guide.BORROW_FAIL_BORROWED.getGuide());
            } else if (Status.isLost(book.getStatus())) {
                System.out.println(Guide.BORROW_FAIL_LOST.getGuide());
            } else if (Status.isOrganizing(book.getStatus())) {
                System.out.println(Guide.BORROW_FAIL_ORGANIZING.getGuide());
            } else {
                bookList.stream()
                        .filter(targetBook -> targetBook.getBookNo().equals(bookNo))
                        .findFirst()
                        .ifPresent(targetBook -> targetBook.changeStatus(Status.BORROWED));
                updateCvsFile();
                System.out.println(Guide.BORROW_COMPLETE.getGuide());
            }
        }else {
            System.out.println("해당하는 책이 없습니다.");
        }
    }

    public void updateCvsFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : bookList) {
                String csvLine = String.format("%d,%s,%s,%d,%s",
                        book.getBookNo(), book.getTitle(), book.getAuthor(), book.getPageNum(), book.getStatus().getStatus());
                writer.write(csvLine);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Book> readAllBooksFromCsv() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                bookList.add(new Book(Long.parseLong(parts[0]), parts[1], parts[2], Integer.parseInt(parts[3]), Status.findStatusByString(parts[4])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }
}