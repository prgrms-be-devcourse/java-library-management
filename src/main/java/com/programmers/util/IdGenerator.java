package com.programmers.util;

public class IdGenerator {

    private Long id;

    public IdGenerator() {
        this.id = 0L;
    }

    public IdGenerator(Long lastRowId) {
        this.id = lastRowId;
    }

    public Long generateId() {
        return this.id += 1;
    }
}
