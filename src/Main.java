import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {

        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        Mode mode = null;
        if(selectMode(bf, bw) == ModeType.NORMAL) {
            bw.write("[System] 일반 모드로 애플리케이션을 실행합니다.");
            mode = new Mode("normal");
        } else if(selectMode(bf, bw) == ModeType.TEST) {
            bw.write("[System] 테스트 모드로 애플리케이션을 실행합니다.");
            mode = new Mode("test");
        }
        mode.run();

        bw.close();
    }

    public static ModeType selectMode(BufferedReader bf, BufferedWriter bw) throws IOException {
        bw.write("Q. 모드를 선택해주세요.\n" +
                "1. 일반 모드\n" +
                "2. 테스트 모드");

        String num = bf.readLine();
        if(num.equals("1")) return ModeType.NORMAL;
        return ModeType.TEST;
    }
}
