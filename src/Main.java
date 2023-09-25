import domain.ModeType;
import service.Mode;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Mode mode = selectMode();
        while(true) mode.run();
    }

    public static Mode selectMode() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Q. 모드를 선택해주세요.\r\n" +
                        "1. 일반 모드\r\n" +
                        "2. 테스트 모드\r\n");
        String num = bf.readLine();
        if(num.equals("1")) {
            System.out.println("[System] 일반 모드로 애플리케이션을 실행합니다.");
            return new Mode("normal");
        }

        System.out.println("[System] 테스트 모드로 애플리케이션을 실행합니다.");
        return new Mode("test");
    }
}
