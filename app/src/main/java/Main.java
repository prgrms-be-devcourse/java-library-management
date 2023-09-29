import message.ExecuteMessage;
import domain.ModeType;
import message.SelectMessage;
import service.Mode;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        Mode mode = selectMode();
        while(true) mode.run();
    }

    public static Mode selectMode() throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(SelectMessage.MODE_SELECT_MESSAGE);
        String num = bf.readLine();
        if(num.equals("1")) {
            System.out.println(ExecuteMessage.NORMAL_MODE);
            return new Mode(ModeType.NORMAL_MODE);
        } else {
            System.out.println(ExecuteMessage.TEST_MODE);
            return new Mode(ModeType.TEST_MODE);
        }
    }
}
