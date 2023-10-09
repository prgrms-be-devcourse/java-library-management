package dev.course.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import dev.course.domain.Book;

import java.lang.reflect.Type;

public class BookSerializer implements JsonSerializer<Book> {

    @Override
    public JsonElement serialize(Book book, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("book_id", book.getBookId());
        jsonObject.addProperty("title", book.getTitle());
        jsonObject.addProperty("author", book.getAuthor());
        jsonObject.addProperty("page_num", book.getPage_num());
        jsonObject.addProperty("state", book.getState().toString());
        return jsonObject;
    }
}
