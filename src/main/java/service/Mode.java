package service;

import domain.SelectMenu;
import domain.ModeType;
import exception.NotAppropriateScope;
import message.SelectMessage;
import repository.FileRepository;
import repository.MemoryRepository;

import java.util.InputMismatchException;

import static domain.Reader.sc;

public class Mode {
    Service service;
    public Mode(ModeType mode) {
        if(mode == ModeType.NORMAL_MODE) service = new Service(new FileRepository("src/main/resources/library.csv"));
        else if(mode == ModeType.TEST_MODE) service = new Service(new MemoryRepository());
    }


    public boolean run() {
        System.out.println(SelectMessage.FUNCTION_SELECT_MESSAGE.getMessage());

        try {
            int selectNum = sc.nextInt();
            sc.nextLine();
            if(selectNum <= 7 && selectNum >= 1) {
                SelectMenu.valueOfSelectNum(selectNum).run(service);
                return true;
            } else {
                throw new NotAppropriateScope();
            }
        } catch (InputMismatchException | NotAppropriateScope e) {
            System.out.println("1~7까지의 숫자를 입력해주세요.");
            return false;
        }
    }
}
