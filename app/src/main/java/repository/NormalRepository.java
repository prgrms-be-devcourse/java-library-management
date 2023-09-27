package repository;

import domain.Book;

import java.io.*;
import java.nio.BufferOverflowException;
import java.util.ArrayList;
import java.util.List;

public class NormalRepository implements Repository {
    File file = new File("C:/데브코스/java-library-management/app/src/main/resources/도서.csv");
    List<Book> books = new ArrayList<>();

    public NormalRepository() throws IOException {
        fileToList(books, file);
    }

    @Override
    public void register(Book book) throws IOException {
        books.add(book);
        updateFile(books, file);
    }

    @Override
    public void printList() {
        books.forEach(this::printBookInfo);
    }

    @Override
    public void search(String titleWord) {
        books.forEach(book -> {
            String title = book.getTitle();
            if(title.contains(titleWord)) {
                    printBookInfo(book);
            }
        });
    }

    @Override
    public void rental(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);

        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서 번호입니다.");
            return;
        }
        switch (selectedBook.getState()) {
            case "대여중" -> System.out.println("[System] 이미 대여중인 도서입니다.");
            case "대여 가능" -> {
                selectedBook.setState("대여중");
                updateFile(books, file);
                System.out.println("[System] 도서가 대여 처리 되었습니다.");
            }
            case "도서 정리중" -> System.out.println("[System] 정리 중인 도서입니다.");
            case "분실됨" -> System.out.println("[System] 분실된 도서입니다.");
        }
    }

    private class ChangeStateThread extends Thread {
        private final Book book;

        public ChangeStateThread(Book book) {
            this.book = book;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(300000);
                book.setState("대여 가능");
                updateFile(books, file);
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            } finally {
                book.setState("대여 가능");
                try {
                    updateFile(books, file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    @Override
    public void returnBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서 번호입니다.");
            return;
        }
        ChangeStateThread thread = new ChangeStateThread(selectedBook);

        if (selectedBook.getState().equals("대여중") || selectedBook.getState().equals("분실됨")) {
            selectedBook.setState("도서 정리중");
            updateFile(books, file);
            thread.setDaemon(true);
            thread.start();

            System.out.println("[System] 도서가 반납 처리 되었습니다.");
        } else if(selectedBook.getState().equals("대여 가능")) {
            System.out.println("[System] 원래 대여가 가능한 도서입니다.");
        } else {
            System.out.println("[System] 반납이 불가능한 도서입니다.");
        }
    }

    @Override
    public void lostBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서 번호입니다.");
            return;
        }

        switch (selectedBook.getState()) {
            case "대여중" -> {
                selectedBook.setState("분실됨");
                updateFile(books, file);
                System.out.println("[System] 도서가 분실 처리 되었습니다.");
            }
            case "대여 가능", "도서 정리중" -> System.out.println("[System] 분실 처리가 불가능한 도서입니다.");
            case "분실됨" -> System.out.println("[System] 이미 분실 처리된 도서입니다.");
        }
    }

    @Override
    public void deleteBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);

        if(selectedBook == null) {
            System.out.println("[System] 존재하지 않는 도서번호 입니다.");
            return;
        } else {
            books.remove(selectedBook);
            updateFile(books, file);
            System.out.println("[System] 도서가 삭제 처리 되었습니다.");
        }
    }

    private void updateFile(List<Book> books, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        books.forEach(book -> {
            try {
                bw.write(String.valueOf(book.getId()) + "," + book.getTitle() + ","
                        + book.getWriter() + "," + String.valueOf(book.getPage()) + "," + book.getState() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bw.close();
    }

    private void fileToList(List<Book> books, File file) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(file));
        String line = "";

        while((line = bf.readLine()) != null) {
            String[] split = line.split(",");
            Book tmpBook = new Book();

            tmpBook.setId(Integer.parseInt(split[0]));
            tmpBook.setTitle(split[1]);
            tmpBook.setWriter(split[2]);
            tmpBook.setPage(Integer.parseInt(split[3]));
            tmpBook.setState(split[4]);

            books.add(tmpBook);
        }
    }
}
