package manage.file;

import entity.Book;

import java.util.ArrayList;
import java.util.List;

public class TestFileManager implements FileManager {
    @Override
    public List<Book> read() {
        return new ArrayList<>();
    }

    @Override
    public void write(List<Book> bookList) {
        return;
    }
}
