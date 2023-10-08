package com.dev_course.data_module;

import java.util.Collections;
import java.util.List;

public class EmptyDataManager<T> implements DataManager<T> {
    @Override
    public List<T> load() {
        return Collections.emptyList();
    }

    @Override
    public void save(List<T> ignored) {

    }
}
