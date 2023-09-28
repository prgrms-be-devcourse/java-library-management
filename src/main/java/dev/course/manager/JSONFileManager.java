package dev.course.manager;

import com.google.gson.Gson;
import dev.course.domain.Book;
import dev.course.domain.BookState;
import dev.course.util.BookSerializer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * JSON 파일에 접근해서 파일을 읽어오고, 작성하는 책임을 담당하는 클래스
 */

public class JSONFileManager {

    private FileReader fileReader;
    private final JSONParser jsonParser;
    private final Gson gson;

    public JSONFileManager() throws IOException {
        this.jsonParser = new JSONParser();
        this.gson = new Gson().newBuilder().setPrettyPrinting()
                .registerTypeAdapter(Book.class, new BookSerializer())
                .create();
    }

    public void readFile(Map<Integer, Book> map, String filePath, BiConsumer<Map<Integer, Book>, Book> addCallback) {


        try {
            fileReader = new FileReader(filePath);
            JSONObject jsonObject = getJSONObect(filePath);
            JSONArray jsonArray = getJSONArray(jsonObject, "book");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = getJSONObject(jsonArray, i);
                Book elem = parseJSONObject(object);
                addCallback.accept(map, elem);
            }

            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject getJSONObect(String filePath) {

        try {
            fileReader = new FileReader(filePath);
            JSONParser parser = new JSONParser();
            return (JSONObject) parser.parse(fileReader);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {}
        return null;
    }

    public JSONObject getJSONObject(JSONArray jsonArray, int idx) {
        return (JSONObject) jsonArray.get(idx);
    }

    public JSONArray getJSONArray(JSONObject jsonObject, String key) {
        return (JSONArray) jsonObject.get(key);
    }

    public JSONArray getJSON(String filePath) {

        JSONObject jsonObject = getJSONObect(filePath);
        if (jsonObject == null) return null;
        JSONArray jsonArray = getJSONArray(jsonObject, "book");
        if (jsonArray == null) return null;
        return jsonArray;
    }

    public Book parseJSONObject(JSONObject object) {
        int book_id = Integer.parseInt(String.valueOf(object.get("book_id")));
        String title = String.valueOf(object.get("title"));
        String author = String.valueOf(object.get("author"));
        int page_num = Integer.parseInt(String.valueOf(object.get("page_num")));
        BookState state = BookState.valueOfState(String.valueOf(object.get("state")));
        return new Book(book_id, title, author, page_num, state);
    }

    public void writeFile(Map<Integer, Book> map, String filePath) {

        try {
            FileWriter fileWriter = new FileWriter(filePath);
            fileWriter.write("{\n");
            fileWriter.write(" \"book\" : [\n");

            Iterator<Book> iterator = map.values().iterator();
            while (iterator.hasNext()) {
                Book book = iterator.next();
                String json = gson.toJson(book);
                fileWriter.write(json);
                if (iterator.hasNext()) fileWriter.write(",");
                fileWriter.write("\n");
            }

            fileWriter.write("]\n");
            fileWriter.write("}");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
