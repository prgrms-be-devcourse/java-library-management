package service;

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

    public void run() throws IOException {
        System.out.println(SelectMessage.FUNCTION_SELECT_MESSAGE.getMessage());

        String num = bf.readLine();
        switch (num) {
            case "1" -> {
                System.out.println(ExecuteMessage.REGISTER.getMessage());
                service.register();
            }
            case "2" -> {
                System.out.println(ExecuteMessage.LIST.getMessage());
                service.list();
            }
            case "3" -> {
                System.out.println(ExecuteMessage.SEARCH.getMessage());
                service.search();
            }
            case "4" -> {
                System.out.println(ExecuteMessage.RENTAL.getMessage());
                service.rental();
            }
            case "5" -> {
                System.out.println(ExecuteMessage.RETURN.getMessage());
                service.returnBook();
            }
            case "6" -> {
                System.out.println(ExecuteMessage.LOST.getMessage());
                service.lostBook();
            }
            case "7" -> {
                System.out.println(ExecuteMessage.DELETE.getMessage());
                service.deleteBook();
            }
            default -> {
                System.out.println(ExecuteMessage.FINISH.getMessage());
                System.exit(0);
            }
        }
    }
}
