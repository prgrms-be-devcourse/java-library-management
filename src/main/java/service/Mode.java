package service;

import domain.SelectMenu;
import message.ExecuteMessage;
import domain.ModeType;
import message.SelectMessage;
import repository.NormalRepository;
import repository.TestRepository;

import java.io.*;

public class Mode {
    BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
    Service service;
    public Mode(ModeType mode) throws IOException {
        if(mode == ModeType.NORMAL_MODE) service = new Service(new NormalRepository());
        else if(mode == ModeType.TEST_MODE) service = new Service(new TestRepository());
    }

    public boolean run() throws IOException {
        System.out.println(SelectMessage.FUNCTION_SELECT_MESSAGE.getMessage());

        String selectNum = bf.readLine();
        SelectMenu.valueOfSelectNum(selectNum).run(service);
        return false;
    }
}
