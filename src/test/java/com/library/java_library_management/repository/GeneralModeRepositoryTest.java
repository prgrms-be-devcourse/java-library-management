package com.library.java_library_management.repository;

import com.library.java_library_management.dto.BookInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

class GeneralModeRepositoryTest {
    Repository repository = new GeneralModeRepository();
    @Test
    public void registerBook() throws IOException {

        repository.registerBook("test1", "Injun", 456);
        List<BookInfo> totalBook = repository.getTotalBook();
        Assertions.assertEquals(totalBook.size(), 1);
    }

    @Test
    public

}