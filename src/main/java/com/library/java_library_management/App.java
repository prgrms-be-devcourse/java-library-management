package com.library.java_library_management;

import com.library.java_library_management.controller.Controller;
import com.library.java_library_management.repository.GeneralModeRepository;
import com.library.java_library_management.repository.Repository;
import com.library.java_library_management.repository.TestModeRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Controller controller = null;
        System.out.println("Q. 모드를 선택해주세요.\n" +
                "1. 일반 모드\n" +
                "2. 테스트 모드\n" +
                "\n");
        String mode = br.readLine();
        switch (mode){
            case "1": controller = new Controller(new GeneralModeRepository());
            break;
            case "2": controller = new Controller(new TestModeRepository());
        }
        controller.printInitial();
    }

}
