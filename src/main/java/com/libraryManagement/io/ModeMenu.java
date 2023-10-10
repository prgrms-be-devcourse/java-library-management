package com.libraryManagement.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.libraryManagement.exception.ExceptionMessage.*;

public class ModeMenu {
    private List<String> modeList;
    private String selectMode;
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public ModeMenu() {
        modeList = new ArrayList<>();
        modeList.add("모드를 선택해주세요.");
        modeList.add("일반 모드");
        modeList.add("테스트 모드");
    }

    public void displayModeMenu() throws IOException {

        while(true) {
            for(int i = 0; i < modeList.size(); i++){
                System.out.println(i + ". " + modeList.get(i));
            }
            System.out.println();

            int selectModeNum = Integer.parseInt(br.readLine());

            if(selectModeNum >= modeList.size() || selectModeNum == 0){
                System.out.println(INVALID_MODE_MENU.getMessage() + "\n");
                continue;
            }

            String selectMode = modeList.get(selectModeNum);
            setSelectMode(selectMode);

            System.out.println("[System] " + this.selectMode + "로 애플리케이션을 실행합니다.\n");
            break;
        }
    }

    public String getSelectMode() {
        return selectMode;
    }

    public void setSelectMode(String selectMode) {
        this.selectMode = selectMode;
    }
}
