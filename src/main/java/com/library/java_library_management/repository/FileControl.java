package com.library.java_library_management.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.dto.JsonInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileControl {
    ObjectMapper objectMapper = new ObjectMapper();

    public String readFile(String filePath, String key) throws IOException{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(new File(filePath));

            String value = jsonNode.get(key).asText();
            return value;
    }

    public void writeFile(String filePath, BookInfo bookInfo) throws IOException{
        objectMapper.writeValue(new File(filePath), bookInfo);
    }
    public void writeFile(String filePath, JsonInfo jsonInfo) throws IOException{
        objectMapper.writeValue(new File(filePath), jsonInfo);
    }

    public void modifyFile(String filePath, String key, String value) throws IOException{
        JsonNode jsonNode = objectMapper.readTree(new File(filePath));

        // Modify the JSON object as needed
        if (jsonNode instanceof ObjectNode) {
            ObjectNode objectNode = (ObjectNode) jsonNode;
            objectNode.put(key, value);
        }
        // Write the modified JSON object back to the file
        objectMapper.writeValue(new File(filePath), jsonNode);
    }

    public List<File> getAllFile(String directoryPath){
        List<File> jsonFiles = new ArrayList<>();

        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                // Check if the file is a JSON file (has a .json extension)
                if (file.isFile() && file.getName().endsWith(".json") && !file.getName().equals("bookcnt.json")) {
                    jsonFiles.add(file);
                }
            }
        }
        return  jsonFiles;
    }
}
