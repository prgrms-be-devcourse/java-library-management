package devcourse.backend.medel;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileRepository {
    private final Path FILEPATH;
    private String firstLine;

    public FileRepository(String path) {
        this.FILEPATH = Paths.get(Objects.requireNonNull(path));
    }

    public List<Book> loadBooks() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(FILEPATH.toFile()));

        List<Book> books = new ArrayList<>();
        firstLine = reader.readLine();
        reader.lines()
                .map(s -> s.split("[;,]"))
                .forEach(data -> {
                    Book.Builder builder = new Book.Builder(data[1], data[2], Integer.parseInt(data[3]));
                    if(data[0].equals("")) books.add(builder.build());
                    else books.add(builder.id(Long.valueOf(data[0]))
                            .bookStatus(data[4])
                            .build());
                });

        return books;
    }
}
