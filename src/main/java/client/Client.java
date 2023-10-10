package client;

import repository.FileRepository;
import repository.TestRepository;
import service.Service;

public class Client {

    private final ConsoleManager consoleManager;
    private Service service;

    public Client(ConsoleManager consoleManager) {
        this.consoleManager = consoleManager;
    }

    public void run(){
        selectMode();
        selectFunction();
    }

    private void selectMode(){
        int mode = consoleManager.modeSelect();
        switch (mode) {
            case 1 -> {
                service = new Service(new FileRepository());
            }
            case 2 -> {
                service = new Service(new TestRepository());
            }
            default -> {
                selectMode();
            }
        }
        consoleManager.modePrint(mode);
    }

    private void selectFunction(){
        while(true){
            int selectResult = consoleManager.selectFunction();
            Function function = Function.of(selectResult);
            function.excute(service, consoleManager);
        }
    }
}
