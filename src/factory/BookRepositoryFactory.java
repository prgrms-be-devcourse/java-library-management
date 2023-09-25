package factory;

import repository.BookRepository;
import repository.FileBookRepository;
import repository.MemBookRepository;

import java.util.HashMap;
import java.util.Map;

public class BookRepositoryFactory {
    private static final Map<Integer, BookRepository> repositories = new HashMap<>();

    static {
        repositories.put(0, new FileBookRepository());
        repositories.put(1, new MemBookRepository());
    }

    public static BookRepository getBookRepository(int mode) {
        return repositories.get(mode);
    }
}
