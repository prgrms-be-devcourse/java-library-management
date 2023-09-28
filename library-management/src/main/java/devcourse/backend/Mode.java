package devcourse.backend;

import devcourse.backend.repository.FileRepository;
import devcourse.backend.repository.MemoryRepository;
import devcourse.backend.repository.Repository;

import java.util.Arrays;
import java.util.Optional;

public enum Mode {
    NORMAL(1, "일반 모드", new FileRepository("src/main/resources/도서 목록.csv")),
    TEST(2, "테스트 모드", new MemoryRepository());

    private final int num;
    private final String description;
    private Repository repository;

    Mode(int num, String description, Repository repository) {
        this.num = num;
        this.description = description;
        this.repository = repository;
    }

    @Override
    public String toString() {
        return num + ". " + description;
    }

    public static Repository getRepository(int num) {
        return get(num).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 모드입니다."))
                .repository;
    }
    private static Optional<Mode> get(int num) {
        return Arrays.stream(Mode.values())
                .filter(e -> e.num == num)
                .findAny();
    }
}
