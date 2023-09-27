package com.library.java_library_management.repository;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.status.BookStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GeneralModeRepository implements Repository{
    ObjectMapper objectMapper = new ObjectMapper();
    FileControl fileControl = new FileControl();
    private String bookNumPath;
    private int value;
    private String filePath;
    private String fileName;


    public GeneralModeRepository() throws IOException{

        this.bookNumPath = "D:\\Users\\java_library_management\\src\\main\\java\\com\\library\\java_library_management\\json\\bookcnt.json";

        JsonNode jsonNode = objectMapper.readTree(new File(bookNumPath));
        this.value = Integer.parseInt(jsonNode.get("count").asText());
        this.filePath = "D:\\Users\\java_library_management\\src\\main\\java\\com\\library\\java_library_management\\json\\";
        this.fileName = value + ". book";
    }




    @Override
    public void registerBook(String title, String author, int pageSize) {
        try{
            fileControl.writeFile(filePath + fileName, new BookInfo(fileControl.readFile(bookNumPath, "count"), title, author, pageSize, BookStatus.AVAILABLE));
            fileControl.modifyFile(bookNumPath, "count", String.valueOf(++value));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<BookInfo> getTotalBook() {
        List<File> allFile = fileControl.getAllFile(filePath);
        List<BookInfo> books = new ArrayList<>();
        try{
            for(File file : allFile){
                BookInfo book = objectMapper.readValue(file, BookInfo.class);
                books.add(book);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return books;
    }
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
        List<BookInfo> books = getTotalBook();
        books.stream()
                .filter(
                        bookinfo -> bookinfo.getBook_id() == book_id && bookinfo.getStatus() == BookStatus.AVAILABLE).findAny()
                .ifPresentOrElse(bookInfo -> {
                    bookInfo.setStatus(BookStatus.RENT);
                }, () -> {
                    throw new RuntimeException("이미 대여중인 도서입니다.");
                });
        return "";
    }

    @Override
    public void returnBook(int book_id) {
        List<BookInfo> books = getTotalBook();
        books.stream()
                .filter(book -> book.getBook_id() == book_id
                        && book.getStatus().equals(BookStatus.RENT) || book.getStatus().equals(BookStatus.LOST))
                .findAny()
                .ifPresentOrElse(book -> {
                    book.setStatus(BookStatus.AVAILABLE);
                },() -> {
                    throw new RuntimeException("원래 대여 가능한 도서입니다.");
                });
    }


    @Override
    public void missBook(int book_id) {
        List<BookInfo> books = getTotalBook();
        books.stream()
                .filter(bookInfo -> bookInfo.getBook_id() == book_id
                        && bookInfo.getStatus() != BookStatus.LOST)
                .findAny()
                .ifPresentOrElse(book -> {
                    book.setStatus(BookStatus.LOST);
                }, () -> {
                    throw new RuntimeException("[System] 이미 분실 처리된 도서입니다.");
                });
    }
    @Override
    public void deleteById(int book_id) {
        Path jsonFilePath = Paths.get(filePath + book_id + ". book.json");

        List<BookInfo> books = getTotalBook();
        books.stream()
                .filter(bookinfo -> bookinfo.getBook_id() == book_id)
                .findAny()
                .ifPresentOrElse(bookInfo -> {
                    try{
                        Files.delete(jsonFilePath);
                    }catch (IOException e){
                        e.printStackTrace();
                    }},
                        () -> {
                            throw new RuntimeException("[System] 존재하지 않는 도서입니다.");
                        });

    }



    //2번 모드 구현 때, 테스트 코드 작성용 메소드
    @Override
    public Optional<BookInfo> findSameBook(String title) {
        return Optional.empty();
    }
}
