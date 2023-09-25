package com.library.java_library_management.io;

import com.library.java_library_management.dto.BookInfo;
import com.library.java_library_management.repository.DatabaseRepository;
import com.library.java_library_management.repository.DatabaseRepository;
import com.library.java_library_management.repository.Repository;
import com.library.java_library_management.status.BookStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class RegisterInput {
    private Repository repository;
    public RegisterInput(Repository repository) {
        this.repository = repository;
    }



    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private String title;
    private String author;
    private int pageSize;

    public void register() throws IOException {
        System.out.println("[System] 도서 등록 메뉴로 넘어갑니다.\n등록할 도서 제목을 입력하세요.");
        title = br.readLine();
        System.out.println("작가 이름을 입력하세요");
        author = br.readLine();
        System.out.println("페이지 수를 입력하세요");
        pageSize = Integer.parseInt(br.readLine());
        BookInfo bookInfo = new BookInfo(0, author, title, pageSize, BookStatus.AVAILABLE);
        repository.registerBook(title, author,pageSize);
    }

}
