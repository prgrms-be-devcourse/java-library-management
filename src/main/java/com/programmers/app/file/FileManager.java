package com.programmers.app.file;

import java.io.IOException;

public interface FileManager<T, C> {

    T loadDataFromFile() throws IOException;

    void save(C c);
}
