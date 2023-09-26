package com.library.java_library_management.service;

import com.library.java_library_management.repository.TestModeRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SelectFunction {
    public void printInitial() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Service service = new Service(new TestModeRepository());
        while(true){
            System.out.println("Q. 사용할 기능을 선택해주세요.\n" +
                    "1. 도서 등록\n" +
                    "2. 전체 도서 목록 조회\n" +
                    "3. 제목으로 도서 검색\n" +
                    "4. 도서 대여\n" +
                    "5. 도서 반납\n" +
                    "6. 도서 분실\n" +
                    "7. 도서 삭제\n");
            int menu = Integer.parseInt(br.readLine());
            switch (menu){
                case 1: service.register();
                    break;
                case 2: service.getBook();
                    break;
                case 4: service.rent();
                    break;
                case 5: service.returnBook();
                    break;
                case 6: service.missBook();
                    break;
                case 7: service.deleteBook();
                    break;

            }
        }

    }
}
