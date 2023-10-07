package com.dev_course.io_module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleBufferedReader implements Reader {
    private final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    @Override
    public String readLine() {
        try {
            return br.readLine();
        } catch (IOException e) {
            // EOF 같은 값에 대한 예외
            throw new EmptyInputException("다음 입력을 읽을 수 없습니다.");
        }
    }
}
