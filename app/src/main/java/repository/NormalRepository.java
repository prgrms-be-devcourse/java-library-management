package repository;

import domain.BookState;
import message.ExecuteMessage;
import thread.NormalChangeStateThread;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static repository.Book.countId;

public class NormalRepository implements Repository {
    File file = new File("src/main/resources/library.csv");
    List<Book> books = new ArrayList<>();

    public NormalRepository() throws IOException {
        fileToList(books, file);
        countId = books.get(books.size() - 1).getId() + 1;
        organizeState(books);
        updateFile(books, file);
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
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        }
        switch (selectedBook.getState()) {
            case RENTING -> System.out.println(ExecuteMessage.RENTAL_RENTING);
            case AVAILABLE -> {
                selectedBook.setState(BookState.RENTING);
                updateFile(books, file);
                System.out.println(ExecuteMessage.RENTAL_AVAILABLE);
            }
            case ORGANIZING -> System.out.println(ExecuteMessage.RENTAL_ORGANIZING);
            case LOST -> System.out.println(ExecuteMessage.RENTAL_LOST);
        }
    }

    @Override
    public void returnBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        }
        NormalChangeStateThread thread = new NormalChangeStateThread(selectedBook, file, books);

        if (selectedBook.getState() == BookState.RENTING || selectedBook.getState() == BookState.LOST) {
            selectedBook.setState(BookState.ORGANIZING);
            updateFile(books, file);
            thread.setDaemon(true);
            thread.start();

            System.out.println(ExecuteMessage.RETURN_COMPLETE);
        } else if(selectedBook.getState() == BookState.AVAILABLE) {
            System.out.println(ExecuteMessage.RETURN_AVAILABLE);
        } else {
            System.out.println(ExecuteMessage.RETURN_IMPOSSIBLE);
        }
    }

    @Override
    public void lostBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        }

        switch (selectedBook.getState()) {
            case RENTING -> {
                selectedBook.setState(BookState.LOST);
                updateFile(books, file);
                System.out.println(ExecuteMessage.LOST_COMPLETE);
            }
            case AVAILABLE, ORGANIZING -> System.out.println(ExecuteMessage.LOST_IMPOSSIBLE);
            case LOST -> System.out.println(ExecuteMessage.LOST_ALREADY);
        }
    }

    @Override
    public void deleteBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);

        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST);
            return;
        } else {
            books.remove(selectedBook);
            updateFile(books, file);
            System.out.println(ExecuteMessage.DELETE_COMPLETE);
        }
    }

    public static void updateFile(List<Book> books, File file) throws IOException {
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
            tmpBook.setState(BookState.valueOf(split[4]));

            books.add(tmpBook);
        }
    }
}
