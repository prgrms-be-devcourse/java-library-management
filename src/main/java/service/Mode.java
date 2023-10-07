package service;

import message.ExecuteMessage;
import view.SelectMenu;
import domain.ModeType;
import exception.NotAppropriateScope;
import repository.FileRepository;
import repository.MemoryRepository;
import java.util.InputMismatchException;

import static view.ConsolePrint.getMenuNum;
import static view.ConsolePrint.getModeNum;

public class Mode {
    Service service;
    public Mode(ModeType mode) {
        if(mode == ModeType.NORMAL_MODE) service = new Service(new FileRepository());
        else if(mode == ModeType.TEST_MODE) service = new Service(new MemoryRepository());
    }


    public boolean run() {
        try {
            int selectNum = getMenuNum();
            if(selectNum <= 7 && selectNum >= 1) {
                SelectMenu.valueOfSelectNum(selectNum).run(service);
                return true;
            } else throw new NotAppropriateScope();
        } catch (InputMismatchException | NotAppropriateScope e) {
            System.out.println(ExecuteMessage.MENU_ERROR);
            return false;
        }
    }
}
