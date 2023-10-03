import domain.SelectMode;
import exception.NotAppropriateScope;
import message.ExecuteMessage;
import domain.ModeType;
import message.SelectMessage;
import service.Mode;

import java.io.*;
import java.util.InputMismatchException;

import static domain.Reader.*;

public class Main {
    public static void main(String[] args) {
        Mode mode;
        try {
            System.out.println(SelectMessage.MODE_SELECT_MESSAGE.getMessage());
            int selectNum = sc.nextInt();
            sc.nextLine();
            if(selectNum == 1 || selectNum == 2) {
                mode = SelectMode.valueOfSelectNum(selectNum).run();
            }
            else throw new NotAppropriateScope();
        } catch (NotAppropriateScope | InputMismatchException e) {
            System.out.println("1이나 2를 입력해주세요.");
            return;
        }

        while(true) {
            if(!mode.run()) break;
        }
        System.out.println(ExecuteMessage.FINISH.getMessage());
    }
}
