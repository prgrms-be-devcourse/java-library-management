package repository;

import domain.Book;
import domain.Status;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class NormalRepository{
    private final String PATH = System.getProperty("user.dir")+ "/src/main/resources/book_data.csv";
    private List<Book> bookList= new ArrayList<>();

    public NormalRepository() {
        loadData();
    }

    // file -> list
    private void loadData() {
        bookList.clear();
        try(BufferedReader br = new BufferedReader(new FileReader(PATH))){
            String line="";
            while ((line = br.readLine())!=null){
                String[] split = line.split(",");
                String title = split[1];
                String author = split[2];
                Integer page = Integer.valueOf(split[3]);
                Status status = Status.valueOf(split[4]);
                Book book = new Book(createId(),title,author,page, status);

                bookList.add(book);
            }
        } catch(IOException e) {
            throw new RuntimeException();
        }
    }

    // list -> file
    private void updateFile(){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))){
            for (Book book : bookList){
                bw.write(book.getId()+","+book.getTitle()+","+book.getAuthor()+","+book.getPage()+","+book.getStatus());
                bw.newLine();
            }
        } catch(IOException e){
            throw new RuntimeException();
        }
    }

    // [1] 도서 등록
    public void register(Book book) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(PATH,true))){
            bw.write(createId()+","+book.getTitle()+","+book.getAuthor()+","+book.getPage()+","+book.getStatus());
            bw.newLine();
            bw.flush();
            bookList.add(book);
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
        return bookList.stream().filter(book -> book.getTitle().contains(title)).collect(Collectors.toList());
    }

    // [4] 도서 대여
    public void borrow(Book book) {
        book.borrow();
    }

    // [5] 도서 반납
    public void returnBook(Book book) {
        book.doReturn();
    }

    // [6] 분실 처리
    public void report(Book book) {
        book.report();
    }

    // [7] 도서 삭제
    public void remove(Book book){
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

    public Long createId(){
        if (bookList.isEmpty()) return 1L;
        return bookList.get(bookList.size()-1).getId()+1;
    }
}