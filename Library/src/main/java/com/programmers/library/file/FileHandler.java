package com.programmers.library.file;

import com.programmers.library.domain.Book;

import java.util.Map;

public class FileHandler {
    private final FileUtil fileUtil;
    public FileHandler(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }
    public Map<Long, Book> init()  {
        return fileUtil.loadFromCsvFile();
    }
    public void saveToFile(Book book)  {
        fileUtil.saveToCsvFile(book);
    }

    public void updateFile(Book book){
        fileUtil.updateFile(book);
    }
}
