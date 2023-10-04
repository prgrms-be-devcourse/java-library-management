package com.programmers.library_management.utils;

import com.programmers.library_management.domain.Book;
import com.programmers.library_management.domain.StatusType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CsvFileManager {
    private static final String FOLDER_PATH =  System.getProperty("user.dir") + "/data";
    private final String FILE_PATH;

    public CsvFileManager(String fileName) {
        createDataFolder();
        FILE_PATH = FOLDER_PATH + "/"+fileName+".csv";
    }

    private void createDataFolder() {
        File file = new File(FOLDER_PATH);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    public void saveMemoryToCsv(Map<Integer, Book> bookMemory) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(FILE_PATH, false), StandardCharsets.UTF_8))) {
            for (Book book : bookMemory.values()) {
                bufferedWriter.write(book.toCsvString());
            }
        } catch (IOException e) {
            System.out.println("[System] 잘못된 파일 접근입니다.\n");
        }
    }

    public Map<Integer, Book> loadMemoryFromCsv() {
        Map<Integer, Book> bookMemory = new HashMap<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_PATH), StandardCharsets.UTF_8))) {
                String tempData;
                while ((tempData = bufferedReader.readLine()) != null) {
                    String[] rawBookData = tempData.split(",");
                    Book book = Book.of(
                            Integer.parseInt(rawBookData[0]),
                            rawBookData[1],
                            rawBookData[2],
                            Integer.parseInt(rawBookData[3]),
                            StatusType.valueOf(rawBookData[4]),
                            rawBookData[5]
                    );
                    bookMemory.put(Integer.parseInt(rawBookData[0]), book);
                }
            } catch (IOException ignore) {
                System.out.println("[System] 잘못된 파일 접근입니다.\n");
            } catch (Exception e) {
                System.out.println("[System] 손상된 파일입니다. 데이터 초기화 후 진행합니다.\n");
            }
        }
        return bookMemory;
    }
}
