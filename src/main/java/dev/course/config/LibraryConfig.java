package dev.course.config;

import lombok.Builder;

public class LibraryConfig {

    private final BookRepositoryConfig bookRepositoryConfig;

    @Builder
    public LibraryConfig(BookRepositoryConfig bookRepositoryConfig) {
        this.bookRepositoryConfig = bookRepositoryConfig;
    }

    public BookRepositoryConfig getBookRepositoryConfig() {
        return this.bookRepositoryConfig;
    }
}
