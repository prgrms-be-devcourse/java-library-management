package org.library.controller;

import org.library.entity.Message;
import org.library.entity.Mode;
import org.library.error.InvalidModeError;
import org.library.repository.Repository;
import org.library.utils.InputManager;

import java.util.Arrays;

public class ModeController {
    InputManager inputManager = new InputManager();

    public Repository selectMode(){
        System.out.println(Message.INPUT_USE_MODE.getMessage());
        printAllMode();
        int mode = inputManager.inputInt();
        if(!isValid(mode)){
            throw new InvalidModeError();
        }
        return getRepository(mode);
    }

    public Repository getRepository(int modeNum){
        for(Mode mode : Mode.values()){
            if(mode.isEqual(modeNum)){
                System.out.println("[System] " + mode.getName() + "로 애플리케이션을 실행합니다.");
                return mode.getRepository();
            }
        }
        throw new InvalidModeError();
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
