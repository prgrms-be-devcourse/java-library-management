import domain.SelectMode;
import message.ExecuteMessage;
import domain.ModeType;
import message.SelectMessage;
import service.Mode;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(SelectMessage.MODE_SELECT_MESSAGE.getMessage());
        String selectNum = bf.readLine();
        Mode mode = SelectMode.valueOfSelectNum(selectNum).run();
        while(true) mode.run();
    }
}
