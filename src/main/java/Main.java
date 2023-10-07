import view.SelectMode;
import exception.NotAppropriateScope;
import message.ExecuteMessage;
import service.Mode;

import java.util.InputMismatchException;

import static domain.ConsolePrint.getModeNum;

public class Main {
    public static void main(String[] args) {
        Mode mode;
        try {
            int selectNum = getModeNum();
            if(selectNum == 1 || selectNum == 2) mode = SelectMode.valueOfSelectNum(selectNum).run();
            else throw new NotAppropriateScope();
        } catch (NotAppropriateScope | InputMismatchException e) {
            System.out.println(ExecuteMessage.MODE_ERROR);
            return;
        }

        while(true) {
            if(!mode.run()) break;
        }
        System.out.println(ExecuteMessage.FINISH.getMessage());
    }
}
