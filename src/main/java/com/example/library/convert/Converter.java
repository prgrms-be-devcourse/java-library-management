package com.example.library.convert;

import com.example.library.domain.Book;
import com.example.library.domain.BookStatusType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Converter {
    public static Book convertJsonToBook(JSONObject jsonObject) {
        String id = (String) jsonObject.get("id");
        String title = (String) jsonObject.get("title");
        String writer = (String) jsonObject.get("writer");
        String pageNumber = (String) jsonObject.get("pageNumber");
        String bookStatus = (String) jsonObject.get("bookStatus");
        String bookReturnTime = (String) jsonObject.get("bookReturnTime");

        return Book.newInstance(Long.parseLong(id), title, writer, Integer.parseInt(pageNumber),
                convertStringToBookStatus(bookStatus), convertStringToLocalDateTime(bookReturnTime));
    }

    public static JSONObject convertBookToJson(List<Book> books) {
        JSONArray jsonArray = new JSONArray();

        for (Book book : books) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", Long.toString(book.getId()));
            jsonObject.put("title", book.getTitle());
            jsonObject.put("writer", book.getWriter());
            jsonObject.put("pageNumber", Integer.toString(book.getPageNumber()));
            jsonObject.put("bookStatusType", book.getBookStatusType());
            jsonObject.put("bookReturnTime", Converter.convertLocalDateTimeToString(book.getBookReturnTime()));
            jsonArray.add(jsonObject);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("books", jsonArray);

        return jsonObject;
    }

    public static String convertLocalDateTimeToString(LocalDateTime bookReturnTime) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return bookReturnTime.format(formatter);
    }


    private static BookStatusType convertStringToBookStatus(String status) {
        if (status.equals("대여중")) {

            return BookStatusType.대여중;

        } else if (status.equals("대여가능")) {

            return BookStatusType.대여가능;

        } else if (status.equals("도서 정리중")) {

            return BookStatusType.도서정리중;

        } else
            return BookStatusType.분실됨;
    }

    private static LocalDateTime convertStringToLocalDateTime(String time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        return LocalDateTime.parse(time, formatter);
    }

}
