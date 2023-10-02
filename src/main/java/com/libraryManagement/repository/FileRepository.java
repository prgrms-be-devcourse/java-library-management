package com.libraryManagement.repository;

import com.libraryManagement.model.domain.Book;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileRepository implements Repository {
    private static final String FILEPATH = "/Users/zooputer/Desktop/Repository/Spring/devCourse/libraryManagement/src/main/resources/static/books.json";

    @Override
    public List<Book> findAll() {
        List<Book> bookList = new ArrayList<>();

        try {
            // JSON 배열 불러오기
            JSONArray jsonArray = new JSONArray();
            FileReader fileReader = new FileReader(FILEPATH);
            JSONTokener tokener = new JSONTokener(fileReader);

            if(tokener.more()){
                jsonArray = new JSONArray(tokener);
            }

            // JSON 배열에서 책 정보를 추출하여 Book 객체로 변환 후 리스트에 추가
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("id");
                String title = jsonObject.getString("title");
                String author = jsonObject.getString("author");
                int pages = jsonObject.getInt("pages");

                Book book = new Book
                        .Builder()
                        .id(id)
                        .title(title)
                        .author(author)
                        .pages(pages)
                        .build();

                bookList.add(book);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    @Override
    public Book findOne(String str) {
        return null;
    }

    @Override
    public Book findOne(long id) {
        return null;
    }

    @Override
    public void bookInsert(Book book) {

        JSONObject obj = new JSONObject();
        obj.put("id", book.getId());
        obj.put("title", book.getTitle());
        obj.put("author", book.getAuthor());
        obj.put("pages", book.getPages());

        try {
            // JSON 배열 생성 또는 기존 배열 불러오기
            JSONArray jsonArray = new JSONArray();
            FileReader fileReader = new FileReader(FILEPATH);
            JSONTokener tokener = new JSONTokener(fileReader);

            if(tokener.more()){
                jsonArray = new JSONArray(tokener);
            }

            // 새로운 JSON 객체를 배열에 추가
            jsonArray.put(obj);

            // 파일에 JSON 배열 쓰기
            FileWriter file = new FileWriter(FILEPATH);
            file.write(jsonArray.toString(4)); // 4는 들여쓰기를 나타냄
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
