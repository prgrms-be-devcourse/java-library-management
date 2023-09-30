package manage.file;

import entity.Book;

import java.util.HashMap;

public class TestFileManager implements FileManager {
    @Override
    public HashMap<Integer, Book> read() {
        return new HashMap<>();
    }

    @Override
    public void write(HashMap<Integer, Book> bookList) {
        return;
    }
}
