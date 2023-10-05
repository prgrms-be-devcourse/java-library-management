package manage.file;

import domain.Book;

import java.util.HashMap;

public class TestBookFileManager implements BookFileManager {
    @Override
    public HashMap<Integer, Book> read() {
        return new HashMap<>();
    }

    @Override
    public void write(HashMap<Integer, Book> bookList) {
        return;
    }
}
