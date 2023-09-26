package repository;

import constant.Guide;
import model.Book;
import model.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements Repository {

    private final String FILE_PATH = "book.csv";
    private final String TEMP_FILE = "temp.csv";

    public FileRepository() {
    }

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

    // CSV 파일에서 모든 Book 객체를 찾는 메서드
    @Override
    public List<Book> findAllBook() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Long bookNo = Long.parseLong(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    int pageNum = Integer.parseInt(parts[3]);
                    Status status = Status.findStatusByString(parts[4]);
                    books.add(new Book(bookNo, title, author, pageNum, status));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    // CSV 파일에서 특정 bookNo에 해당하는 Book 객체를 찾는 메서드
    @Override
    public List<Book> findBookByTitle(String searchTitle) {
        List<Book> foundBooks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    Long bookNo = Long.parseLong(parts[0]);
                    String title = parts[1];
                    String author = parts[2];
                    int pageNum = Integer.parseInt(parts[3]);
                    Status status = Status.findStatusByString(parts[4]);

                    // 제목(title)에 입력한 문자열이 포함되어 있는 경우 해당 책을 추가
                    if (title.contains(searchTitle)) {
                        foundBooks.add(new Book(bookNo, title, author, pageNum, status));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foundBooks;
    }

    @Override
    public void borrowBook(Long bookNo) {
        boolean found = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_FILE))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0 && parts[0].equals(bookNo.toString())) {
                    if (parts[0].equals(bookNo.toString())) {
                        if (Status.isBorrowed(parts[4])) {
                            System.out.println(Guide.BORROW_FAIL_BORROWED.getGuide());
                            break;
                        } else if (Status.isLost(parts[4])) {
                            System.out.println(Guide.BORROW_FAIL_LOST.getGuide());
                            break;
                        } else if (Status.isOrganizing(parts[4])) {
                            System.out.println(Guide.BORROW_FAIL_ORGANIZING.getGuide());
                            break;
                        } else {
                            parts[4] = Status.BORROWED.getStatus();
                            found = true;
                        }
                    }
                }
                // 수정된 행을 다시 CSV 형식의 문자열 변환
                StringBuilder modifiedLine = new StringBuilder();
                for (int i = 0; i < parts.length; i++) {
                    modifiedLine.append(parts[i]);
                    if (i < parts.length - 1) {
                        modifiedLine.append(",");
                    }
                }
                // 수정된 행을 출력 파일에 작성
                writer.write(modifiedLine.toString());
                writer.write("\n"); // 각 행을 줄 바꿈으로 구분
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!found) {
            deleteFile(TEMP_FILE);
            return;
        }
        deleteFile(FILE_PATH);
        renameFile();
        System.out.println(Guide.BORROW_COMPLETE);
    }

    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private void renameFile() {
        File file = new File(TEMP_FILE);
        boolean renameTo = file.renameTo(new File(FILE_PATH));
    }
}