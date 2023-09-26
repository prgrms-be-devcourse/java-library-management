package com.programmers.common;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    <S extends T> S save(S entity);
    Optional<T> findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
    <S extends T> void update(S entity);
}
