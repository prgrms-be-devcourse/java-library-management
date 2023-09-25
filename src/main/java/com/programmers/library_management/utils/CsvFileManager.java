package com.programmers.library_management.utils;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.Status;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CsvFileManager {
    private final String RESOURCES = Objects.requireNonNull(CsvFileManager.class.getClassLoader().getResource("book_list.csv")).getPath();

    public CsvFileManager(){}

    // TODO: fix catch Block
    public void saveMemoryToCsv(Map<Integer, Book> bookList){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RESOURCES, false), StandardCharsets.UTF_8))){
            for(Book book:bookList.values()){
                bufferedWriter.write(book.toCsvString());
            }
        } catch (IOException ignore){

        }
    }

    // TODO: fix catch Block
    public Map<Integer, Book> loadMemoryFromCsv(){
        Map<Integer, Book> bookMemory = new HashMap<>();
        File file = new File(RESOURCES);
        if(file.exists()){
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(RESOURCES), StandardCharsets.UTF_8))){
                String tempData;
                while((tempData = bufferedReader.readLine()) != null){
                    String[] bookRawData = tempData.split(",");
                    Book book = new Book(
                            Integer.parseInt(bookRawData[0]),
                            bookRawData[1],
                            bookRawData[2],
                            Integer.parseInt(bookRawData[3]),
                            Status.valueOf(bookRawData[4]),
                            bookRawData[5]
                    );
                    bookMemory.put(Integer.parseInt(bookRawData[0]), book);
                }
            }catch (IOException ignore){

            }
        }
        return bookMemory;
    }
}
