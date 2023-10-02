package com.libraryManagement.view;

import com.libraryManagement.util.GlobalVariables;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ModeMenu {
    private List<String> modeList = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ModeMenu() {
        modeList.add(" ");  // 0번 인덱스는 빈칸으로
        modeList.add("일반 모드");
        modeList.add("테스트 모드");
    }

    public void displayModeMenu() throws IOException {

        while(true) {
            System.out.println("0. 모드를 선택해주세요.");

            for(int i = 1; i < modeList.size(); i++){
                System.out.println(i + ". " + modeList.get(i));
            }
            System.out.println();

            int num = Integer.parseInt(br.readLine());

            if(num > modeList.size() || num == 0){
                System.out.println("숫자를 다시 입력해주세요.");
                System.out.println();
            }else{
                GlobalVariables.mode = modeList.get(num);
                System.out.println("[System] " + modeList.get(num) + "로 애플리케이션을 실행합니다.");
                System.out.println();
                break;
            }
        }
    }
}
