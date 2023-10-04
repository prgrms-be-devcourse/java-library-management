package repository;

import domain.Book;
import manager.FileManager;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileRepository implements BookRepository {
    private final FileManager fileManager;
    private final List<Book> bookList;
    private final Integer START_ID = 1;

    public FileRepository(String path) {
        fileManager = new FileManager(path);
        bookList = fileManager.loadData();
    }

    // [1] 도서 등록
    @Override
    public void register(Book book) {
        bookList.add(book);
        fileManager.updateFile(bookList);
    }

    // [2] 도서 목록 조회
    @Override
    public List<Book> getBookList() {
        return bookList;
    }

    // [3] 도서 검색
    @Override
    public List<Book> findByTitle(String title) {
        return bookList.stream().filter(book -> book.getTitle().contains(title)).collect(Collectors.toList());
    }

    // [4] 도서 대여
    @Override
    public void borrow(Book book) {
        book.borrow();
        fileManager.updateFile(bookList);
    }

    // [5] 도서 반납
    @Override
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
    @Override
    public void remove(Book book){
        bookList.remove(book);
        fileManager.updateFile(bookList);
    }

    // 아이디로 도서 조회
    @Override
    public Optional<Book> findById(Integer id){
        for (Book book: bookList){
            if (book.isSameBookId(id)){
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    // 도서 아이디 생성
    @Override
    public Integer createId(){
        if (bookList.isEmpty()) return START_ID;
        return bookList.get(bookList.size()-1).getId()+1;
    }

    // 리스트 값 비우기 (테스트용)
    public void clear(){
        bookList.clear();
        fileManager.updateFile(bookList);
    }
}
