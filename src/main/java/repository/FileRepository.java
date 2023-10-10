package repository;

import domain.Book;
import domain.BookState;
import exception.FileWriteException;
import thread.FileChangeStateThread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static domain.Book.countId;
import static view.Reader.fc;

public class FileRepository implements Repository {
    private static File file = new File("src/main/resources/library.csv");
    private static List<Book> books = new ArrayList<>();

    public FileRepository() {
        fileToList();
        if(books.isEmpty()) countId = 1;
        else countId = books.get(books.size() - 1).getId() + 1;
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
        return books.stream().filter(book -> {
            return book.containsTitleWord(titleWord);
        }).toList();
    }

    @Override
    public BookState rental(int id) {
        Optional<Book> selectedBookOptional = findById(id);
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
        Optional<Book> selectedBookOptional = findById(id);

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
        Optional<Book> selectedBookOptional = findById(id);
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

    private static Optional<Book> findById(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();
        return selectedBookOptional;
    }

    @Override
    public boolean deleteBook(int id) {
        Optional<Book> selectedBookOptional = findById(id);
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
