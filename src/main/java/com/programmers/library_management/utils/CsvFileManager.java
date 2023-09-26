package com.programmers.library_management.utils;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.Status;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CsvFileManager {
    private String RESOURCES = System.getProperty("user.dir")+"/data";

    public CsvFileManager(){
        createDataFolder();
        RESOURCES +="/book_list.csv";
    }

    private void createDataFolder(){
        File file = new File(RESOURCES);
        if(!file.exists()){
            file.mkdir();
        }
    }

    public void saveMemoryToCsv(Map<Integer, Book> bookList){
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(RESOURCES, false), StandardCharsets.UTF_8))){
            for(Book book:bookList.values()){
                bufferedWriter.write(book.toCsvString());
            }
        } catch (IOException e){
            System.out.println("[System] 잘못된 파일 접근입니다.\n");
        }
    }

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
            } catch (IOException ignore){
                System.out.println("[System] 잘못된 파일 접근입니다.\n");
            } catch (Exception e) {
                System.out.println("[System] 손상된 파일입니다. 데이터 초기화 후 진행합니다.\n");
            }
        }
        return bookMemory;
    }
}
