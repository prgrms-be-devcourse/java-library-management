package service;

import domain.ModeType;
import exception.NotAppropriateScopeException;
import message.ExecuteMessage;
import repository.FileRepository;
import repository.MemoryRepository;
import view.SelectMenu;

import java.util.InputMismatchException;

import static domain.ConsolePrint.getMenuNum;

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
            } else throw new NotAppropriateScopeException();
        } catch (InputMismatchException | NotAppropriateScopeException e) {
            System.out.println(ExecuteMessage.MENU_ERROR);
            return false;
        }
    }
}
