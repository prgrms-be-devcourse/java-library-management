package repository;

import domain.Book;
import domain.BookState;
import exception.FileWriteException;
import thread.FileChangeStateThread;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static domain.Reader.fc;
import static domain.Book.countId;

public class FileRepository implements Repository {
    private static File file = new File("src/main/resources/library.csv");
    private static List<Book> books = new ArrayList<>();

    public FileRepository() {
        fileToList();
        countId = books.get(books.size() - 1).getId() + 1;
        organizeState(books);
        updateFile();
    }

    @Override
    public void register(Book book) {
        books.add(book);
        updateFile();
    }

    @Override
    public List<Book> getList() { return books; }

    @Override
    public List<Book> search(String titleWord) {
        List<Book> selectedBooks = new ArrayList<>();
        books.forEach(book -> {
            if(book.getTitle().contains(titleWord)) {
                selectedBooks.add(book);
                updateFile();
            }
        });
        return selectedBooks;
    }

    @Override
    public BookState rental(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            BookState state = book.getState();

            if (state == BookState.AVAILABLE) {
                book.changeStateToRenting();
                updateFile();
            }
            return state;
        }
        return null;
    }

    @Override
    public BookState returnBook(int id, int time) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();

        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            BookState state = book.getState();
            Thread thread = new FileChangeStateThread(book, time);
            if (state == BookState.RENTING || state == BookState.LOST) {
                book.changeStateToOrganizing();
                updateFile();
                thread.setDaemon(true);
                thread.start();
            }
            return state;
        }
        return null;
    }

    @Override
    public BookState lostBook(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            BookState state = book.getState();

            if (state == BookState.RENTING) {
                book.changeStateToLost();
                updateFile();
            }
            return state;
        }
        return null;
    }

    @Override
    public boolean deleteBook(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        if(selectedBookOptional.isPresent()) {
            Book book = selectedBookOptional.get();
            books.remove(book);
            return true;
        }
        return false;
    }

    public static void updateFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            books.forEach(book -> {
                try {
                    bw.write(book.fileLine());
                } catch (IOException e) {
                    throw new FileWriteException();
                }
            });
            bw.close();
        } catch (IOException e) {
            throw new FileWriteException();
        }
    }

    private void fileToList() {
        String line = "";

        while(fc.hasNextLine()) {
            line = fc.nextLine();
            String[] split = line.split(",");
            Book tmpBook = new Book(Integer.parseInt(split[0]), split[1],
                    split[2], Integer.parseInt(split[3]), BookState.valueOfState(split[4]));

            books.add(tmpBook);
        }
    }
}
