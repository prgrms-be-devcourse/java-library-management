package repository;

import domain.Book;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class NormalRepository{
    private final FileManager fileManager = new FileManager();
    private List<Book> bookList;

    public NormalRepository() {
        bookList = fileManager.loadData();
    }

    // [1] 도서 등록
    public void register(Book book) {
        bookList.add(book);
        fileManager.updateFile(bookList);
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
        fileManager.updateFile(bookList);
    }

    // [5] 도서 반납
    public void returnBook(Book book) {
        book.doReturn();
        fileManager.updateFile(bookList);
    }

    // [6] 분실 처리
    public void report(Book book) {
        book.report();
        fileManager.updateFile(bookList);
    }

    // [7] 도서 삭제
    public void remove(Book book){
        bookList.remove(book);
        fileManager.updateFile(bookList);
    }

    // 아이디로 도서 조회
    public Optional<Book> findById(Integer id){
        for (Book book: bookList){
            if (Objects.equals(id, book.getId())){
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    // 도서 아이디 생성
    public Integer createId(){
        if (bookList.isEmpty()) return 1;
        return bookList.get(bookList.size()-1).getId()+1;
    }
}