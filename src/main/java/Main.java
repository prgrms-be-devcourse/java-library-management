import exception.NotAppropriateScopeException;
import message.ExecuteMessage;
import service.Mode;
import view.SelectMode;

import java.util.InputMismatchException;

import static domain.ConsolePrint.getModeNum;

public class Main {
    public static void main(String[] args) {
        Mode mode;
        try {
            int selectNum = getModeNum();
            if(selectNum == 1 || selectNum == 2) mode = SelectMode.valueOfSelectNum(selectNum).run();
            else throw new NotAppropriateScopeException();
        } catch (NotAppropriateScopeException | InputMismatchException e) {
            System.out.println(ExecuteMessage.MODE_ERROR);
            return;
        }

        while(true) {
            if(!mode.run()) break;
        }
        System.out.println(ExecuteMessage.FINISH.getMessage());
    }
}
