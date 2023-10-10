package com.libraryManagement.repository;

import com.libraryManagement.domain.Book;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.libraryManagement.domain.Book.BookStatus.DELETE;

public class FileRepository implements Repository {
    // filepath 유연하게!
    private static final String FILEPATH = "/Users/zooputer/Desktop/Repository/Spring/devCourse/libraryManagement/src/main/resources/static/books.json";
    private JSONArray jsonArray;
    private FileReader fileReader;
    private JSONTokener tokener;

    public void readJSONArray() throws FileNotFoundException {
        jsonArray = new JSONArray();
        fileReader = new FileReader(FILEPATH);
        tokener = new JSONTokener(fileReader);

        if(tokener.more()){
            jsonArray = new JSONArray(tokener);
        }
    }

    @Override
    public List<Book> findAllBooks() {
        List<Book> bookList = new ArrayList<>();

        try {
            readJSONArray();

            // JSON 배열에서 책 정보를 추출하여 Book 객체로 변환 후 리스트에 추가
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("id");
                String title = jsonObject.getString("title");
                String author = jsonObject.getString("author");
                int pages = jsonObject.getInt("pages");
                String status = jsonObject.getString("status");

                if(status.equals(DELETE.getName())){
                    continue;
                }

                Book book = new Book
                        .Builder()
                        .id(id)
                        .title(title)
                        .author(author)
                        .pages(pages)
                        .status(status)
                        .build();

                bookList.add(book);
            }

        } catch (IOException e) {
            // file read 실패시 exception throw
            e.printStackTrace();
        }

        return bookList;
    }

    @Override
    public List<Book> findBooksByTitle(String str) {
        List<Book> bookList = new ArrayList<>();

        try {
            readJSONArray();

            // JSON 배열에서 책 정보를 추출하여 Book 객체로 변환 후 리스트에 추가
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                long id = jsonObject.getLong("id");
                String title = jsonObject.getString("title");
                String author = jsonObject.getString("author");
                int pages = jsonObject.getInt("pages");
                String status = jsonObject.getString("status");

                if(status.equals(DELETE.getName())){
                    continue;
                }

                if(title.contains(str)){
                    Book book = new Book
                            .Builder()
                            .id(id)
                            .title(title)
                            .author(author)
                            .pages(pages)
                            .status(status)
                            .build();

                    bookList.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    public Book findBookById(long id) {
        try {
            readJSONArray();

            JSONObject jsonObject = jsonArray.getJSONObject((int) id - 1);
            String title = jsonObject.getString("title");
            String author = jsonObject.getString("author");
            int pages = jsonObject.getInt("pages");
            String status = jsonObject.getString("status");

            return new Book
                    .Builder()
                    .id(id)
                    .title(title)
                    .author(author)
                    .pages(pages)
                    .status(status)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void insertBook(Book book) {
        JSONObject obj = new JSONObject();
        obj.put("id", book.getId());
        obj.put("title", book.getTitle());
        obj.put("author", book.getAuthor());
        obj.put("pages", book.getPages());
        obj.put("status", book.getStatus());

        try {
            readJSONArray();

            jsonArray.put(obj);

            FileWriter file = new FileWriter(FILEPATH);
            file.write(jsonArray.toString(4)); // 4는 들여쓰기를 나타냄
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateBookStatus(long id, String bookStatus) {
        try {
            readJSONArray();

            JSONObject jsonToUpdate = jsonArray.getJSONObject((int) id - 1);

            // JSONObject 수정
            jsonToUpdate.put("status", bookStatus);

            // JSON 배열에서 수정된 JSONObject를 다시 설정
            jsonArray.put((int) id - 1, jsonToUpdate);

            FileWriter file = new FileWriter(FILEPATH);
            file.write(jsonArray.toString(4)); // 4는 들여쓰기를 나타냄
            file.flush();
            file.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getNumCreatedBooks() {
        try {
            readJSONArray();
            return jsonArray.length();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
