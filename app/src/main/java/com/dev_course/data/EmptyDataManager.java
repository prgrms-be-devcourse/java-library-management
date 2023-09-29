package com.dev_course.data;

import java.util.ArrayList;
import java.util.List;

public class EmptyDataManager<T> implements DataManager<T> {
    @Override
    public List<T> load() {
        return new ArrayList<>();
    }

    @Override
    public void save() {
    }
}
