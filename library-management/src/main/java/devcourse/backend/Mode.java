package devcourse.backend;

import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.MemoryRepository;
import devcourse.backend.repository.Repository;
import devcourse.backend.view.Menu;

import java.util.Arrays;
import java.util.Optional;

public enum Mode {
    NORMAL(1, new FileRepository("src/main/resources/도서 목록.csv")),
    TEST(2, new MemoryRepository());

    private final int num;
    private Repository repository;

    Mode(int num, Repository repository) {
        this.num = num;
        this.repository = repository;
    }

    public static Repository getRepository(int num) {
        return get(num).orElseThrow().repository;
    }
    private static Optional<Mode> get(int num) {
        return Arrays.stream(Mode.values())
                .filter(e -> e.num == num)
                .findAny();
    }
}
