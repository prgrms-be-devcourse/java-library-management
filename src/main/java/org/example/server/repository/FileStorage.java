package org.example.server.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.entity.Book;
import org.example.server.entity.bookStatus.BookStatusType;
import org.example.server.entity.bookStatus.LoadStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileStorage {
    public final LinkedHashMap<Integer, Book> DATA;
    private final File JSON_FILE;
    private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public int newId;

    public FileStorage() {
        try {
            URI path = Paths.get("src", "main", "resources", "book.json").toUri();
            JSON_FILE = new File(path);
            Map<String, Object> jsonMap = OBJECT_MAPPER.readValue(JSON_FILE, Map.class);
            newId = (int) jsonMap.get("new_id");
            DATA = new LinkedHashMap<>();
            if (newId != 1) {
                ArrayList<LinkedHashMap<String, Object>> dataObjects = (ArrayList<LinkedHashMap<String, Object>>) jsonMap.get("data");
                putObjectsToBookData(dataObjects);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveFile() {
        Map<String, Object> jsonMap = new HashMap<>();
        if (DATA.isEmpty()) {
            jsonMap.put("new_id", 1);
            jsonMap.put("data", new ArrayList<>());
        } else {
            jsonMap.put("new_id", newId);
            ArrayList<LinkedHashMap<String, Object>> dataObjects = new ArrayList<>();
            putBookDataToObjects(dataObjects);
            jsonMap.put("data", dataObjects);
        }
        try (FileWriter fileWriter = new FileWriter(JSON_FILE)) {
            String jsonStr = OBJECT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);
            fileWriter.write(jsonStr);
            fileWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException("[System] 파일 쓰기 에러 발생");
        }
    }

    private void putObjectsToBookData(ArrayList<LinkedHashMap<String, Object>> objects) {
        objects.forEach(
                (bookInfoMap) -> {
                    int id = (int) bookInfoMap.get("id");
                    String name = (String) bookInfoMap.get("name");
                    String author = (String) bookInfoMap.get("author");
                    int pages = (int) bookInfoMap.get("pages");
                    String status = (String) bookInfoMap.get("status");
                    String endLoadTime = (String) bookInfoMap.get("endLoadTime");
                    DATA.put(id, new Book(id, name, author, pages, status, endLoadTime));
                }
        );
    }

    private void putBookDataToObjects(ArrayList<LinkedHashMap<String, Object>> objects) {
        DATA.values().forEach(
                book -> {
                    LinkedHashMap<String, Object> bookInfoMap = new LinkedHashMap<>();
                    bookInfoMap.put("id", book.id);
                    bookInfoMap.put("name", book.name);
                    bookInfoMap.put("author", book.author);
                    bookInfoMap.put("pages", book.pages);
                    bookInfoMap.put("status", book.status.getType().name());
                    if (book.status.getType().equals(BookStatusType.LOAD)) {
                        LoadStatus bookStatus = (LoadStatus) book.status;
                        bookInfoMap.put("endLoadTime", bookStatus.END_LOAD_TIME.toString());
                    } else {
                        bookInfoMap.put("endLoadTime", "");
                    }
                    objects.add(bookInfoMap);
                }
        );
    }
}
