package org.library.service;

import org.library.entity.Book;
import org.library.entity.Message;
import org.library.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BookService {
    private final Repository repository;

    public BookService(Repository repository) {
        this.repository = repository;
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
        try{
            return repository.findByTitle(title);
        }catch(NoSuchElementException e){
            return new ArrayList<>();
        }
    }

    // 4. 도서 대여
    public void rent(Long id){
        Book book = repository.findById(id);
        try{
            String result = book.rent();
            System.out.println(result);
        }catch(RuntimeException e){
            System.out.println(e.getMessage());
        }

        repository.save(book);
    }

    // 5. 도서 반납
    public void returns(Long id){
        Book book = repository.findById(id);
        try{
            String result = book.returns();
            System.out.println(result);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        repository.save(book);
    }

    // 6. 도서 분실
    public void reportLost(Long id){
        Book book = repository.findById(id);
        try{
            String result = book.reportLost();
            System.out.println(result);
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
        repository.save(book);
    }

    // 7. 도서 삭제
    public void delete(Long id){
        Book book = repository.findById(id);
        try{
            repository.delete(book);
            System.out.println(Message.SUCCESS_DELETE.getMessage());
        }catch (RuntimeException e){
            System.out.println(e.getMessage());
        }
    }

    // 8. 파일 저장(종료)
    public void saveFile(){
        repository.saveFile();
    }
}
