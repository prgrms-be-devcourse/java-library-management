package service;

import domain.SelectMenu;
import domain.ModeType;
import message.SelectMessage;
import repository.FileRepository;
import repository.MemoryRepository;
import static domain.Reader.sc;

public class Mode {
    Service service;
    public Mode(ModeType mode) {
        if(mode == ModeType.NORMAL_MODE) service = new Service(new FileRepository());
        else if(mode == ModeType.TEST_MODE) service = new Service(new MemoryRepository());
    }


    public boolean run() {
        System.out.println(SelectMessage.FUNCTION_SELECT_MESSAGE.getMessage());

        int selectNum = sc.nextInt();
        sc.nextLine();
        if(selectNum <= 7 && selectNum >= 1) {
            SelectMenu.valueOfSelectNum(selectNum).run(service);
            return true;
        }
        return false;
    }
}
