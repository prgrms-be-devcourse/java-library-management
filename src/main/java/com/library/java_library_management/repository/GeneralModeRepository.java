package com.library.java_library_management.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.java_library_management.controller.Controller;
import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.dto.JsonInfo;
import com.library.java_library_management.status.BookStatus;

import java.awt.print.Book;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
    ObjectMapper objectMapper = new ObjectMapper();
    FileControl fileControl = new FileControl();
    private String bookNumPath;
    private int value;
    private String filePath;
    private String fileName;


    public GeneralModeRepository() throws IOException{
        this.bookNumPath = "D:\\Users\\java_library_management\\bookcnt.json";
        JsonNode jsonNode = objectMapper.readTree(new File(bookNumPath));
        this.value = Integer.parseInt(jsonNode.get("count").asText());

        this.filePath = "D:\\Users\\java_library_management\\";
        this.fileName = value + ". book.json";
    }




    //도서 등록
    @Override
    public void registerBook(String title, String author, int pageSize) {
        try{
            String jsonStatus = objectMapper.writeValueAsString(BookStatus.AVAILABLE);
            fileControl.writeFile(filePath + fileName,
                            new JsonInfo(Integer.parseInt(fileControl.readFile(bookNumPath, "count")),
                            title, author, pageSize, jsonStatus));
            fileControl.modifyFile(bookNumPath, "count", String.valueOf(++value));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //전체 목록 조회
    @Override
    public List<BookInfo> getTotalBook() {
        List<File> allFile = fileControl.getAllFile(filePath);
        System.out.println("리스트 사이즈 : " + allFile.size());
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
        List<File> allFile = fileControl.getAllFile(filePath);
        try{
            for (File file : allFile) {
                BookInfo book = getBookFromFile(file);
                if(book.getBook_id() == book_id){
                    if(book.getStatus() == BookStatus.RENT)
                        return "현재 대여중인 도서입니다.";
                    else if(book.getStatus() == BookStatus.LOST)
                        return "현재 분실상태인 도서입니다.";
                    else if(book.getStatus() == BookStatus.CLEANING)
                        return "현재 정리중인 도서입니다.";
                    else{
                        String jsonStatus = objectMapper.writeValueAsString(BookStatus.RENT);
                        fileControl.modifyFile(file.getAbsolutePath(), "status", jsonStatus);
                        return book.getStatus().rentBook(book);
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }


        return "";
    }

    @Override
    public void returnBook(int book_id) {
        List<File> allFile = fileControl.getAllFile(filePath);
        try{
            for(File file : allFile){
                BookInfo book = getBookFromFile(file);
                if(book.getBook_id() == book_id && book.getStatus() == BookStatus.AVAILABLE){
                    System.out.println("파일의 상태 : " + book.getStatus());
                    throw new RuntimeException("원래 대여 가능한 도서입니다");
                }
                else if(book.getBook_id() == book_id){
                    String jsonStatus = objectMapper.writeValueAsString(BookStatus.CLEANING);
                    fileControl.modifyFile(file.getAbsolutePath(), "status", jsonStatus);
                    scheduler.schedule(() -> {
                        try {
                            String statusAfterFive = objectMapper.writeValueAsString(BookStatus.AVAILABLE);
                            fileControl.modifyFile(file.getAbsolutePath(), "status", statusAfterFive);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }, 5, TimeUnit.MINUTES);


                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void missBook(int book_id) {
        List<File> allFile = fileControl.getAllFile(filePath);
        try{
            for(File file : allFile){
                BookInfo book = getBookFromFile(file);
                if(book_id == book.getBook_id() && book.getStatus() == BookStatus.LOST)
                    throw new RuntimeException("이미 분실 처리된 도서입니다.");
                else if(book_id == book.getBook_id()){
                    String jsonStatus = objectMapper.writeValueAsString(BookStatus.LOST);
                    fileControl.modifyFile(file.getAbsolutePath(), "status", jsonStatus);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public void deleteById(int book_id) {
        List<File> allFile = fileControl.getAllFile(filePath);
        try{
            for(File file : allFile){
                BookInfo book = getBookFromFile(file);
                if(book.getBook_id() == book_id) {
                    Files.delete(Paths.get(file.getAbsolutePath()));
                    return;
                }
            }
            throw new RuntimeException("존재하지 않는 도서번호입니다.");
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

        BookInfo book = new BookInfo(book_id, author, title, page_size, bookStatus);
        return book;
    }

    //2번 모드 구현 때, 테스트 코드 작성용 메소드
    @Override
    public Optional<BookInfo> findSameBook(String title) {
        return Optional.empty();
    }
}
