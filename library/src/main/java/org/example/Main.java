package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static int modeNum;
    private static Mode mode;

    public static void main(String[] args) throws IOException {

        System.out.println("0. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");

        System.out.print("\n> ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        modeNum = Integer.parseInt(br.readLine());
        mode = new Mode();
        if(modeNum == 1) {
            mode.normalMode();
        }else {
            mode.testMode();
        }
    }
}