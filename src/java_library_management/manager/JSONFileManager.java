package java_library_management.manager;

import com.google.gson.Gson;
import java_library_management.domain.Book;
import java_library_management.domain.BookState;
import java_library_management.util.BookSerializer;
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

public class JSONFileManager {

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
            FileReader fileReader = new FileReader(filePath);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(fileReader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("book");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject object = (JSONObject) jsonArray.get(i);
                Book elem = parseJSONObject(object);
                addCallback.accept(map, elem);
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException ignored) {}
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
