package repository;

import domain.BookState;
import message.ExecuteMessage;
import thread.NormalChangeStateThread;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static domain.Reader.sc;
import static repository.Book.countId;

public class FileRepository implements Repository {
    File file = new File("src/main/resources/library.csv");
    List<Book> books = new ArrayList<>();

    public FileRepository() throws IOException {
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
        books.forEach(book -> System.out.println(book.toString()));
    }

    @Override
    public void search(String titleWord) {
        books.forEach(book -> {
            String title = book.getTitle();
            if(title.contains(titleWord)) {
                System.out.println(book.toString());
            }
        });
    }

    @Override
    public void rental(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);

        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
            return;
        }
        switch (selectedBook.getState()) {
            case RENTING -> System.out.println(ExecuteMessage.RENTAL_RENTING.getMessage());
            case AVAILABLE -> {
                selectedBook.setState(BookState.RENTING);
                updateFile(books, file);
                System.out.println(ExecuteMessage.RENTAL_AVAILABLE.getMessage());
            }
            case ORGANIZING -> System.out.println(ExecuteMessage.RENTAL_ORGANIZING.getMessage());
            case LOST -> System.out.println(ExecuteMessage.RENTAL_LOST.getMessage());
        }
    }

    @Override
    public void returnBook(int id) {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
            return;
        }
        NormalChangeStateThread thread = new NormalChangeStateThread(selectedBook, file, books);

        if (selectedBook.getState() == BookState.RENTING || selectedBook.getState() == BookState.LOST) {
            selectedBook.setState(BookState.ORGANIZING);
            updateFile(books, file);
            thread.setDaemon(true);
            thread.start();

            System.out.println(ExecuteMessage.RETURN_COMPLETE.getMessage());
        } else if(selectedBook.getState() == BookState.AVAILABLE) {
            System.out.println(ExecuteMessage.RETURN_AVAILABLE.getMessage());
        } else {
            System.out.println(ExecuteMessage.RETURN_IMPOSSIBLE.getMessage());
        }
    }

    @Override
    public void lostBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);
        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
            return;
        }

        switch (selectedBook.getState()) {
            case RENTING -> {
                selectedBook.setState(BookState.LOST);
                updateFile(books, file);
                System.out.println(ExecuteMessage.LOST_COMPLETE.getMessage());
            }
            case AVAILABLE, ORGANIZING -> System.out.println(ExecuteMessage.LOST_IMPOSSIBLE.getMessage());
            case LOST -> System.out.println(ExecuteMessage.LOST_ALREADY.getMessage());
        }
    }

    @Override
    public void deleteBook(int id) throws IOException {
        Book selectedBook = books.stream().filter(book -> book.getId() == id)
                .findAny()
                .orElse(null);

        if(selectedBook == null) {
            System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
            return;
        } else {
            books.remove(selectedBook);
            updateFile(books, file);
            System.out.println(ExecuteMessage.DELETE_COMPLETE.getMessage());
        }
    }

    public static void updateFile(List<Book> books, File file) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        books.forEach(book -> {
            try {
                bw.write(book.fileLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        bw.close();
    }

    private void fileToList(List<Book> books, File file) throws IOException {
        String line = "";

        while((line = sc.nextLine()) != null) {
            String[] split = line.split(",");
            Book tmpBook = new Book(Integer.parseInt(split[0]), split[1],
                    split[2], Integer.parseInt(split[3]), BookState.valueOfState(split[4]));

            books.add(tmpBook);
        }
    }
}
