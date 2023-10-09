package com.library.java_library_management.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileInfo {
    ObjectMapper objectMapper = new ObjectMapper();
    private String bookNumPath;
    private int value;
    private String filePath;
    private String fileName;

    public FileInfo(){
        try{
            this.bookNumPath = "D:\\Users\\java_library_management\\bookcnt.json";
            JsonNode jsonNode = objectMapper.readTree(new File(bookNumPath));
            this.value = Integer.parseInt(jsonNode.get("count").asText());

            this.filePath = "D:\\Users\\java_library_management\\";
            this.fileName = value + ". book.json";
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public String getBookNumPath() {
        return bookNumPath;
    }

    public int getValue() {
        return value;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
