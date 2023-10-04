package org.library.controller;

import org.library.entity.Message;
import org.library.entity.Mode;
import org.library.exception.InvalidModeException;
import org.library.repository.Repository;
import org.library.utils.ConsoleInputManager;

import java.util.Arrays;

public class ModeController {
    private ConsoleInputManager consoleInputManager;
    private String path;

    public ModeController(ConsoleInputManager consoleInputManager, String path) {
        this.consoleInputManager = consoleInputManager;
        this.path = path;
    }

    public Repository selectMode(){
        System.out.println(Message.INPUT_USE_MODE.getMessage());
        printAllMode();
        int mode = consoleInputManager.inputInt();
        if(!isValid(mode)){
            throw new InvalidModeException();
        }
        return getRepository(mode);
    }

    public Repository getRepository(int modeNum){
        for(Mode mode : Mode.values()){
            if(mode.isEqual(modeNum)){
                System.out.println("[System] " + mode.getName() + "로 애플리케이션을 실행합니다.");
                return mode.getRepository(path);
            }
        }
        throw new InvalidModeException();
    }

    public void printAllMode(){
        Arrays.stream(Mode.values())
                .forEach(mode -> System.out.println(mode.toString()));
    }

    private boolean isValid(int mode){
        return Arrays.stream(Mode.values())
                .anyMatch(m->m.isEqual(mode));
    }
}
