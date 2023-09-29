package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static int modeNum;
    private static Mode mode;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while(true) {

            System.out.println("0. 모드를 선택해주세요.");
            System.out.println("1. 일반 모드");
            System.out.println("2. 테스트 모드");

            System.out.print("\n> ");

            try {
                modeNum = Integer.parseInt(br.readLine());
                mode = new Mode();
                if (modeNum == 1) {
                    mode.normalMode(1);
                } else if (modeNum == 2) {
                    mode.normalMode(2);
                } else {
                    throw new IllegalArgumentException("1 또는 2만 입력해주세요.");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}