package repository;

import domain.BookState;
import exception.FileWriteException;
import message.ExecuteMessage;
import view.FileConsolePrint;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static domain.Reader.fc;
import static repository.Book.countId;

public class FileRepository implements Repository {
    public static File file = new File("src/main/resources/library.csv");
    public static List<Book> books = new ArrayList<>();

    public FileRepository() {
        fileToList(books, file);
        countId = books.get(books.size() - 1).getId() + 1;
        organizeState(books);
        updateFile(books, file);
    }

    @Override
    public void register(Book book) {
        books.add(book);
        updateFile(books, file);
    }

    @Override
    public void printList() {
        FileConsolePrint.printListView();
    }

    @Override
    public void search(String titleWord) {
        books.forEach(book -> {
            FileConsolePrint.searchView(titleWord, book);
        });
    }

    @Override
    public void rental(int id) {
        Optional<Book> selectedBookOptional = books.stream()
                .filter(book -> book.isSameId(id))
                .findFirst();

        selectedBookOptional.ifPresentOrElse(
                FileConsolePrint::rentalView,
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

    @Override
    public void returnBook(int id) {
        Optional<Book> selectedBookOptional = books.stream().filter(book -> book.isSameId(id))
                .findFirst();
        selectedBookOptional.ifPresentOrElse(
                FileConsolePrint::returnView,
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

    @Override
    public void lostBook(int id) {
        Optional<Book> selectedBookOptional = books.stream().filter(book -> book.isSameId(id))
                .findFirst();
        selectedBookOptional.ifPresentOrElse(
                FileConsolePrint::lostView,
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

    @Override
    public void deleteBook(int id) {
        Optional<Book> selectedBookOptional = books.stream().filter(book -> book.isSameId(id))
                .findFirst();
        selectedBookOptional.ifPresentOrElse(
                FileConsolePrint::deleteView,
                () -> {
                    System.out.println(ExecuteMessage.NOT_EXIST.getMessage());
                }
        );
    }

    public static void updateFile(List<Book> books, File file) {
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

    private void fileToList(List<Book> books, File file) {
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
