package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    private static int modeNum;
    private static Mode mode;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("0. 모드를 선택해주세요.");
        System.out.println("1. 일반 모드");
        System.out.println("2. 테스트 모드");

        mode = new Mode();

        while(true) {
            System.out.print("\n> ");
            modeNum = Integer.parseInt(br.readLine());

            try {
                if (modeNum == 1) {
                    System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.\n");
                    mode.normalMode(1);
                    break;
                } else if (modeNum == 2) {
                    System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.\n");
                    mode.normalMode(2);
                    break;
                } else {
                    throw new IllegalArgumentException("1 또는 2만 입력해주세요.");
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}