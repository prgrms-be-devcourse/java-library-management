package org.example.server.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.entity.Book;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

// 실제 DB의 역할
public class FileStorage {
    public int newId; //  생성 예정인 id 값, 1부터 생성, JSON에 저장되어 있다.
    public final LinkedHashMap<Integer, Book> data; // 프로그램 실행 시에는 파일 변경은 없고 해당 Map 데이터가 변경
    private final File jsonFile;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileStorage() {
        try {
            URI path = Paths.get("src", "main", "resources", "book.json").toUri();
            jsonFile = new File(path);
            Map<String, Object> jsonMap = objectMapper.readValue(jsonFile, Map.class);
            newId = (int) jsonMap.get("new_id");
            data = new LinkedHashMap<>();
            if (newId != 1) {
                ArrayList<LinkedHashMap<String, Object>> dataObjects = (ArrayList<LinkedHashMap<String, Object>>) jsonMap.get("data");
                putObjectsToBookData(dataObjects); // 데이커를 사용할 수 있도록 변경
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }// FileStorage는 생성하자마자 파일에서 데이터를 모두 읽어오고 가공한다.

    private void putObjectsToBookData(ArrayList<LinkedHashMap<String, Object>> objects) {
        objects.forEach(
                (bookInfoMap) -> {
                    int id = (int) bookInfoMap.get("id");
                    String name = (String) bookInfoMap.get("name");
                    String author = (String) bookInfoMap.get("author");
                    int pages = (int) bookInfoMap.get("pages");
                    String state = (String) bookInfoMap.get("state");
                    String endLoadTime = (String) bookInfoMap.get("endLoadTime");
                    data.put(id, new Book(id, name, author, pages, state, endLoadTime));
                }
        );
    }

    public void saveFile() {
        Map<String, Object> jsonMap = new HashMap<>();
        if (data.isEmpty()) {
            // 도서가 없는 경우에는 newid 1로 변경 후 저장.
            jsonMap.put("new_id", 1);
            jsonMap.put("data", new ArrayList<>());
        } else {
            jsonMap.put("new_id", newId);
            ArrayList<LinkedHashMap<String, Object>> dataObjects = new ArrayList<>();
            // Book to LinkedHashMap<String, Object>
            putBookDataToObjects(dataObjects);
            jsonMap.put("data", dataObjects);
        }
        /* JSON 파일 저장 */
        try (FileWriter fileWriter = new FileWriter(jsonFile)) {
            String jsonStr = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonMap);
            System.out.println(jsonStr);
            fileWriter.write(jsonStr);
            fileWriter.flush();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    } // 프로그램 종료시 실행

    private void putBookDataToObjects(ArrayList<LinkedHashMap<String, Object>> objects) {
        data.values().stream().forEach(
                book -> {
                    LinkedHashMap<String, Object> bookInfoMap = new LinkedHashMap<>();
                    bookInfoMap.put("id", book.id);
                    bookInfoMap.put("name", book.name);
                    bookInfoMap.put("author", book.author);
                    bookInfoMap.put("pages", book.pages);
                    bookInfoMap.put("state", book.state);
                    bookInfoMap.put("endLoadTime", book.endLoadTime);
                    objects.add(bookInfoMap);
                }
        );
    }
}
