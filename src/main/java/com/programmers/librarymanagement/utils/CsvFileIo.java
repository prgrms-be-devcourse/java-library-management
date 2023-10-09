package com.programmers.librarymanagement.utils;

import com.programmers.librarymanagement.domain.Book;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CsvFileIo {

    private static final String PATH = System.getProperty("user.dir") + "/src/main/resources/data.csv";

    private final BufferedReader br;

    public CsvFileIo() {
        try {
            br = new BufferedReader(new FileReader(PATH));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<List<String>> readCsv() {

        List<List<String>> dataList = new ArrayList<>();

        try {
            String data;

            while ((data = br.readLine()) != null) {

                String[] singleData = data.split(",");

                dataList.add(Arrays.asList(singleData));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return dataList;
    }

    public void writeCsv(Book book) {

        File csv = new File(PATH);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true))) {

            String singleData = book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPage();

            bw.write(singleData);
            bw.newLine();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCsv(Map<Long, Book> bookMap) {

        File csv = new File(PATH);

        String singleData;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csv))) {

            for (Book book : bookMap.values()) {

                singleData = book.getId() + "," + book.getTitle() + "," + book.getAuthor() + "," + book.getPage();

                bw.write(singleData);
                bw.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
