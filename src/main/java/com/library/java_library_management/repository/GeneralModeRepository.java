package com.library.java_library_management.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.dto.JsonInfo;
import com.library.java_library_management.status.BookStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class GeneralModeRepository implements Repository{
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final FileControl fileControl = new FileControl();
    private final FileInfo fileInfo = new FileInfo();


    @Override
    public BookStatus getStatusById(int book_id) {
        try{
            File file = new File(fileInfo.getFilePath() + book_id + ". book.json");
            BookInfo book = getBookFromFile(file);
            return book.getStatus();
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }
    //도서 등록
    @Override
    public void registerBook(String title, String author, int pageSize) {
        try{
            String jsonStatus = objectMapper.writeValueAsString(BookStatus.AVAILABLE);
            fileControl.writeFile(fileInfo.getFilePath() + fileInfo.getFileName(),
                            new JsonInfo(Integer.parseInt(fileControl.readFile(fileInfo.getBookNumPath(), "count")),
                            title, author, pageSize, jsonStatus));
            fileInfo.setValue(fileInfo.getValue()+1);
            fileControl.modifyFile(fileInfo.getBookNumPath(), "count", String.valueOf(fileInfo.getValue()));
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    //전체 목록 조회
    @Override
    public List<BookInfo> getTotalBook() {
        List<File> allFile = fileControl.getAllFile(fileInfo.getFilePath());
        List<BookInfo> books = new ArrayList<>();
        try{
            for(File file : allFile){
                BookInfo book = getBookFromFile(file);
                books.add(book);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return books;
    }

    //제목으로 도서 검색
    @Override
    public List<BookInfo> findByTitle(String title) {
        List<BookInfo> books = getTotalBook();
        List<BookInfo> collect = books.stream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
        return collect;
    }


    @Override
    public String rentBook(int book_id) {

        try{
            File file = new File(fileInfo.getFilePath() + book_id + ". book.json");
            BookInfo book = getBookFromFile(file);
            String jsonStatus = objectMapper.writeValueAsString(BookStatus.RENT);
            fileControl.modifyFile(file.getAbsolutePath(), "status", jsonStatus);
            return book.getStatus().rentBook(book);
        }catch (IOException e){
            return null;
        }
    }

    @Override
    public void returnBook(int book_id) {
        File file = new File(fileInfo.getFilePath() + book_id + ". book.json");
        try{
            BookInfo book = getBookFromFile(file);
            String jsonStatus = objectMapper.writeValueAsString(BookStatus.CLEANING);
            fileControl.modifyFile(file.getAbsolutePath(), "status", jsonStatus);
            scheduler.schedule(() -> {
                try {
                    String statusAfterFive = objectMapper.writeValueAsString(BookStatus.AVAILABLE);
                    fileControl.modifyFile(file.getAbsolutePath(), "status", statusAfterFive);
                } catch (IOException e) {
                    e.printStackTrace();
                }}, 5, TimeUnit.MINUTES);
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void missBook(int book_id) {
        try{
            File file = new File(fileInfo.getFilePath() + book_id + ". book.json");
            String jsonStatus = objectMapper.writeValueAsString(BookStatus.LOST);
            fileControl.modifyFile(file.getAbsolutePath(), "status", jsonStatus);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(int book_id) {

        try{
            File file = new File(fileInfo.getFilePath() + book_id + ". book.json");
            Files.delete(Paths.get(file.getAbsolutePath()));
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private BookInfo getBookFromFile(File file) throws IOException {
        String status = fileControl.readFile(file.getAbsolutePath(), "status");
        BookStatus bookStatus = objectMapper.readValue(status, BookStatus.class);

        int book_id = Integer.parseInt(fileControl.readFile(file.getAbsolutePath(), "book_id"));
        String title = fileControl.readFile(file.getAbsolutePath(), "title");
        String author = fileControl.readFile(file.getAbsolutePath(), "author");
        int page_size = Integer.parseInt(fileControl.readFile(file.getAbsolutePath(), "page_size"));

        BookInfo book = new BookInfo(book_id, title, author, page_size, bookStatus);
        return book;
    }

    //2번 모드 구현 때, 테스트 코드 작성용 메소드
    @Override
    public Optional<BookInfo> findSameBook(String title) {
        List<BookInfo> totalBook = getTotalBook();
        for(BookInfo book : totalBook){
            if(book.getTitle().equals(title)){
                return Optional.ofNullable(book);

            }
        }
        return Optional.empty();
    }
}
