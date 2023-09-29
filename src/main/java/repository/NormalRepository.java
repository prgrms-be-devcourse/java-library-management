package repository;

import domain.Book;
import domain.Status;
import exception.UnableStatusException;
import exception.NotExistBookIdException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NormalRepository{
    private final String PATH = System.getProperty("user.dir")+ "/src/main/resources/book_data.csv";
    private List<Book> bookList= new ArrayList<>();

    public NormalRepository() {
        updateBookList();
    }

    // file -> list
    private void updateBookList() {
        try(BufferedReader br = new BufferedReader(new FileReader(PATH))){
            String line="";
            while ((line = br.readLine())!=null){
                String[] split = line.split(",");
                String title = split[1];
                String author = split[2];
                Integer page = Integer.valueOf(split[3]);
                Book book = new Book(title,author,page);

                bookList.add(book);
            }
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }
    // list -> file
    private void updateFile(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH,true))){
            for (Book book : bookList){
                bw.write(book.getId()+","+book.getTitle()+","+book.getAuthor()+","+book.getPage()+"\n");
            }
        } catch(IOException e){
            throw new RuntimeException();
        }
    }

    // [1] 도서 등록
    public void register(Book book) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH,true))){
            bw.write(book.getId()+","+book.getTitle()+","+book.getAuthor()+","+book.getPage());
            bw.newLine();
        } catch(IOException e){
            throw new RuntimeException();
        }
    }

    // [2] 도서 목록 조회
    public List<Book> getBookList() {
        return bookList;
    }

    // [3] 도서 검색
    public List<Book> findByTitle(String title) {
        List<Book> booksFounded = bookList.stream().filter(book -> book.getTitle().contains(title)).collect(Collectors.toList());

        return booksFounded;
    }

    // [4] 도서 대여
    public void borrow(Long id) {
        Book book = findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()){
            case CLEANING -> throw new UnableStatusException("정리 중인 도서입니다.");
            case BORROWED -> throw new UnableStatusException("이미 대여 중인 도서입니다.");
            case LOST ->    throw new UnableStatusException("분실된 도서입니다.");
            case AVAILABLE -> book.borrow();
        }
    }

    // [5] 도서 반납
    public void returnBook(Long id) {
        Book book = findById(id).orElseThrow(NotExistBookIdException::new);
        switch (book.getStatus()){
            case AVAILABLE -> throw new UnableStatusException("원래 대여가 가능한 도서입니다.");
            case CLEANING -> throw new UnableStatusException("이미 반납되어 정리 중인 도서입니다.");
            case BORROWED, LOST -> book.doReturn();
        }
    }

    // [6] 분실 처리
    public void report(Long id){
        Book book = findById(id).orElseThrow(NotExistBookIdException::new);
        if (book.getStatus()== Status.LOST) throw new UnableStatusException("이미 분실처리된 도서입니다.");
        book.report();
    }

    // [7] 도서 삭제
    public void remove(Long id){
        Book book = findById(id).orElseThrow(NotExistBookIdException::new);
        bookList.remove(book);
        updateFile();
    }

    // 아이디로 도서 조회
    public Optional<Book> findById(Long id){
        for (Book book: bookList){
            if (id == book.getId()){
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }
}