package org.library.service;

import org.library.entity.Book;
import org.library.entity.Exception;
import org.library.entity.Message;
import org.library.repository.Repository;
import org.library.utils.AvailableProcessor;

import java.util.List;

public class BookService {
    private final Repository repository;
    private final AvailableProcessor availableProcessor;

    public BookService(Repository repository) {
        this.repository = repository;
        this.availableProcessor = new AvailableProcessor(repository);
    }

    // id 생성
    public Long generateId(){
        return repository.generatedId();
    }

    // 1. 도서 등록
    public void save(Book book){
        repository.save(book);
    }

    // 2. 전체 도서 목록 조회
    public List<Book> findAll(){
        return repository.findAll();
    }

    // 3. 제목으로 도서 검색
    public List<Book> findByTitle(String title){
        if(title.isBlank()){
            throw new IllegalArgumentException(Exception.INVALID_TITLE.getMessage());
        }
        return repository.findByTitle(title);
    }

    // 4. 도서 대여
    public String rent(Long id){
        Book book = repository.findById(id);
        try{
            String result = book.rent();
            repository.save(book);
            return result;
        }catch(RuntimeException e){
            return e.getMessage();
        }
    }

    // 5. 도서 반납
    public String returns(Long id){
        Book book = repository.findById(id);
        try{
            String result = book.returns();
            repository.save(book);
            return result;
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    // 6. 도서 분실
    public String reportLost(Long id){
        Book book = repository.findById(id);
        try{
            String result = book.reportLost();
            repository.save(book);
            return result;
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    // 7. 도서 삭제
    public String delete(Long id){
        Book book = repository.findById(id);
        try{
            repository.delete(book);
            return Message.SUCCESS_DELETE.getMessage();
        }catch (RuntimeException e){
            return e.getMessage();
        }
    }

    // 8. 파일 저장(종료)
    public void flush(){
        repository.flush();
    }

    public void processAvailable(){
        availableProcessor.run();
    }
}
