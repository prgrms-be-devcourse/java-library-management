package com.dev_course.data;

import java.util.List;

public interface DataManager<T> {
    List<T> load();
    void save();
}
