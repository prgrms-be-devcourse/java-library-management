import domain.SelectMode;
import message.ExecuteMessage;
import domain.ModeType;
import message.SelectMessage;
import service.Mode;

import java.io.*;

import static domain.Reader.*;

public class Main {
    public static void main(String[] args) {
        System.out.println(SelectMessage.MODE_SELECT_MESSAGE.getMessage());
        int selectNum = sc.nextInt();
        sc.nextLine();
        Mode mode = SelectMode.valueOfSelectNum(selectNum).run();
        while(true) {
            if(!mode.run()) break;
        }
        System.out.println(ExecuteMessage.FINISH.getMessage());
    }
}
